package realistic_game;

import realistic_game.control_units.PlayerControlUnit;
import realistic_game.engines.Realistic_Game_Engine;
import util.UTIL;
import util.animation.engine.MovingAnimationObject;
import util.animation.pipeline.AnimationObject;
import util.animation.pipeline.AnimationPipeline;
import util.animation.pipeline.StandardAODrawer;
import util.animation.util.AEColor;
import util.animation.util.AEPoint;
import util.animation.util.AERect;
import util.animation.util.AESize;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends MovingAnimationObject {
	public Realistic_Game_Engine engine;
	public static AESize getStdSize(AESize frameSize) {
		return new AESize((frameSize.getHeight()*0.06)/5, frameSize.getHeight()*0.06);
	}

	private int curWeapon = 0;
	public final ArrayList<Weapon> weapons = new ArrayList<>();
	private void switchToNextWeapon() {
		curWeapon = UTIL.returnIntoBounds(curWeapon+1, weapons.size());
	}
	public Weapon getCurrentWeapon() {
		if(curWeapon>=0&&curWeapon<weapons.size()) {
			return weapons.get(curWeapon);
		}
		return null;
	}
	ArrayList<Wearable> wearables = new ArrayList<>();
	public void addWearable(Wearable newWear) {
		newWear.setWearer(this);
		Iterator<Wearable> wear_iter = wearables.iterator();
		while(wear_iter.hasNext()) {
			Wearable wear=wear_iter.next();
			if(!wear.allowNewWearable(newWear)) {
				wear_iter.remove();
			}
		}
		wearables.add(newWear);
	}
	private double lifePs=100; public double getLifePs() { return lifePs; }
	public void setLifePs(double d) {
		lifePs = d;
	}
	public boolean lookingLeft = false; public boolean isLookingLeft() { return lookingLeft; }
    public double additioFX = 0;
    public double additioFY = 0;
	public Player(double x, double y, int w, int h, final AEColor c, Realistic_Game_Engine engine_g) {
		super(x, y, 0, 0, 0, 0, w, h, OVAL, null);
		playerColor=c;
		drawParam=new StandardAODrawer() {
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
				super.draw(o, pipe, c);
				AERect toDrawAt = pipe.getDrawBoundsFor(o);
				pipe.getDrawer().fillOval(AEColor.BLACK, new AERect((toDrawAt.getX()+toDrawAt.getWidth()*(isLookingLeft()?0.25:0.75)) - Math.max(toDrawAt.getWidth()/2,2)/2, (toDrawAt.getY()+toDrawAt.getHeight()*0.25) - Math.max(toDrawAt.getWidth()/2,2)/2, Math.max(toDrawAt.getWidth()/2,2), Math.max(toDrawAt.getWidth()/2,2)));
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
//		weapons.add(getWeapon_ZAT_NIK_TEL());
//		addWearable(new ProtectiveVest());
//		weapons.add(AnimatedCloseCombatWeapon.getKnife());
//		weapons.add(RangedWeapon.getWeapon_PISTOL(engine.getFrameSize().getSize()));
	}
	private AEColor playerColor;
	public AEColor getPlayerColor() {
		return playerColor;
	}
	@Override public void move(int ticksPerSecond) {
		setF_X(getF_X()+additioFX);
		setF_Y(getF_Y()+additioFY);
		super.move(ticksPerSecond);
		Weapon curWeap = getCurrentWeapon();
		if(curWeap instanceof AnimatedCloseCombatWeapon)
			((AnimatedCloseCombatWeapon) curWeap).stepAnimation();
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
		if(getV_X()>-getStdHorizontailSpeedMax())
			setV_X(getV_X()-getStdHorizontailSpeedChange());
//			setV_X(-getStdHorizontailSpeedMax());
	}
	private void performRightAction() {
		lookingLeft = false;
		for(Wearable w:wearables)if(w.rightAction())return;
		if(getV_X()<getStdHorizontailSpeedMax())
			setV_X(getV_X()+getStdHorizontailSpeedChange());
//			setV_X(getStdHorizontailSpeedMax());
	}
	public final double getStdHorizontailSpeedChange() {return engine.getLimitPerc_H(3.7);}
	public final double getStdHorizontailSpeedMax() {return engine.getLimitPerc_H(3.7)*5;}
	private void performAttackAction() {
//	    draw(g);//to update the weapon location
	    getCurrentWeapon().updatePosition();
		for(Wearable w:wearables)if(w.attackAction())return;
		attack();
	}
	public void gotHit(Shot shot) {
		for(Wearable w:wearables)if(w.gotHitAction(shot, engine.particles))return;
		lifePs = getLifePs() - shot.damage;
		shot.startExplosion(engine.particles);
		setV_X(getV_X() + (shot.getV_X()<0?-Math.abs(shot.getV_X())*shot.blowbackMultipler:Math.abs(shot.getV_X())*shot.blowbackMultipler));
	}


	public PlayerControlUnit controlUnit;
	public void controlUnit_compute() {
        if(controlUnit.canCalculateNewActions()) {
        	controlUnit.calculateNewActions();
    		if(controlUnit.isUpAction())
    			performUpAction();
    		if(controlUnit.isLeftAction())
    			performLeftAction();
    		if(controlUnit.isRightAction())
    			performRightAction();
    		if(controlUnit.isAttackAction())	
    			performAttackAction();
    		if(controlUnit.isSwitchAction())
    			switchToNextWeapon();
        }
	}





	public boolean computeStops(ArrayList<? extends MovingAnimationObject> mapParticles) {
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
        return true;
	}
}