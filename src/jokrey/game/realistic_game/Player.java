package jokrey.game.realistic_game;

import jokrey.game.realistic_game.care_package.WeaponPackage;
import jokrey.game.realistic_game.control_units.PlayerControlUnit;
import jokrey.game.realistic_game.engines.PlayerStats;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.engine.MovingAnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.pipeline.StandardAODrawer;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AEPoint;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends MovingAnimationObject {
	public Realistic_Game_Engine engine;
	public final PlayerStats stats = new PlayerStats();
	public static AESize getStdSize(AESize frameSize) {
		return new AESize((frameSize.getHeight()*0.06)/5, frameSize.getHeight()*0.06);
	}

	private int curWeapon = 0;
	public final ArrayList<Weapon> weapons = new ArrayList<>();
	private void switchToNextWeapon() {
		int toReturn = curWeapon+1;
		if(weapons.size()>0) {
			while(toReturn<0)
				toReturn += 	weapons.size();
			while(toReturn>=weapons.size())
				toReturn -= 	weapons.size();
		}
		curWeapon = toReturn;
	}
	public Weapon getCurrentWeapon() {
		if(curWeapon>=0&&curWeapon<weapons.size()) {
			return weapons.get(curWeapon);
		} else if(!weapons.isEmpty()) {
            curWeapon=0;
            return getCurrentWeapon();
        }
		return null;
	}
	public final ArrayList<Wearable> wearables = new ArrayList<>();
	public void addWearable(Wearable newWear) {
		newWear.setWearer(this);
        wearables.removeIf(wear -> wear.newWearableAddedRemoveThis(newWear));
		wearables.add(newWear);
	}
	private double lifePs=Integer.MIN_VALUE; public double getLifePs() { return lifePs; }
	public void setLifePs(double d) {
		lifePs = d;
	}
	public boolean lookingLeft = false; public boolean isLookingLeft() { return lookingLeft; }
    public double additioFX = 0;
    public double additioFY = 0;
	public Player(int w, int h, Realistic_Game_Engine engine_g) {
		super(0, 0, 0, 0, 0, 0, w, h, OVAL, null);
		drawParam=new StandardAODrawer() {
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
				super.draw(o, pipe, playerColor);
				AERect toDrawAt = pipe.getDrawBoundsFor(o);
				//draw the eye
				pipe.getDrawer().fillOval(AEColor.BLACK, new AERect((toDrawAt.getX()+toDrawAt.getWidth()*(isLookingLeft()?0.25:0.75)) - Math.max(toDrawAt.getWidth()/2,2)/2, (toDrawAt.getY()+toDrawAt.getHeight()*0.25) - Math.max(toDrawAt.getWidth()/2,2)/2, Math.max(toDrawAt.getWidth()/2,2), Math.max(toDrawAt.getWidth()/2,2)));
				//draw the life line
				pipe.getDrawer().drawLine(AEColor.RED.brighter(), new AEPoint(toDrawAt.getX()+toDrawAt.getWidth()/2, toDrawAt.getY()), new AEPoint(toDrawAt.getX()+toDrawAt.getWidth()/2, toDrawAt.getY()+ toDrawAt.getHeight()*getLifePs()/100));
				Weapon curWeap = getCurrentWeapon();
				if(curWeap!=null) {
					curWeap.weaponHolder=Player.this;
					curWeap.draw(pipe);
				}

				Iterator<Wearable> wearIter = wearables.iterator();
				while(wearIter.hasNext()) {
					Wearable wearable=wearIter.next();
					if(wearable.imDeadPleaseKillMe())
						wearIter.remove();
					else
						wearable.drawWearable(pipe);
				}
			}
		};
		engine=engine_g;
		setF_Y(getStdGravitation());
	}
	private AEColor playerColor=null;
	public void setPlayerColor(AEColor color) {
		if(playerColor==null) {
			playerColor = color;
			stats.color=color;
		} else
		    throw new IllegalStateException("cannot be done twice");
	}
	public AEColor getPlayerColor() {
		return playerColor;
	}
	@Override public void move(int ticksPerSecond) {
		setF_X(getF_X()+additioFX);
		setF_Y(getF_Y()+additioFY);
		super.move(ticksPerSecond);
		Weapon curWeap = getCurrentWeapon();
		if(curWeap instanceof AnimatedCloseCombatWeapon)
			((AnimatedCloseCombatWeapon) curWeap).stepAnimation(ticksPerSecond);
		setF_X(getF_X()-additioFX);
		setF_Y(getF_Y()-additioFY);
	}
	public void attack() {
		Weapon curWeap = getCurrentWeapon();
		if(curWeap instanceof RangedWeapon) {
			if(((RangedWeapon) curWeap).shoot(engine.shots))
				setV_X(getV_X() + (((RangedWeapon) curWeap).getRecoil() * (isLookingLeft()?1:-1)));
		} else if(curWeap instanceof AnimatedCloseCombatWeapon) {
			((AnimatedCloseCombatWeapon) curWeap).startAttack();
		}
	}
	private void performUpAction() {
		for(Wearable w:wearables)if(w.upAction())return;
    	if(getV_Y()==0) {
    		setV_Y(-getStdVerticalBoostSpeed());
    	}
	}
	public final double getStdVerticalBoostSpeed() {return engine.getLimitPerc_H(44);}
	public final double getStdGravitation() {return engine.getLimitPerc_H(50);}
	private void performLeftAction() {
		lookingLeft = true;
		for(Wearable w:wearables)if(w.leftAction())return;
		if(getV_X()>-getStdHorizontalSpeedMax())
			setV_X(getV_X()- getStdHorizontalSpeedChange());
	}
	private void performRightAction() {
		lookingLeft = false;
		for(Wearable w:wearables)if(w.rightAction())return;
		if(getV_X()< getStdHorizontalSpeedMax())
			setV_X(getV_X()+ getStdHorizontalSpeedChange());
	}
	public final double getStdHorizontalSpeedChange() {return engine.getLimitPerc_H(4);}
	public final double getStdHorizontalSpeedMax() {return engine.getLimitPerc_H(4)*5;}
	private void performAttackAction() {
		Weapon curWeap = getCurrentWeapon();
	    if(curWeap!=null)
	    	curWeap.updatePosition();
		for(Wearable w:wearables)if(w.attackAction())return;
		attack();
	}


    private void performDiscardAction() {
	    throwAwayWeapon(getCurrentWeapon());
    }

    public void throwAwayWeapon(Weapon weapon) {
        if(!weapons.isEmpty() && weapons.remove(weapon)) {
            WeaponPackage pack = new WeaponPackage(weapon, engine.getVirtualBoundaries());
            pack.w.weaponHolder=null;
            engine.packages.add(pack);
            pack.setY(getY());
            pack.setV_Y(-getStdHorizontalSpeedMax()*2);
            pack.setF_Y(getStdGravitation());
            if(isLookingLeft()) {
                pack.setX(getX()-pack.getW()*1.1);
                pack.setV_X(-getStdHorizontalSpeedMax()/2 + getV_X());
            } else {
                pack.setX(getX()+getW()+pack.getW()*0.1);
                pack.setV_X(getStdHorizontalSpeedMax()/2 + getV_X());
            }
            switchToNextWeapon();
        }
    }


    public void gotHit(Shot shot) {
		for(Wearable w:wearables)if(w.gotHitAction(shot, engine.particles))return;
		lifePs = getLifePs() - shot.damage;
		shot.executeHitAnimation(engine.particles);
		setV_X(getV_X() + (shot.getV_X()<0?-Math.abs(shot.getV_X())*shot.blowbackMultipler:Math.abs(shot.getV_X())*shot.blowbackMultipler));
	}


	public PlayerControlUnit controlUnit;
	public void controlUnit_compute() {
		if( controlUnit.calculateNewActions(this, engine) ) {
            if( controlUnit.isUpAction() )
                performUpAction();
            if( controlUnit.isLeftAction() )
                performLeftAction();
            if( controlUnit.isRightAction() )
                performRightAction();
            if( controlUnit.isAttackAction() )
                performAttackAction();
            if( controlUnit.isDiscardAction() )
                performDiscardAction();
			if( controlUnit.isSwitchAction() )
				switchToNextWeapon();
        }
	}



    public void computeStops(ArrayList<? extends MovingAnimationObject> mapParticles) {
        for(MovingAnimationObject mr:mapParticles) {
    		if(!(mr instanceof MapParticle && !((MapParticle)mr).isSolid) && getBounds().intersects(mr.getBounds())) {
    			double leftOverlap=getX()+getW()-mr.getX();
    			double rightOverlap=mr.getX()+mr.getW()-getX();
    			double topOverlap=getY()+getH()-mr.getY();
    			double botOverlap=mr.getY()+mr.getH()-getY();

    			double smallestOverlap=Double.MAX_VALUE;
    			double shiftX=0;
    			double shiftY=0;

    			int l_r_t_b = -1;
    			if(leftOverlap<smallestOverlap) {
    				smallestOverlap=leftOverlap;
    				shiftX=-leftOverlap;
    				shiftY=0;
    				l_r_t_b=0;
    			}if(rightOverlap<smallestOverlap) {
    				smallestOverlap=rightOverlap;
    				shiftX=rightOverlap;
    				shiftY=0;
    				l_r_t_b=1;
    			}if(topOverlap<smallestOverlap) {
    				smallestOverlap=topOverlap;
    				shiftX=0;
    				shiftY=-topOverlap;
    				l_r_t_b=2;
    			}if(botOverlap<smallestOverlap) {
    				smallestOverlap=botOverlap;
    				shiftX=0;
    				shiftY=botOverlap;
    				l_r_t_b=3;
    			}

    			if(l_r_t_b==0 || l_r_t_b==1) {
    				setV_X(0);
//        				double oldply_vx=player_1.getV_X();
//        				player_1.setV_X(mr.getV_X());
//    			    	mr.setV_X(oldply_vx);
//        				if(Math.abs(mr.getV_X())<=22)
//            				mr.setV_X(mr.getV_X()<0?-22:22);
    			} else if(l_r_t_b==2 || l_r_t_b==3) {
    				setV_Y(0);
    				if(getV_X()<0)
    					setF_X(66);
    				if(getV_X()>0)
    					setF_X(-66);
    			} else
	                setF_X(0);

    			setX(getX()+shiftX);
    			setY(getY()+shiftY);
    		}
        }
    }
}