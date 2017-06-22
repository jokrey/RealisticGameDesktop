package realistic_game;

import util.UTIL;
import util.animation.engine.AnimationEngine;
import util.animation.pipeline.AnimationObject;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AEColor;
import util.animation.util.AEPoint;
import util.animation.util.AERect;
import util.animation.util.AESize;

public abstract class AnimatedCloseCombatWeapon extends CloseCombatWeapon {
	public AnimatedCloseCombatWeapon(int w, int h) {
		super(w, h);
	}
	public abstract double getInitDamage();
	public abstract double getAnimSpeed();
	public void hitParticle(@SuppressWarnings("unused") AnimationObject p) {
		curPosInAnimation=0;
	}
	double curPosInAnimation = 0;

	double lastShot = System.nanoTime()/1e9;
	void startAttack() {
		if(!isAttacking() && System.nanoTime()/1e9-lastShot>getDelay()) {
			curPosInAnimation=0.00001;
			lastShot=System.nanoTime()/1e9;
		}
	}
	public boolean isAttacking() {return curPosInAnimation>0;}
	void stepAnimation(int ticksPerSecond) {
		if(isAttacking())
			curPosInAnimation+=(getAnimSpeed()*(1.0/ticksPerSecond));
		if(curPosInAnimation>100)
			curPosInAnimation=0;
	}
	@Override public double getDamage() {
		return isAttacking()?getInitDamage():0;
	}

    public static AnimatedCloseCombatWeapon getKnife() {
    	return new AnimatedCloseCombatWeapon(15, 15) {
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+(weaponHolder.getH()/2 - getH()/2));
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()+1):weaponHolder.getW()+1));
			}
			@Override public String getName() {	return "KNIFE";}
			@Override public double getDelay() {return 2.5;}
			@Override public double getAnimSpeed() {return 333;}
			@Override public double getInitDamage() {return 22;}
			@Override public double getBlowbackMultipler() {return 66;}
//			@Override public void intersectsWith(Particle p) {}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				double gripL = (int) (Math.min(drawWeapRect.getWidth(), drawWeapRect.getHeight())*0.25);
				double bladeL = (int) (Math.min(drawWeapRect.getWidth(), drawWeapRect.getHeight())*0.75);
				int theta = (int) (90 * (curPosInAnimation/100)) - 90;
//				x0 + r cos theta, y0 + r sin theta
				int x0 = (int) drawWeapRect.x;
				int y0 = (int) (drawWeapRect.y+drawWeapRect.getHeight());
				if(weaponHolder==null||weaponHolder.isLookingLeft()) {
					x0+=drawWeapRect.getWidth();
					theta=(int) (270 - (90 * (curPosInAnimation/100)));
				}
				
				int x_grip = (int)(x0 + gripL * Math.cos(Math.toRadians(theta)));
				int y_grip = (int)(y0 + gripL * Math.sin(Math.toRadians(theta)));
				int x_bladeL = (int)(x0 + (gripL+bladeL) * Math.cos(Math.toRadians(theta)));
				int y_bladeL = (int)(y0 + (gripL+bladeL) * Math.sin(Math.toRadians(theta)));
				pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x0, y0), new AEPoint(x_bladeL, y_bladeL));
				pipe.getDrawer().drawLine(AEColor.DARK_GRAY, new AEPoint(x0, y0), new AEPoint(x_grip, y_grip));
			}
		};
    }
    public static AnimatedCloseCombatWeapon getLightsaber(final AEColor lsClr) {
    	return new AnimatedCloseCombatWeapon(1, 1) {
    		{
    			drawParam=lsClr;
    		}
			@Override public void intersectsWith(AnimationObject p) {
				if(isAttacking()) {
					if(p instanceof Shot)
						((Shot)p).setV_X(-((Shot)p).getV_X());
					else if(p instanceof AnimatedCloseCombatWeapon) {
						if(((AnimatedCloseCombatWeapon)p).isAttacking())
							curPosInAnimation=0;
						((AnimatedCloseCombatWeapon)p).curPosInAnimation=0;
					}
//					curPosInAnimation=0;
				}
			}
			@Override public void updatePosition() {
        		setW(weaponHolder.getH()*0.66);
        		setH(weaponHolder.getH()*0.66);
        		setY(weaponHolder.getY()+(weaponHolder.getH()/2 - getH()*0.7));
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()+2):weaponHolder.getW()+2));
			}
			@Override public String getName() {	return "LIGHTSABER";}
			@Override public double getDelay() {return 0.4;}
			@Override public double getAnimSpeed() {return 333;}
			@Override public double getInitDamage() {return 44;}
			@Override public double getBlowbackMultipler() {return 22;}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
