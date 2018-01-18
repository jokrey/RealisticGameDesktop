package jokrey.game.realistic_game;

import util.UTIL;
import jokrey.utilities.animation.engine.LimitRangeMovingAnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AEPoint;
import jokrey.utilities.animation.util.AERect;

import java.awt.*;
import java.util.ArrayList;

public abstract class Wearable {
//	public static final int TYPE_BODY = 0;
//	public static final int TYPE_FOOT = 1;
//	public static final int TYPE_ALL = 2;
	private Player wearer = null;
	Player getWearer(){return wearer;}
	double activatedAt;
	double getSecondsSinceActivation() {return System.nanoTime()/1e9-activatedAt;}
	void setWearer(Player p) {
		wearer=p;
		activatedAt=System.nanoTime()/1e9;
	}
	
//	final int type;
//	public Wearable(int type_g) {
//		type=type_g;
//	}
	abstract String getName();
	abstract boolean allowNewWearable(Wearable newWear);
	void drawWearable(AnimationPipeline pipe) {
		AERect toDrawAt = pipe.getDrawBoundsFor(getWearer());
		drawWearable(pipe, toDrawAt.x,toDrawAt.y,toDrawAt.getWidth(),toDrawAt.getHeight());
	}
	public abstract void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h);

	//TO OVERRIDE
	boolean imDeadPleaseKillMe(){return false;}
	boolean attackAction(){return false;}
	boolean upAction(){return false;}
	boolean leftAction(){return false;}
	boolean rightAction(){return false;}
	@SuppressWarnings("unused")
	boolean gotHitAction(Shot s, ArrayList<LimitRangeMovingAnimationObject> particles){return false;}



	public static Wearable getWearable_ProtectiveVest() {
		return new Wearable() {
			@Override String getName() {
				return "VEST";
			}
			@Override boolean allowNewWearable(Wearable newWear){
	    		if(newWear.getName().equals(getName())) {
	    			bulletProtectionPercent = 100;
	        		return false;
	    		} else
	    			return true;
	    	}

	    	double bulletProtectionPercent = 100;
	    	@Override boolean gotHitAction(Shot shot, ArrayList<LimitRangeMovingAnimationObject> particles) {
	    		getWearer().setLifePs(getWearer().getLifePs() - (shot.damage - shot.damage * (bulletProtectionPercent/100)));
	    		bulletProtectionPercent-=shot.damage;
				shot.startExplosion(particles);
				getWearer().setV_X(getWearer().getV_X() + (shot.getV_X()<0?-Math.abs(shot.getV_X())*shot.blowbackMultipler:Math.abs(shot.getV_X())*shot.blowbackMultipler) / 2);
				return true;
	    	}
	    	@Override boolean imDeadPleaseKillMe() {
	    		return bulletProtectionPercent<=0;
	    	}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				pipe.getDrawer().fillHalfOval(AEColor.DARK_GRAY, new AERect(x, y-(h*0.15), w, h), 0);
			}
		};
	}
	public static Wearable getWearable_DoubleJump() {
		return new Wearable() {
			@Override String getName() {
				return "DOUBLE_JUMP";
			}
			@Override boolean allowNewWearable(Wearable newWear){
                return !newWear.getName().equals(getName());
	    	}

	    	boolean allreadyDoubleJumped=false;
	    	@Override boolean upAction() {
	    		if(getWearer().getV_Y()==0) {
	    			getWearer().setV_Y(-getWearer().getStdVerticalBoostSpeed());
	        		allreadyDoubleJumped=false;
		    		return true;
	    		} else if(!allreadyDoubleJumped) {
	    			getWearer().setV_Y(-getWearer().getStdVerticalBoostSpeed());
	        		allreadyDoubleJumped=true;
		    		return true;
	    		} else {
	    			return false;
	    		}
	    	}
	    	@Override boolean imDeadPleaseKillMe() {return false;}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
//				g.setColor(getWearer()==null?Color.gray:((Color) getWearer().drawParam).darker());
				pipe.getDrawer().fillHalfOval(AEColor.LIGHT_GRAY, new AERect(x, y+(h-h*0.32), w, h*0.3), 0);
			}
		};
	}
	public static Wearable getWearable_SpeedBoost() {
		return new Wearable() {
			@Override String getName() {
				return "SPEED_BOOST";
			}
			@Override boolean allowNewWearable(Wearable newWear){
                return !newWear.getName().equals(getName());
	    	}

	    	@Override boolean upAction() {
	    		if(getWearer().getV_Y()==0) {
	    			getWearer().setV_Y(-getWearer().getStdVerticalBoostSpeed()*1.6);
		    		return true;
	    		} else {
	    			return false;
	    		}
	    	}
	    	@Override boolean leftAction() {
	    		getWearer().lookingLeft = true;
	    		getWearer().setV_X(getWearer().getV_X()-(getWearer().getStdHorizontailSpeedChange()*1.6));
	    		if(getWearer().getV_X()<=-getWearer().getStdHorizontailSpeedMax()*1.6)
	    			getWearer().setV_X(-getWearer().getStdHorizontailSpeedMax()*1.6);
	    		return true;
	    	}
	    	@Override boolean rightAction() {
	    		getWearer().lookingLeft = false;
	    		getWearer().setV_X(getWearer().getV_X()+(getWearer().getStdHorizontailSpeedChange()*1.6));
	    		if(getWearer().getV_X()>=getWearer().getStdHorizontailSpeedMax()*1.6)
	    			getWearer().setV_X(getWearer().getStdHorizontailSpeedMax()*1.6);
	    		return true;
	    	}
	    	@Override boolean imDeadPleaseKillMe() {return false;}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
//				g.setColor(getWearer()==null?Color.gray:((Color) getWearer().drawParam).brighter());
				pipe.getDrawer().fillHalfOval(AEColor.GRAY, new AERect(x, y+(h-h*0.32), w, h*0.3), 0);
			}
		};
	}
	public static Wearable getWearable_JetPack() {
		return new Wearable() {
			@Override String getName() {
				return "JET_PACK";
			}
			@Override boolean allowNewWearable(Wearable newWear){
	    		if(newWear.getName().equals(getName())) {
	    			fuel+=100;
	        		return false;
	    		} else
	    			return true;
	    	}

	    	int fuel=100;
	    	double lastBoost = System.nanoTime()/1e9;
	    	@Override boolean upAction() {
	    		if(System.nanoTime()/1e9-lastBoost>0.2) {
	    			lastBoost = System.nanoTime()/1e9;
	    			if(getWearer().getV_Y()!=0) {
		    			getWearer().setV_Y(getWearer().getV_Y()-getWearer().getStdGravitation()/4);
		                Rectangle bnds = new Rectangle((int) (getWearer().getX()+(getWearer().isLookingLeft()?getWearer().getW():-getWearer().getW())), (int)(getWearer().getY()+(getWearer().getH()*0.15)), (int)getWearer().getW(), (int)getWearer().getH()/2);
		                LimitRangeMovingAnimationObject.startExplosion(getWearer().engine.particles, 9, new AEPoint(bnds.x+bnds.width/2, bnds.y+bnds.height),
		                		LimitRangeMovingAnimationObject.getExplosionBnds(new AEPoint(bnds.x+bnds.width/2, bnds.y+bnds.height+99), 44,222, AnimationObject.RECT),
								90,new AEColor(255, 255, 0, 0), new AEColor(255, 255, 0, 0).brighter(), new AEColor(255, 255, 0, 0).brighter(), new AEColor(255, 255, 0, 0).brighter());
		    			fuel--;
			    		return true;
	    			}
	    		}
				return false;
	    	}
	    	@Override boolean imDeadPleaseKillMe() {return fuel<=0;}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				pipe.getDrawer().fillHalfOval(AEColor.DARK_GRAY, new AERect(x+(getWearer()==null? 0 : getWearer().isLookingLeft()?w:-w), y+(h*0.15), w, h), 1);
			}
		};
	}


	public static Wearable getWearable_ZattedEffect() {
		return new Wearable() {
			@Override String getName() {
				return "ZATTED";
			}
			@Override boolean allowNewWearable(Wearable newWear){
	    		if(newWear.getName().equals(getName())) {
	    			if(getWearer().getLifePs()>33)getWearer().setLifePs(33);
	    			else getWearer().setLifePs(-1);
					activatedAt=System.nanoTime()/1e9;
	        		return false;
	    		} else
	    			return true;
	    	}
	    	@Override boolean imDeadPleaseKillMe() {
	    		return getSecondsSinceActivation()>8;
	    	}
	    	@Override boolean leftAction() {
	    		getWearer().setV_X(getWearer().getV_X()-(getWearer().getStdHorizontailSpeedChange()/3));
	    		if(getWearer().getV_X()<=-getWearer().getStdHorizontailSpeedMax()/2)
	    			getWearer().setV_X(-getWearer().getStdHorizontailSpeedMax()/2);
	    		return true;
	    	}
	    	@Override boolean rightAction() {
	    		getWearer().setV_X(getWearer().getV_X()+(getWearer().getStdHorizontailSpeedChange()/3));
	    		if(getWearer().getV_X()>=getWearer().getStdHorizontailSpeedMax()/2)
	    			getWearer().setV_X(getWearer().getStdHorizontailSpeedMax()/2);
	    		return true;
	    	}
	    	@Override boolean upAction() {
	        	if(getWearer().getV_Y()==0) {
	        		getWearer().setV_Y(-(getWearer().getStdVerticalBoostSpeed()/4));
	        	}
	    		return true;
	    	}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				for(int i=0;i<2;i++)//called so often it looks like more
					pipe.getDrawer().fillOval(AEColor.CYAN, new AERect(UTIL.getRandomNr(x-4, x+w+4), UTIL.getRandomNr(y-4, y+h+4), 4, 4));
			}
		};
	}
	public static Wearable getWearable_ForceLighteningEffect() {
		return new Wearable() {
			@Override String getName() {
				return "FORCE_LIGHTENING";
			}
			@Override boolean allowNewWearable(Wearable newWear){
	    		if(newWear.getName().equals(getName())) {
					activatedAt=System.nanoTime()/1e9;
	        		return false;
	    		} else
	    			return true;
	    	}
	    	@Override boolean imDeadPleaseKillMe() {
	    		if(Math.rint(getSecondsSinceActivation()*10)%2==0)//relativly random
	    			getWearer().setLifePs(getWearer().getLifePs() - 0.1);
	    		return getSecondsSinceActivation()>2.5;
	    	}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				for(int i=0;i<2;i++)//called so often it looks like more
					pipe.getDrawer().fillOval(AEColor.CYAN, new AERect(UTIL.getRandomNr(x-4, x+w+4), UTIL.getRandomNr(y-4, y+h+4), 2, 2));
			}
		};
	}
	public static Wearable getWearable_BurningEffect() {
		return new Wearable() {
			@Override String getName() {
				return "BURNING";
			}
			double disposeAfter = 4;
			@Override boolean leftAction() {
				disposeAfter-=0.1111;
				return false;
			}
			@Override boolean rightAction() {
				disposeAfter-=0.1111;
				return false;
			}
			@Override boolean allowNewWearable(Wearable newWear){
	    		if(newWear.getName().equals(getName())) {
					activatedAt=System.nanoTime()/1e9;
	        		return false;
	    		} else
	    			return true;
	    	}
	    	@Override boolean imDeadPleaseKillMe() {
	    		if(Math.rint(getSecondsSinceActivation()*10)%2==0)//relativly random
	    			getWearer().setLifePs(getWearer().getLifePs() - 0.1);
	    		return getSecondsSinceActivation()>disposeAfter;
	    	}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				for(int i=0;i<4;i++)//called so often it looks like more
					pipe.getDrawer().fillOval(AEColor.ORANGE, new AERect(UTIL.getRandomNr(x-4, x+w+4), UTIL.getRandomNr(y-4, y+h+4), UTIL.getRandomNr(2, 5), UTIL.getRandomNr(2, 5)));
			}
		};
	}
}