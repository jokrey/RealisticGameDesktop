package jokrey.game.realistic_game.control_units;

import jokrey.game.realistic_game.*;
import jokrey.game.realistic_game.care_package.CarePackage;
import jokrey.game.realistic_game.care_package.WeaponPackage;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AEPoint;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AEVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControlUnit_AI extends PlayerControlUnit {
	//HYPER-PARAMETERS
	private static final short SIMILAR_POSITION_DISTANCE = 4;
	private static final short NUMBER_OF_ROUNDS_POSITION_HAS_TO_BE_SIMILAR_TO_ACT = 10;
	private static final short SHOT_EVASION_RADIUS_IN_TIMES_PLAYER_WIDTH = 14;



	private final List<AEColor> friendlies;
	public ControlUnit_AI(AEColor... friendlies) {
		this.friendlies = Arrays.asList(friendlies);
	}

	//RUNTIME VARIABLES - THE AI's MEMORY
//	private double lifePs_inLastRound = 100;
	private AEPoint lastPlayerPosition = null;
	private boolean lastIndeterminantDirectionActionWasLeft = false;
	private int number_of_rounds_in_which_position_is_very_similar = 0;
	@Override public void doCalculations(Player player, Realistic_Game_Engine engine) {
		//KI CODE:
		//action rules:
		//always shoot
		//always try to land on the nearest platform

		//target jump on a higher platform(basicly choose a path to get to the target) TODO
		//anticipate future player positions in order to hit TODO
		//add Stargate logic TODO

		//Add and test new wearables (jetpack)


//1 - Find a suitable Weapon(or set the target to the next ammo crate)
        boolean currentWeaponIsInCoolDownAndOtherRangedWeaponIsNot = false;
		boolean anyRangedWeaponHasAmmo = false;
		boolean anyRangedWeaponHasMoreDamageThanCurWeaponAndIsNotInCooldown = false;
		boolean anotherCloseCombatWeaponCouldHit = false;
		boolean anotherCloseCombatWeaponCouldHitAndHasMoreDamage = false;
		boolean currentCloseCombatWeaponCouldHit = false;
		boolean hasACloseCombatWeapon = false;
		for(Weapon w:player.weapons) {
			w.weaponHolder=player;
			w.updatePosition();
            if(w instanceof RangedWeapon) {
                RangedWeapon rw = ((RangedWeapon) w);
                if(!rw.needsAmmo()) {
                    anyRangedWeaponHasAmmo=true;
                    if(rw.getDamage()>player.getCurrentWeapon().getDamage() && !rw.isInCooldown())
                        anyRangedWeaponHasMoreDamageThanCurWeaponAndIsNotInCooldown=true;
                }
                if(player.getCurrentWeapon().isInCooldown() && !w.isInCooldown() && w instanceof RangedWeapon) {
                    currentWeaponIsInCoolDownAndOtherRangedWeaponIsNot = true;
                }
			} else if(w instanceof CloseCombatWeapon) {
				hasACloseCombatWeapon=true;
				for(Player p:engine.getAlivePlayers()) {
					if(p!=player&&AnimationObject.intersect(w, p)) {
						if(w==player.getCurrentWeapon())
							currentCloseCombatWeaponCouldHit=true;
						else {
							anotherCloseCombatWeaponCouldHit=true;
							if(w instanceof AnimatedCloseCombatWeapon) {
								double playerDmg = player.getCurrentWeapon() instanceof AnimatedCloseCombatWeapon?((AnimatedCloseCombatWeapon)player.getCurrentWeapon()).getInitDamage():player.getCurrentWeapon().getDamage();
								if(((AnimatedCloseCombatWeapon)w).getInitDamage()>playerDmg)
									anotherCloseCombatWeaponCouldHitAndHasMoreDamage=true;
							} else {
								if(w.getDamage()>player.getCurrentWeapon().getDamage())
									anotherCloseCombatWeaponCouldHitAndHasMoreDamage=true;
							}
						}
					}
				}
			}
		}


        if(lastPlayerPosition == null)
            lastPlayerPosition=player.getMid();


//2 - Find a target
		AnimationObject target = null;
		for(Player oP:engine.getAlivePlayers()) {
			if(oP!=player && !friendlies.contains(oP.getPlayerColor())) {
				if(target==null) {
					target=oP;
				} else {
					boolean old_isClearHLOS = hasClearHorizontalLineOfSight(player, engine, target);
					boolean cur_isClearHLOS = hasClearHorizontalLineOfSight(player, engine, oP);
					if(cur_isClearHLOS) {
						if(old_isClearHLOS) {
	    					if(getVectorToPlayer(player, oP).getLength() < getVectorToPlayer(player, target).getLength())
	    						target=oP;
						} else {
							target=oP;
						}
					} else {
						if(old_isClearHLOS) {
							//keepOld
						} else {
	    					if(getVectorToPlayer(player, oP).getLength() < getVectorToPlayer(player, target).getLength())
	    						target=oP;
						}
					}
				}
			}
		}

		if(!hasACloseCombatWeapon && !anyRangedWeaponHasAmmo)
			target=null;//if enemy can not be hurt anyway, the enemy player is not to be pursuit

		if(target==null || !(anyRangedWeaponHasAmmo && hasClearHorizontalLineOfSight(player, engine, target))) {//if the enemy can be shot, that is to be preferred
    		for(CarePackage oP:engine.packages) {
    			if(isUsefulPackage(player, engine, oP)) {
    				if(target==null) {
    					target=oP;
    				} else {
    					if(getVectorToPlayer(player, oP).getLength() < getVectorToPlayer(player, target).getLength())
    						target=oP;
    				}
    			}
    		}
		}

		if(number_of_rounds_in_which_position_is_very_similar > NUMBER_OF_ROUNDS_POSITION_HAS_TO_BE_SIMILAR_TO_ACT)
			target=null;
//		if(player.getLifePs()<lifePs_inLastRound)   //try to evade future damage by acting out - replaced with shot evasion
//		    target=null;
        if(player.getV_Y()==0) {//standing on the floor
            int left_right_radius = (int) (player.getW()*SHOT_EVASION_RADIUS_IN_TIMES_PLAYER_WIDTH);

			AnimationObject virtualSurroundingsShot = new AnimationObject(player.getX() - left_right_radius, player.getY(), left_right_radius*2, player.getH(), AnimationObject.RECT);
			for(Shot s:engine.shots) {
				if(AnimationObject.intersect(s, virtualSurroundingsShot) && (
                        (s.getV_X() > 0 && s.getMid().x < player.getMid().x)
                        ||
                        (s.getV_X() < 0 && s.getMid().x > player.getMid().x)
                    )) {
					target=null; //initiates jump
				}
			}


            //virtualSurroundingsEnemyWithCCWeap
//			AnimationObject vs_cc = new AnimationObject(player.getX() - left_right_radius, player.getY()-player.getH(), left_right_radius*2, player.getH()*3, AnimationObject.RECT);
//			for(Player op:engine.getAlivePlayers()) {
//				if(op.getCurrentWeapon() instanceof CloseCombatWeapon && AnimationObject.intersect(op, vs_cc)) {
//				    if(op.getX() < player.getX() && op.getY() < player.getY()) // upper left
//    					target=new AnimationObject(vs_cc.getX(), vs_cc.getY(), 1, 1, AnimationObject.RECT);
//                    else if(op.getX() > player.getX() && op.getY() < player.getY()) // upper right
//                        target=new AnimationObject(vs_cc.getX()+vs_cc.getW(), vs_cc.getY(), 1, 1, AnimationObject.RECT);
//                    else if(op.getX() < player.getX() && op.getY() > player.getY()) // lower left
//                        target=new AnimationObject(vs_cc.getX(), vs_cc.getY()+vs_cc.getH(), 1, 1, AnimationObject.RECT);
//                    else if(op.getX() > player.getX() && op.getY() > player.getY()) // lower right
//                        target=new AnimationObject(vs_cc.getX()+vs_cc.getW(), vs_cc.getY()+vs_cc.getH(), 1, 1, AnimationObject.RECT);
//                    else
//                        target=null;
//				}
//			}
        }




//3 - calculate key actions

        //3.2 - calculate throwing away worst weapon
        if(target instanceof WeaponPackage && player.weapons.size()>=3 && !player.weapons.contains(((WeaponPackage)target).w) &&
                AnimationObject.intersect(player, target)) {
            List<Weapon> hierarchical_list = Weapon.getHierarchicalWeaponList(engine.getVirtualBoundaries());
            Weapon worstWeapon = null;
            for(Weapon w:player.weapons)
                if(hierarchical_list.indexOf(w) > hierarchical_list.indexOf(worstWeapon))
                    worstWeapon = w;

            if(player.getCurrentWeapon().equals(worstWeapon))
                performDiscardWeaponAction=true;
            else
                performSwitchWeaponAction=true;
        }

        //3.3 - switch to best weapon
        if (player.getCurrentWeapon() instanceof RangedWeapon) {
            if(currentWeaponIsInCoolDownAndOtherRangedWeaponIsNot || !anyRangedWeaponHasAmmo || anotherCloseCombatWeaponCouldHit ||
                    anyRangedWeaponHasMoreDamageThanCurWeaponAndIsNotInCooldown || (anyRangedWeaponHasAmmo && ((RangedWeapon) player.getCurrentWeapon()).needsAmmo()))
                performSwitchWeaponAction = true;
        } else {
            if (currentCloseCombatWeaponCouldHit) {
                if (anotherCloseCombatWeaponCouldHitAndHasMoreDamage)
                    performSwitchWeaponAction = true;
            } else {
                if (anotherCloseCombatWeaponCouldHit || anyRangedWeaponHasAmmo)
                    performSwitchWeaponAction = true;
            }
        }

        //3.4 - fire and move
		boolean clearHLineOfSightToTarget = hasClearHorizontalLineOfSight(player, engine, target);
		if(clearHLineOfSightToTarget) {
			if(target instanceof Player) {//todo anticipate future player positions in order to hit
				if(player.getCurrentWeapon() instanceof RangedWeapon) {
                    if(number_of_rounds_in_which_position_is_very_similar > NUMBER_OF_ROUNDS_POSITION_HAS_TO_BE_SIMILAR_TO_ACT/3) {
                        lastIndeterminantDirectionActionWasLeft= !lastIndeterminantDirectionActionWasLeft;
                    }
                    AEPoint shotInit_left = ((RangedWeapon)player.getCurrentWeapon()).getShotInitLocation(true);
                    AEPoint shotInit_right = ((RangedWeapon)player.getCurrentWeapon()).getShotInitLocation(false);
                    boolean left_action_required = shotInit_left.x > target.getX()+target.getW()+10/*    || target.getBounds().contains(shotInit_left)*/;
                    boolean right_action_required = shotInit_right.x < target.getX()-10          /* || target.getBounds().contains(shotInit_right)*/;

                    if(left_action_required && right_action_required) {
                        performAttackAction=true;
                    } else if(left_action_required) {
                        if(player.isLookingLeft())
                            performAttackAction=true;
                        else
                            performLeftAction= true;
                    } else if(right_action_required) {
                        if(!player.isLookingLeft())
                            performAttackAction=true;
                        else
                            performRightAction = true;
                    } else {
                        if(lastIndeterminantDirectionActionWasLeft)
                            performLeftAction=true;
                        else
                            performRightAction=true;
                    }
				} else if(player.getCurrentWeapon() != null) {
					if(AnimationObject.intersect(player.getCurrentWeapon(), target))
						performAttackAction=true;
					else {
						if(player.getMidAsPoint().x > target.getMidAsPoint().x)
							performLeftAction=true;
						else
							performRightAction=true;
					}
				}
			} else {
				if(target!=null) {
					if(player.getMidAsPoint().x > target.getMidAsPoint().x)
						performLeftAction=true;
					else
						performRightAction=true;
				}
			}
		} else {
			if(new AnimationObject(player.getX()+player.getW()+2,player.getY(),2,2, AnimationObject.RECT).overlapingBoundsRight(engine.getVirtualLimit_width()))
				performLeftAction=true;
			else if(new AnimationObject(player.getX()-2,player.getY(),2,2, AnimationObject.RECT).overlapingBoundsLeft()) {
				performRightAction=true;
			} else {
				if(target!=null) {
					if(target.getMidAsPoint().y<player.getMidAsPoint().y) {//is target above
						if(canFreelyJump(player, engine)) {
							if(player.getMidAsPoint().x > target.getMidAsPoint().x)
								performLeftAction=true;
							else
								performRightAction=true;
							performUpAction=true;
						} else {
							if(player.isLookingLeft())
								performLeftAction=true;
							else
								performRightAction=true;
						}
					} else if(target.getMidAsPoint().y>player.getMidAsPoint().y) {
						if(player.getV_Y()>0) {
							if(player.getMidAsPoint().x > target.getMidAsPoint().x)
								performLeftAction=true;
							else
								performRightAction=true;
						} else {
							if(player.isLookingLeft())
								performLeftAction=true;
							else
								performRightAction=true;
						}
					} else {
						performUpAction=true;
					}
				} else {
					if(player.isLookingLeft())
						performLeftAction=true;
					else
						performRightAction=true;
				}
			}
		}
        //TODO jump when the AnimationObject ends
        AnimationObject testP = new AnimationObject(new AERect(player.getX()+(player.isLookingLeft()?-2:player.getW()+2), player.getY(), player.getW(), player.getH()), AnimationObject.RECT);
        AnimationObject collP;
        if(target==null || (collP=AnimationObject.collidesWithOne(testP, engine.mapParticles))!=null && collP instanceof MapParticle && ((MapParticle)collP).isSolid) {
//                if(player.isLookingLeft()) {
//                    performRightAction=true;
//                    performLeftAction=false;
//                } else {
//                    performLeftAction=true;
//                    performRightAction=false;
//                }
            performUpAction=true;
        }



//4 - reset runtime variables
//		lifePs_inLastRound=player.getLifePs();
		if(player.getMid().distance(lastPlayerPosition) < SIMILAR_POSITION_DISTANCE)
			number_of_rounds_in_which_position_is_very_similar++;
		else
			number_of_rounds_in_which_position_is_very_similar=0;
        lastPlayerPosition=player.getMid();
	}

	private boolean isUsefulPackage(Player player, Realistic_Game_Engine engine, CarePackage oP) {
        List<Weapon> hierarchical_list = Weapon.getHierarchicalWeaponList(engine.getVirtualBoundaries());

        Weapon worstWeapon = null;
        for(Weapon w:player.weapons)
            if(hierarchical_list.indexOf(w) > hierarchical_list.indexOf(worstWeapon))
                worstWeapon = w;

        if(oP instanceof WeaponPackage) {
            int index_pack = hierarchical_list.indexOf(((WeaponPackage)oP).w);
            int index_cur = hierarchical_list.indexOf(worstWeapon);
            if(index_pack <= index_cur  || (worstWeapon instanceof RangedWeapon && ((RangedWeapon)worstWeapon).amunition == 0)) {
                return true;
            }
        } else {//every other package is definitely useful
            return true;
        }
		return player.getCurrentWeapon()==null;
	}
	public AEVector getVectorToPlayer(Player player, AnimationObject p) {
		AEVector vector = new AEVector(2);
		vector.set(0, player.getMidAsPoint().x-p.getMidAsPoint().x);
		vector.set(1, player.getMidAsPoint().y-p.getMidAsPoint().y);
		return vector;
	}
	public boolean hasClearHorizontalLineOfSight(Player player, Realistic_Game_Engine engine, AnimationObject p) {
		boolean clearLineOfSight = false;
		if(p!=null) {
			int playerMid_x = (int) player.getMidAsPoint().x;
			int playerMid_y = (int) player.getMidAsPoint().y;
			AEVector nearestPlayerVector = new AEVector(2);
			nearestPlayerVector.set(0, playerMid_x-p.getMidAsPoint().x);
			nearestPlayerVector.set(1, playerMid_y-p.getMidAsPoint().y);
    		clearLineOfSight = playerMid_y>=p.getY()&&playerMid_y<=p.getY()+p.getH();
    		int nearesPlayer_mid_x = (int) p.getMidAsPoint().x;
    		if(clearLineOfSight) {
    			for(MapParticle mp:engine.mapParticles) {
    				if(mp.isSolid && playerMid_y>=mp.getY()&&playerMid_y<=mp.getY()+mp.getH()   &&
    						(Realistic_Game_Engine.isBetween(mp.getMidAsPoint().x, nearesPlayer_mid_x, playerMid_x) ||
    						Realistic_Game_Engine.isBetween(mp.getMidAsPoint().x, playerMid_x, nearesPlayer_mid_x))) {
    					clearLineOfSight=false;
    					break;
    				}
    			}
    		}
		}
		return clearLineOfSight;
	}
//	public boolean hasClearVerticalLineOfSight(Player player, Realistic_Game_Engine engine, AnimationObject p) {
//		if(p==null)return false;
//		boolean clearLOS = player.getMidAsPoint().x>=p.getX()&&player.getMidAsPoint().x<=p.getX()+p.getW();
//		if(clearLOS) {
//			for(MapParticle mp:engine.mapParticles) {
//				if(mp.isSolid && p!=mp && player.getMidAsPoint().x>=mp.getX()&&player.getMidAsPoint().x<=mp.getX()+mp.getW()   &&
//						(Realistic_Game_Engine..isBetween(mp.getMidAsPoint().y, p.getMidAsPoint().y, player.getMidAsPoint().y) ||
//						Realistic_Game_Engine..isBetween(mp.getMidAsPoint().y, player.getMidAsPoint().y, p.getMidAsPoint().y))) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
	public boolean canFreelyJump(Player player, Realistic_Game_Engine engine) {
		double lowest_s_y = Integer.MAX_VALUE;//on screen actually highest
		for(double t=0;t<Integer.MAX_VALUE;t+=0.1) {
			double s_y = (0.5 * player.getA_Y() * t*t) + (-player.getStdVerticalBoostSpeed() * t) + (player.getY()+player.getH());
			if(s_y < lowest_s_y)lowest_s_y=s_y;
			else break;
		}
		int YPHWillReach = (int) (lowest_s_y);
		for(MapParticle mp:engine.mapParticles) {
			boolean isAbove = player.getMidAsPoint().x>=mp.getX()&&player.getMidAsPoint().x<=mp.getX()+mp.getW() && player.getY()+player.getH() > mp.getY();
			if(mp.isSolid && isAbove && Realistic_Game_Engine.isBetween(mp.getY(), YPHWillReach, (player.getY()+player.getH())-2)) {
				return false;
			}
		}
		return true;
	}
}