//				g.setStroke(new BasicStroke(2));
				double gripL = (int) (Math.min(drawWeapRect.getWidth(), drawWeapRect.getHeight())*0.1);
				double bladeL = (int) (Math.min(drawWeapRect.getWidth(), drawWeapRect.getHeight())*0.75);
				int theta = (int) (90 * (curPosInAnimation/100)) - 90;
//				x0 + r cos theta, y0 + r sin theta
				int x0 = (int) drawWeapRect.x;
				int y0 = (int) (drawWeapRect.y+drawWeapRect.getHeight());
				if(weaponHolder==null||weaponHolder.isLookingLeft()) {
					x0+=drawWeapRect.getWidth();
					theta=(int) (270 - (90 * (curPosInAnimation/100)));
				}

				int x_grip = (int)(x0 + gripL * Math.cos(Math.toRadians(theta)));
				int y_grip = (int)(y0 + gripL * Math.sin(Math.toRadians(theta)));
				int x_bladeL = (int)(x0 + (gripL+bladeL) * Math.cos(Math.toRadians(theta)));
				int y_bladeL = (int)(y0 + (gripL+bladeL) * Math.sin(Math.toRadians(theta)));
				pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(x0, y0), new AEPoint(x_bladeL, y_bladeL));
				pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x0, y0), new AEPoint(x_grip, y_grip));
			}
		};
    }
    public static AnimatedCloseCombatWeapon getForceLightening(final AESize frameSize) {
    	return new AnimatedCloseCombatWeapon(1, 1) {
    		{drawParam=new AEColor(255,0,255,255);}
			@Override public void updatePosition() {
        		setW((weaponHolder.getH()*0.66)*3);
        		setH(weaponHolder.getH()*0.66);
        		setY(weaponHolder.getY()+(weaponHolder.getH()/2 - getH()/2));
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()+2):weaponHolder.getW()+2));
			}
			@Override public void hitParticle(AnimationObject p) {
				if(p instanceof Player) {
					((Player)p).addWearable(Wearable.getWearable_ForceLighteningEffect());
				}
			}
			@Override public String getName() {	return "FORCE_LIGHTENING";}
			@Override public double getDelay() {return 0.01;}
			@Override public double getAnimSpeed() {return 888;}
			@Override public double getInitDamage() {return 0.25;}
			@Override public double getBlowbackMultipler() {return 0;}
			@Override public void drawWeap(AnimationPipeline pipe) {
				if(weaponHolder==null)return;
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				AERect drawWeapHolderRect = pipe.getDrawBoundsFor(weaponHolder);
				int size = (int) (frameSize.getHeight()*0.01);
				int x = (int) (drawWeapHolderRect.x+(weaponHolder.isLookingLeft()?-(size+2):drawWeapHolderRect.getWidth()+2));
				int y = (int) (drawWeapHolderRect.y+drawWeapHolderRect.getHeight()/2);
//				g.setStroke(new BasicStroke(2));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {
					pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x, y+size), new AEPoint(x, y));
					pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x, y+size), new AEPoint(x+size*0.2, y+4));
					pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x, y+size), new AEPoint(x+(size-size*0.6), y+size/2));
				} else {
					pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x+size, y+size), new AEPoint(x+size, y));
					pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x+size, y+size), new AEPoint(x+(size-size*0.2), y+4));
					pipe.getDrawer().drawLine(AEColor.LIGHT_GRAY, new AEPoint(x+size, y+size), new AEPoint(x+(size*0.6), y+size/2));
				}
				if(isAttacking()) {
					for(int i=0;i<5;i++)
						pipe.getDrawer().drawOval(AEColor.GRAY, new AERect(drawWeapRect.x+UTIL.getRandomNr(0, drawWeapRect.getWidth()), drawWeapRect.y+UTIL.getRandomNr(0, drawWeapRect.getHeight()), 2, 2));
				}
			}
		};
    }
}