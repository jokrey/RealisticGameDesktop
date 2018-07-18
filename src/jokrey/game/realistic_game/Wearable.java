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
	protected Player getWearer(){return wearer;}
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
	public abstract String getName();
	public abstract boolean newWearableAddedRemoveThis(Wearable newWear);
	public void drawWearable(AnimationPipeline pipe) {
		AERect toDrawAt = pipe.getDrawBoundsFor(getWearer());
		drawWearable(pipe, toDrawAt.x,toDrawAt.y,toDrawAt.getWidth(),toDrawAt.getHeight());
	}
	public abstract void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h);

	//TO OVERRIDE
	public boolean imDeadPleaseKillMe(){return false;}
	public boolean attackAction(){return false;}
	public boolean upAction(){return false;}
	public boolean leftAction(){return false;}
	public boolean rightAction(){return false;}
	public boolean gotHitAction(Shot s, ArrayList<LimitRangeMovingAnimationObject> particles){return false;}



	public static Wearable getWearable_ProtectiveVest() {
		return new Wearable() {
			@Override
			public String getName() {
				return "VEST";
			}
			@Override public boolean newWearableAddedRemoveThis(Wearable newWear){
				return newWear.getName().equals(getName());//if the new thing is also a vest then this one can be discarded
			}

	    	private double bulletProtectionPercent = 100;
	    	@Override public boolean gotHitAction(Shot shot, ArrayList<LimitRangeMovingAnimationObject> particles) {
	    		getWearer().setLifePs(getWearer().getLifePs() - (shot.damage - shot.damage * (bulletProtectionPercent/100)));
	    		bulletProtectionPercent-=shot.damage;
				shot.executeHitAnimation(particles);
				getWearer().setV_X(getWearer().getV_X() + (shot.getV_X()*shot.blowbackMultipler/10));
				return true;
	    	}
	    	@Override public boolean imDeadPleaseKillMe() {
	    		return bulletProtectionPercent<=0;
	    	}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				pipe.getDrawer().fillHalfOval(AEColor.DARK_GRAY, new AERect(x, y-(h*0.15), w, h), 0);
			}
		};
	}
	public static Wearable getWearable_DoubleJump() {
		return new Wearable() {
			@Override
			public String getName() {
				return "DOUBLE_JUMP";
			}
			@Override public boolean newWearableAddedRemoveThis(Wearable newWear){
			    return newWear.getName().equals("DOUBLE_JUMP") || newWear.getName().equals("SPEED_BOOST") || newWear.getName().equals("JET_PACK");
            }

	    	boolean allreadyDoubleJumped=false;
	    	@Override public boolean upAction() {
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
	    	@Override public boolean imDeadPleaseKillMe() {return false;}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
//				g.setColor(getWearer()==null?Color.gray:((Color) getWearer().drawParam).darker());
				pipe.getDrawer().fillHalfOval(AEColor.RED, new AERect(x, y+(h-h*0.32), w, h*0.3), 0);
			}
		};
	}
	public static Wearable getWearable_SpeedBoost() {
		return new Wearable() {
			@Override public String getName() {
				return "SPEED_BOOST";
			}
			@Override public boolean newWearableAddedRemoveThis(Wearable newWear){
                return newWear.getName().equals("DOUBLE_JUMP") || newWear.getName().equals("SPEED_BOOST") || newWear.getName().equals("JET_PACK");
	    	}

	    	@Override public boolean upAction() {
	    		if(getWearer().getV_Y()==0) {
	    			getWearer().setV_Y(-getWearer().getStdVerticalBoostSpeed()*1.6);
		    		return true;
	    		} else {
	    			return false;
	    		}
	    	}
	    	@Override public boolean leftAction() {
	    		getWearer().lookingLeft = true;
	    		getWearer().setV_X(getWearer().getV_X()-(getWearer().getStdHorizontalSpeedChange()*1.6));
	    		if(getWearer().getV_X()<=-getWearer().getStdHorizontalSpeedMax()*1.6)
	    			getWearer().setV_X(-getWearer().getStdHorizontalSpeedMax()*1.6);
	    		return true;
	    	}
	    	@Override public boolean rightAction() {
	    		getWearer().lookingLeft = false;
	    		getWearer().setV_X(getWearer().getV_X()+(getWearer().getStdHorizontalSpeedChange()*1.6));
	    		if(getWearer().getV_X()>=getWearer().getStdHorizontalSpeedMax()*1.6)
	    			getWearer().setV_X(getWearer().getStdHorizontalSpeedMax()*1.6);
	    		return true;
	    	}
	    	@Override public boolean imDeadPleaseKillMe() {return false;}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
//				g.setColor(getWearer()==null?Color.gray:((Color) getWearer().drawParam).brighter());
				pipe.getDrawer().fillHalfOval(AEColor.CYAN, new AERect(x, y+(h-h*0.32), w, h*0.3), 0);
			}
		};
	}
	public static Wearable getWearable_JetPack() {
		return new Wearable() {
			@Override
			public String getName() {
				return "JET_PACK";
			}
			@Override public boolean newWearableAddedRemoveThis(Wearable newWear){
                return newWear.getName().equals("DOUBLE_JUMP") || newWear.getName().equals("SPEED_BOOST") || newWear.getName().equals("JET_PACK");
	    	}

	    	int fuel=100;
	    	double lastBoost = System.nanoTime()/1e9;
	    	@Override public boolean upAction() {
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
	    	@Override public boolean imDeadPleaseKillMe() {return fuel<=0;}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				pipe.getDrawer().fillHalfOval(AEColor.DARK_GRAY, new AERect(x+(getWearer()==null? 0 : getWearer().isLookingLeft()?w:-w), y+(h*0.15), w, h), 1);
			}
		};
	}


	public static Wearable getWearable_ZattedEffect() {
		return new Wearable() {
			@Override
			public String getName() {
				return "ZATTED";
			}
			@Override public boolean newWearableAddedRemoveThis(Wearable newWear){
	    		if(newWear.getName().equals(getName())) {
	    			if(getWearer().getLifePs()>33)getWearer().setLifePs(33);
	    			else getWearer().setLifePs(-1);
	        		return true;
	    		} else
	    			return false;
	    	}
	    	@Override public boolean imDeadPleaseKillMe() {
	    		return getSecondsSinceActivation()>8;
	    	}
	    	@Override public boolean leftAction() {
	    		getWearer().setV_X(getWearer().getV_X()-(getWearer().getStdHorizontalSpeedChange()/3));
	    		if(getWearer().getV_X()<=-getWearer().getStdHorizontalSpeedMax()/2)
	    			getWearer().setV_X(-getWearer().getStdHorizontalSpeedMax()/2);
	    		return true;
	    	}
	    	@Override public boolean rightAction() {
	    		getWearer().setV_X(getWearer().getV_X()+(getWearer().getStdHorizontalSpeedChange()/3));
	    		if(getWearer().getV_X()>=getWearer().getStdHorizontalSpeedMax()/2)
	    			getWearer().setV_X(getWearer().getStdHorizontalSpeedMax()/2);
	    		return true;
	    	}
	    	@Override public boolean upAction() {
	        	if(getWearer().getV_Y()==0) {
	        		getWearer().setV_Y(-(getWearer().getStdVerticalBoostSpeed()/4));
	        	}
	    		return true;
	    	}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				for(int i=0;i<2;i++)//called so often it looks like much more
					pipe.getDrawer().fillOval(AEColor.CYAN, new AERect(UTIL.getRandomNr(x-4, x+w+4), UTIL.getRandomNr(y-4, y+h+4), 4, 4));
			}
		};
	}
	public static Wearable getWearable_ForceLighteningEffect() {
		return new Wearable() {
			@Override
			public String getName() {
				return "FORCE_LIGHTENING";
			}
			@Override public boolean newWearableAddedRemoveThis(Wearable newWear){
	        	return newWear.getName().equals(getName()); //new force lightning effect, so one can be discarded
	    	}
	    	@Override public boolean imDeadPleaseKillMe() {
	    		if(Math.rint(getSecondsSinceActivation()*10)%2==0)//relativly random
	    			getWearer().setLifePs(getWearer().getLifePs() - 0.1);
	    		return getSecondsSinceActivation()>2.5;
	    	}
			@Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
				for(int i=0;i<2;i++)//called so often it looks like much more
					pipe.getDrawer().fillOval(new AEColor(0,0,255), new AERect(UTIL.getRandomNr(x-4, x+w+4), UTIL.getRandomNr(y-4, y+h+4), 2, 2));
			}
		};
	}
	public static Wearable getWearable_BurningEffect() {
		return new Wearable() {
			@Override
			public String getName() {
				return "BURNING";
			}
			double disposeAfter = 4;
			@Override public boolean leftAction() {
				disposeAfter-=0.1111;
				return false;
			}
			@Override public boolean rightAction() {
				disposeAfter-=0.1111;
				return false;
			}
			@Override public boolean newWearableAddedRemoveThis(Wearable newWear){
			    return newWear.getName().equals(getName());
	    	}
	    	@Override public boolean imDeadPleaseKillMe() {
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