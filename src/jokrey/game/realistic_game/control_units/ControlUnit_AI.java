package jokrey.game.realistic_game.control_units;

import jokrey.game.realistic_game.*;
import jokrey.game.realistic_game.care_package.AmmoPackage;
import jokrey.game.realistic_game.care_package.CarePackage;
import util.UTIL;
import jokrey.utilities.animation.engine.MovingAnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AEVector;

public class ControlUnit_AI extends PlayerControlUnit {
	public ControlUnit_AI(Player p) {super(p);}

	double lifePs_inLastRound = 100;
	@Override public void doCalculations() {
		//KI CODE:
		//action rules:
		//always shoot
		//always try to land on the nearest platform
		//target jump on a higher platform(basicly choose a path to get to the target) TODO not implemented
		//evade shots TODO not implemented
		//anticipate future player positions in order to hit TODO
		//change weapon if it can't shoot right now TODO also intelligent weapon choosing in general
		//add Stargate logic TODO

		//Add and test new wearables (jetpack)

//Find a suitable Weapon(or set the target to the next ammo crate)
		boolean anyRangedWeaponHasAmmo = false;
		boolean anyRangedWeaponHasMoreDamageThanCurWeapon = false;
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
					if(rw.getDamage()>player.getCurrentWeapon().getDamage())
						anyRangedWeaponHasMoreDamageThanCurWeapon=true;
				}
			} else if(w instanceof CloseCombatWeapon) {
				hasACloseCombatWeapon=true;
				for(Player p:getEngine().players) {
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
		if(player.getCurrentWeapon() instanceof RangedWeapon) {
			if(!anyRangedWeaponHasAmmo || anotherCloseCombatWeaponCouldHit ||
					anyRangedWeaponHasMoreDamageThanCurWeapon || (anyRangedWeaponHasAmmo && ((RangedWeapon)player.getCurrentWeapon()).needsAmmo()))
				performSwitchWeaponAction=true;
		} else {
			if(currentCloseCombatWeaponCouldHit) {
				if(anotherCloseCombatWeaponCouldHitAndHasMoreDamage)
					performSwitchWeaponAction=true;
			} else {
				if(anotherCloseCombatWeaponCouldHit || anyRangedWeaponHasAmmo)
					performSwitchWeaponAction=true;
			}
		}

//Find a target
		MovingAnimationObject target = null;
		for(Player oP:getEngine().players) {
			if(oP!=player) {
				if(target==null) {
					target=oP;
				} else {
					boolean old_isClearHLOS = hasClearHorizontalLineOfSight(target);
					boolean cur_isClearHLOS = hasClearHorizontalLineOfSight(oP);
					if(cur_isClearHLOS) {
						if(old_isClearHLOS) {
	    					if(getVectorToPlayer(oP).getLength() < getVectorToPlayer(target).getLength())
	    						target=oP;
						} else {
							target=oP;
						}
					} else {
						if(old_isClearHLOS) {
							//keepOld
						} else {
	    					if(getVectorToPlayer(oP).getLength() < getVectorToPlayer(target).getLength())
	    						target=oP;
						}
					}
				}
			}
		}

		if(!hasACloseCombatWeapon && !anyRangedWeaponHasAmmo)target=null;//if player can not be hurt anyway, the player is not to pursuit

		if(target==null || !(anyRangedWeaponHasAmmo && hasClearHorizontalLineOfSight(target))) {//if the enemy can by shot, that is to prefer
    		for(CarePackage oP:getEngine().packages) {
    			if(isUsefulPackage(oP)) {
    				if(target==null) {
    					target=oP;
    				} else {
    					if(getVectorToPlayer(oP).getLength() < getVectorToPlayer(target).getLength())
    						target=oP;
    				}
    			}
    		}
		}

		if(player.getLifePs()<lifePs_inLastRound)target=null;//try to evade future damage by acting out

//		if(curWeap instanceof RangedWeapon && ((RangedWeapon) curWeap).needsAmmo()) {//try changing the weapon
//			for(int wi=0;wi<player.weapons.size();wi++) {
//        		if(!(player.weapons.get(wi) instanceof CloseCombatWeapon || !((RangedWeapon) player.weapons.get(wi)).needsAmmo()))
//        			player.switchToNextWeapon();
//        		else break;
//			}
//			curWeap = player.getCurrentWeapon();
//    		if(curWeap instanceof RangedWeapon && ((RangedWeapon) curWeap).needsAmmo()) {//Search for new Ammo
//				target=null;
//        		for(MovingAnimationObject oP:getEngine().packages) {
//        			boolean usefulP = false;
//        			for(Weapon w:player.weapons) {
//        				if(oP instanceof AmmoPackage) {
//            				if(w.getName().equals(((AmmoPackage)oP).ammoFor)) {
//            					usefulP=true;
//            					break;
//            				}
//        				} else if(oP instanceof WeaponPackage) {
//        					if(!w.equals(((WeaponPackage)oP).w)) {
//            					usefulP=true;
//            					break;
//            				}
//        				}
//        			}
//        			if(usefulP) {
//        				if(target==null) {
//        					target=oP;
//        				} else {
//        					boolean oldCollision = player.getMid().y>=target.getY()&&player.getMid().y<=target.getY()+target.getH();
//        					boolean curCollision = player.getMid().y>=oP.getY()&&player.getMid().y<=oP.getY()+oP.getH();
//        					if(curCollision&&!oldCollision) {
//        						target=oP;
//        					} else {
//        						Vector nearestPlayerVector = new Vector(2);
//        						nearestPlayerVector.set(0, player.getMid().x-target.getMid().x);
//        						nearestPlayerVector.set(1, player.getMid().y-target.getMid().y);
//        						Vector curPlayerVector = new Vector(2);
//        						curPlayerVector.set(0, player.getMid().x-oP.getMid().x);
//        						curPlayerVector.set(1, player.getMid().y-oP.getMid().y);
//            					if(Math.abs(curPlayerVector.getLength()) < Math.abs(nearestPlayerVector.getLength())) {
//            						target=oP;
//            					}
//        					}
//        				}
//        			}
//        		}
//    		}
//		}


//		boolean wantsToBeLookingLeft = target!=null?player.getMid().x > target.getMid().x:(UTIL.getRandomNr(0, 500)==1?!player.lookingLeft:player.lookingLeft);
//		if(wantsToBeLookingLeft && !player.lookingLeft) {
//			player.performLeftAction();
//		} else if(!wantsToBeLookingLeft && player.lookingLeft) {
//			player.performRightAction();
//		} else if(!clearLineOfSight) {
//			if(player.lookingLeft) {
//				player.performLeftAction();
//			} else {
//				player.performRightAction();
//			}
//		}
		boolean clearHLineOfSightToTarget = hasClearHorizontalLineOfSight(target);
		if(clearHLineOfSightToTarget) {
			if(target instanceof Player) {//anticipate future player positions in order to hit
				if(player.getCurrentWeapon() instanceof RangedWeapon) {
					if(player.isLookingLeft()) {
						if(target.getBounds().contains(((RangedWeapon)player.getCurrentWeapon()).getShotInitLocation()) ||
								((RangedWeapon)player.getCurrentWeapon()).getShotInitLocation().x > target.getX())
							performAttackAction=true;
						else
							performRightAction=true;
					} else {
						if(target.getBounds().contains(((RangedWeapon)player.getCurrentWeapon()).getShotInitLocation()) ||
								((RangedWeapon)player.getCurrentWeapon()).getShotInitLocation().x < target.getX()+target.getW())
							performAttackAction=true;
						else
							performLeftAction=true;
					}
				} else {
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
//			AnimationObject testP = new AnimationObject(new Rectangle((int)(player.getX()+(player.lookingLeft?-player.getW():player.getW())), (int)player.getY(), (int)player.getW(), (int)player.getH()+2), AnimationObject.RECT);
//            if(!(AnimationObject.collision(testP, getEngine().mapParticles)/* || testP.overlapingBoundsWi(getEngine().getH())*/))
//            	performUpAction=true;//TODO jump when the AnimationObject ends
//            else if()
			AnimationObject testP = new AnimationObject(new AERect(player.getX()+(player.isLookingLeft()?-2:2), player.getY(), player.getW(), player.getH()), AnimationObject.RECT);
			AnimationObject collP;
			if((collP=AnimationObject.collidesWithOne(testP, getEngine().mapParticles))!=null) {
				if(collP instanceof MapParticle && ((MapParticle)collP).isSolid) {
					if(player.isLookingLeft()) {
						performRightAction=true;
						performLeftAction=false;
					} else {
						performLeftAction=true;
						performRightAction=false;
					}
				}
			}
		} else {
			if(new AnimationObject(player.getX()+1,0,1,player.getW(), AnimationObject.RECT).overlapingBoundsRight(getEngine().getVirtualLimit_width()))
				performLeftAction=true;
			else if(new AnimationObject(player.getX()-1,0,1,player.getW(), AnimationObject.RECT).overlapingBoundsLeft()) {
				performRightAction=true;
			} else {
				if(target!=null) {
					if(target.getMidAsPoint().y<player.getMidAsPoint().y) {//is target above
						if(/*player.getV_Y()==0 && */canFreelyJump()) {
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
		//					if(target.getV_Y()<=5)
		//						performUpAction=true;
		//					else
		//						;
						}
		//				if(player.getV_Y()<0) {//flying upwards
		//					//s = 0,5 · a · t*t + vo · t + s0
		////					int s_y = 0.5 * player.getA_Y() * t*t + player.getV_Y() * t;// player.getV_Y()
		//					//TODO do those mathematically::
		//					double lowest_s_y = Integer.MIN_VALUE;//on screen actually highest
		//					for(double t=0;t<Integer.MAX_VALUE;t+=1) {
		//						double s_y = (0.5 * player.getA_Y() * t*t) + (player.getV_Y() * t) + (player.getY()+player.getH());
		//						if(lowest_s_y>s_y)lowest_s_y=s_y;
		//						else break;
		//					}
		//					int YPHWillReach = (int) (lowest_s_y);
		//					System.out.println(YPHWillReach);
		//					ArrayList<MapParticle> mapParticlesBelowReachP = new ArrayList<>();
		//					for(MapParticle mp:getEngine().mapParticles)
		//						if(mp.isSolid&&mp.getY()>YPHWillReach)
		//							mapParticlesBelowReachP.add(mp);
		//					double verticallyReachableDistance_left_x = 0;
		//					double verticallyReachableDistance_right_x = 0;
		//					for(double t=0;t<Integer.MAX_VALUE;t+=1) {//while the distance does not move down again
		//						double s_y = (0.5 * player.getA_Y() * t*t) + (player.getV_Y() * t) + (player.getY()+player.getH());
		////						double s_x_left = player.getV_X() + player.getStdHorizontailSpeedMax()*t;
		//						if(s_y<=lowest_s_y) {
		//							verticallyReachableDistance_left_x	= player.getX() + player.getV_X() + (-player.getStdHorizontailSpeedMax()*t);
		//							verticallyReachableDistance_right_x	= player.getX() + player.getH() + player.getV_X() + player.getStdHorizontailSpeedMax()*t;
		//						} else
		//							break;
		//					}
		//					System.out.println(verticallyReachableDistance_left_x);
		//					System.out.println(verticallyReachableDistance_right_x);
		//					ArrayList<MapParticle> reachableMapParticles = new ArrayList<>();
		//					for(MapParticle mp:mapParticlesBelowReachP)
		//						if(UTIL.isBetween(mp.getY(), verticallyReachableDistance_left_x, verticallyReachableDistance_right_x) ||
		//								UTIL.isBetween(mp.getY()+mp.getH(), verticallyReachableDistance_left_x, verticallyReachableDistance_right_x))
		//							reachableMapParticles.add(mp);
		//					MapParticle highestMapParticle=null;
		//					for(MapParticle mp:reachableMapParticles) {
		//						if(highestMapParticle==null||mp.getY()>highestMapParticle.getY()) {
		//							highestMapParticle=mp;
		//						} else if(mp.getY()==highestMapParticle.getY() && Math.abs(mp.getX()-target.getX())<Math.abs(highestMapParticle.getX()-target.getX())) {//if does not matter check for distance to target
		//							highestMapParticle=mp;
		//						}
		//					}
		//
		//					target = highestMapParticle;
		//					if(player.getMid().x > target.getMid().x)
		//						performLeftAction=true;
		//					else
		//						performRightAction=true;
		//				} else if(player.getV_Y()>0) {//falling
		//					ArrayList<MapParticle> mapParticlesBelowP = new ArrayList<>();
		//					for(MapParticle mp:getEngine().mapParticles)
		//						if(mp.isSolid&&mp.getY()>player.getY()+player.getH())
		//							mapParticlesBelowP.add(mp);
		//					ArrayList<MapParticle> reachableMapParticles = new ArrayList<>();
		//					for(MapParticle mp:mapParticlesBelowP) {
		//						double verticallyReachableDistance_left_x = 0;
		//						double verticallyReachableDistance_right_x = 0;
		//						for(double t=0;t<Integer.MAX_VALUE;t+=1) {
		//							double s_y = (0.5 * player.getA_Y() * t*t) + (player.getV_Y() * t) + (player.getY()+player.getH());
		////							double s_x_left = player.getV_X() + player.getStdHorizontailSpeedMax()*t;
		//							verticallyReachableDistance_left_x	= player.getX() + player.getV_X() + (-player.getStdHorizontailSpeedMax()*t);
		//							verticallyReachableDistance_right_x	= player.getX() + player.getH() + player.getV_X() + player.getStdHorizontailSpeedMax()*t;
		//							if(mp.getY()<s_y) {
		//								if(UTIL.isBetween(mp.getY(), verticallyReachableDistance_left_x, verticallyReachableDistance_right_x) ||
		//										UTIL.isBetween(mp.getY()+mp.getH(), verticallyReachableDistance_left_x, verticallyReachableDistance_right_x)) {
		//									reachableMapParticles.add(mp);
		//									break;
		//								}
		//							}
		//						}
		//					}
		//					MapParticle highestMapParticle=null;
		//					for(MapParticle mp:reachableMapParticles) {
		//						if(highestMapParticle==null||mp.getY()>highestMapParticle.getY()) {
		//							highestMapParticle=mp;
		//						} else if(mp.getY()==highestMapParticle.getY() && Math.abs(mp.getX()-target.getX())<Math.abs(highestMapParticle.getX()-target.getX())) {//if does not matter check for distance to target
		//							highestMapParticle=mp;
		//						}
		//					}
		//
		//					target = highestMapParticle;
		//					if(player.getMid().x > target.getMid().x)
		//						performLeftAction=true;
		//					else
		//						performRightAction=true;
		//				} else if(player.getV_Y()==0) {
		//					if(target.getV_Y()<=0) {
		////						ArrayList<MapParticle> mapParticlesAboveP = new ArrayList<>();
		////						for(MapParticle mp:getEngine().mapParticles)
		////							if(mp.isSolid&&mp.getY()<player.getY()+player.getH())
		////								mapParticlesAboveP.add(mp);
		////						double reachablePoint = Integer.MIN_VALUE;//on screen actually highest
		////						for(double t=0;t<Integer.MAX_VALUE;t+=1) {
		////							double s_y = (0.5 * player.getA_Y() * t*t) + (player.getV_Y() * t) + (player.getY()+player.getH());
		////							if(reachablePoint>s_y)reachablePoint=s_y;
		////							else break;
		////						}
		////						ArrayList<MapParticle> reachableAnimationObjects = new ArrayList<>();
		////						for(MapParticle mp:mapParticlesAboveP)
		////							if(mp.getY()>reachablePoint)
		////								reachableAnimationObjects.add(mp);
		////						MapParticle mp = null;
		////						
		////						if(hasClearVerticalLineOfSight(mp))
		////						else
		////							performUpAction=true;
		////							
		////						
		////						target=mp;
		//						performUpAction=true;
		//						if(player.getMid().x > target.getMid().x)
		//							performLeftAction=true;
		//						else
		//							performRightAction=true;
		//					} else {
		////						wait for it
		//					}
		//				}
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
					AnimationObject testP = new AnimationObject(new AERect(player.getX()+(player.isLookingLeft()?-2:2), player.getY(), player.getW(), player.getH()), AnimationObject.RECT);
					AnimationObject collP;
					if((collP=AnimationObject.collidesWithOne(testP, getEngine().mapParticles))!=null) {
						if(collP instanceof MapParticle && ((MapParticle)collP).isSolid) {
							if(player.isLookingLeft()) {
								performRightAction=true;
								performLeftAction=false;
							} else {
								performLeftAction=true;
								performRightAction=false;
							}
						}
					}
					performUpAction=true;
				}
			}
		}
		lifePs_inLastRound=player.getLifePs();
	}

	private boolean isUsefulPackage(CarePackage oP) {
		boolean usefulP = false;
		for(Weapon w:player.weapons) {
			if(oP instanceof AmmoPackage) {
				if(w.getName().equals(((AmmoPackage)oP).ammoFor)) {
					usefulP=true;
					break;
				}
			} else {//every other package is useful
				usefulP=true;
				break;
			}
		}
		return usefulP;
	}
	//	public Vector getNewPos(double t, double applied_v_x) {
//		Vector vector = new Vector(2);
//		vector.set(0, (0.5 * player.getA_Y() * t*t) + (player.getV_Y() * t) + (player.getY()));
//		vector.set(1, player.getX() + player.getV_X() + applied_v_x*t);
//		return vector;
//	}
	public AEVector getVectorToPlayer(AnimationObject p) {
		AEVector vector = new AEVector(2);
		vector.set(0, player.getMidAsPoint().x-p.getMidAsPoint().x);
		vector.set(1, player.getMidAsPoint().y-p.getMidAsPoint().y);
		return vector;
	}
	public boolean hasClearHorizontalLineOfSight(AnimationObject p) {
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
    			for(MapParticle mp:getEngine().mapParticles) {
    				if(mp.isSolid && playerMid_y>=mp.getY()&&playerMid_y<=mp.getY()+mp.getH()   &&
    						(UTIL.isBetween(mp.getMidAsPoint().x, nearesPlayer_mid_x, playerMid_x) ||
    						UTIL.isBetween(mp.getMidAsPoint().x, playerMid_x, nearesPlayer_mid_x))) {
    					clearLineOfSight=false;
    					break;
    				}
    			}
    		}
		}
		return clearLineOfSight;
	}
	public boolean hasClearVerticalLineOfSight(AnimationObject p) {
		if(p==null)return false;
		boolean clearLOS = player.getMidAsPoint().x>=p.getX()&&player.getMidAsPoint().x<=p.getX()+p.getW();
		if(clearLOS) {
			for(MapParticle mp:getEngine().mapParticles) {
				if(mp.isSolid && p!=mp && player.getMidAsPoint().x>=mp.getX()&&player.getMidAsPoint().x<=mp.getX()+mp.getW()   &&
						(UTIL.isBetween(mp.getMidAsPoint().y, p.getMidAsPoint().y, player.getMidAsPoint().y) ||
						UTIL.isBetween(mp.getMidAsPoint().y, player.getMidAsPoint().y, p.getMidAsPoint().y))) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean canFreelyJump() {
		double lowest_s_y = Integer.MAX_VALUE;//on screen actually highest
		for(double t=0;t<Integer.MAX_VALUE;t+=0.1) {
			double s_y = (0.5 * player.getA_Y() * t*t) + (-player.getStdVerticalBoostSpeed() * t) + (player.getY()+player.getH());
			if(s_y < lowest_s_y)lowest_s_y=s_y;
			else break;
		}
		int YPHWillReach = (int) (lowest_s_y);
		for(MapParticle mp:getEngine().mapParticles) {
			boolean isAbove = player.getMidAsPoint().x>=mp.getX()&&player.getMidAsPoint().x<=mp.getX()+mp.getW() && player.getY()+player.getH() > mp.getY();
			if(mp.isSolid && isAbove && UTIL.isBetween(mp.getY(), YPHWillReach, (player.getY()+player.getH())-2)) {
				return false;
			}
		}
		return true;
	}
}