package jokrey.game.realistic_game;

import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import util.UTIL;
import util.UTIL_2D;
import jokrey.utilities.animation.engine.LimitRangeMovingAnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObjectDrawer;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AEPoint;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;

import java.util.ArrayList;
import java.util.List;

public abstract class RangedWeapon extends Weapon {
	public RangedWeapon(int w, int h) {
		super(w, h);
		amunition=getInitAmmo();
	}

    public abstract  int getRecoil();
	public abstract double getShotSpeed();
	public abstract AESize getShotSize();
	public abstract double getShotGrav();
	public abstract AEColor getShotClr();
	public abstract int getInitAmmo();
	public abstract AEPoint getShotInitLocation(boolean player_looking_left);

	public AEPoint getShotInitLocation() {
	    return getShotInitLocation(weaponHolder.lookingLeft);
    }

	public boolean needsAmmo() {
		return getInitAmmo()>=0&&amunition<=0;
	}
	public int amunition = 0;
	Shot getShot() {
		AESize sS = getShotSize();
		AEPoint p = getShotInitLocation();
		p.x+=weaponHolder.isLookingLeft()?-sS.getWidth()/2:sS.getWidth()/2;
		p.y+=weaponHolder.isLookingLeft()?-sS.getHeight()/2:sS.getHeight()/2;
		return new Shot(new AEPoint(p.x,p.y), weaponHolder.getV_X() + (weaponHolder.isLookingLeft()?-getShotSpeed():getShotSpeed()), getShotGrav(), sS.getWidth(), sS.getHeight(), getShotClr(), getDamage(), getBlowbackMultipler(), weaponHolder);
	}
	boolean shoot(ArrayList<Shot> shots) {
		if(needsAmmo() || weaponHolder==null || isInCooldown())return false;
		used();
		shots.add(getShot());
		amunition--;
		return true;
	}


	public static RangedWeapon getWeapon_PlasmaGun(final int damage) {
		return new RangedWeapon(17, 5) {
			AEColor plasmaClr = AEColor.getRandomColor();
			@Override public int getInitAmmo() {return -1;}
			@Override public String getName() {return "PlasmaGun";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
        		pipe.getDrawer().fillOval(plasmaClr, drawWeapRect/*new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getW()/2, drawWeapRect.getH()/2)*/);
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-getW():weaponHolder.getW()));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()));
			}
			@Override public double getShotSpeed() {return (int) (5.0/damage * 500.0);}
			@Override public AESize getShotSize() {return new AESize(damage/2,damage/2);}
			@Override public AEColor getShotClr() {return plasmaClr;}
			@Override public int getRecoil() {return (int) (damage*3.3);}
			@Override public double getShotGrav() {return 0;}
			@Override public double getDelay() {return damage/45.0 * 2.0;}
			@Override public double getDamage() {return damage;}
			@Override public double getBlowbackMultipler() {return damage/45 * 2.5;}
		};
	}
	public static RangedWeapon getWeapon_SMG(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.006);
		return new RangedWeapon(h*3, h) {
			@Override public int getInitAmmo() {return 44;}
			@Override public String getName() {return "SMG";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				int bodyH = (int) (drawWeapRect.getHeight() - drawWeapRect.getHeight()/5);
				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getWidth(), bodyH));
//				g.setStroke(new BasicStroke(2));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.15, (int)drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+drawWeapRect.getHeight()));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.15), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, (int)drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, drawWeapRect.y+drawWeapRect.getHeight()));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-getW():weaponHolder.getW()));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()+getH()/2));
			}
			@Override public double getShotSpeed() {return 500;}
			@Override public AESize getShotSize() {return new AESize(4,4);}
			@Override public AEColor getShotClr() {return new AEColor(255,229, 189, 27);}
			@Override public int getRecoil() {return 16;}
			@Override public double getShotGrav() {return 0.8;}
			@Override public double getDelay() {return 0.09;}
			@Override public double getDamage() {return 4;}
			@Override public double getBlowbackMultipler() {return 0.06;}
		};
	}
	public static Weapon getWeapon_BLASTER(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.007);
		return new RangedWeapon(h*4, h) {
			@Override public int getInitAmmo() {return -1;}
			@Override public String getName() {return "BLASTER";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				int bodyH = (int) (drawWeapRect.getHeight() - drawWeapRect.getHeight()/5);
				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getWidth(), bodyH));
//				g.setStroke(new BasicStroke(2));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.15, drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+drawWeapRect.getHeight()));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.15), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, drawWeapRect.y+drawWeapRect.getHeight()));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.2):weaponHolder.getW()-getW()*0.2));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()+getH()/2));
			}
			@Override public double getShotSpeed() {return 555;}
			@Override public AESize getShotSize() {return new AESize(12,4);}
			@Override public AEColor getShotClr() {return new AEColor(255,255,0,0);}
			@Override public int getRecoil() {return 2;}
			@Override public double getShotGrav() {return 0;}
			@Override public double getDelay() {return 0.8;}
			@Override public double getDamage() {return 10;}
			@Override public double getBlowbackMultipler() {return 0.04;}
		};
	}
	public static Weapon getWeapon_FORCE_PUSH(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.01);
		return new RangedWeapon(h, h) {
			@Override public int getInitAmmo() {return -1;}
			@Override public String getName() {return "FORCE_PUSH";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
//				g.setStroke(new BasicStroke(2));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint(drawWeapRect.x, drawWeapRect.y));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.2, drawWeapRect.y+4));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.6), drawWeapRect.y+drawWeapRect.getHeight()/2));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.2), drawWeapRect.y+4));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()*0.6), drawWeapRect.y+drawWeapRect.getHeight()/2));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()+2):weaponHolder.getW()+2));
        		drawParam=weaponHolder.drawParam;
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0-2:getW()+2)), (int)(getY()+getH()/2));
			}
			@Override public double getShotSpeed() {return 555;}
			@Override public AESize getShotSize() {return new AESize(15,15*3);}
			@Override public AEColor getShotClr() {return new AEColor(255,128,128,255);}
			@Override public int getRecoil() {return 0;}
			@Override public double getShotGrav() {return 0;}
			@Override public double getDelay() {return 2.2;}
			@Override public double getDamage() {return 0;}
			@Override public double getBlowbackMultipler() {return 0.8;}
			@Override public Shot getShot() {
				AESize sS = getShotSize();
				AEPoint p = getShotInitLocation();
				p.x+=weaponHolder.isLookingLeft()?-sS.getWidth()/2:sS.getWidth()/2;
//				p.y+=weaponHolder.lookingLeft?-sS.getH()/2:sS.getH()/2;
				p.y-=5;
				return new Shot(new AEPoint(p.x,p.y), weaponHolder.getV_X() + (weaponHolder.isLookingLeft()?-getShotSpeed():getShotSpeed()), getShotGrav(), sS.getWidth(), sS.getHeight(), getShotClr(), getDamage(), getBlowbackMultipler(), weaponHolder) {
					{
        				drawParam=new AnimationObjectDrawer() {
							@Override public boolean canDraw(AnimationObject o, Object param) { return true; }
							@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
								AERect drawWeapRect = pipe.getDrawBoundsFor(o);
								if(getV_X()<0)
								    pipe.getDrawer().drawHalfOval(getShotClr(), drawWeapRect, 2);
								else
									pipe.getDrawer().drawHalfOval(getShotClr(), drawWeapRect, 3);
		        				pipe.getDrawer().fillOval(getShotClr(), new AERect(drawWeapRect.x, (int)(drawWeapRect.y + drawWeapRect.getHeight()/2 - (drawWeapRect.getHeight()*0.3)/2), (int) drawWeapRect.getWidth(), (int) (drawWeapRect.getHeight()*0.3)));
							}
        				};
					}
					@Override public boolean hitParticle(AERect lastShotBounds, AnimationObject p_g, List<LimitRangeMovingAnimationObject> particles) {return true;}
				};
			}
		};
	}
	public static RangedWeapon getWeapon_RIFLE(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.01);
		return new RangedWeapon(h*5, h) {
			@Override public int getInitAmmo() {return 22;}
			@Override public String getName() {return "RIFLE";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				int bodyH = (int) (drawWeapRect.getHeight() - drawWeapRect.getHeight()/3);
				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getWidth(), bodyH));
//				g.setStroke(new BasicStroke(3));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.15, drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+drawWeapRect.getHeight()));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.15), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, drawWeapRect.y+drawWeapRect.getHeight()));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.2):weaponHolder.getW()-getW()*0.2));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()+getH()/2));
			}
			@Override public double getShotSpeed() {return 567;}
			@Override public AESize getShotSize() {return new AESize(8,4);}
			@Override public AEColor getShotClr() {return new AEColor(255,229, 189, 27);}
			@Override public int getRecoil() {return 44;}
			@Override public double getShotGrav() {return 1;}
			@Override public double getDelay() {return 0.3;}
			@Override public double getDamage() {return 18;}
			@Override public double getBlowbackMultipler() {return 0.2;}
		};
	}
	public static RangedWeapon getWeapon_SNIPER(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.01);
		return new RangedWeapon(h*7, h) {
			@Override public int getInitAmmo() {return 4;}
			@Override public String getName() {return "SNIPER";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				int bodyH = (int) (drawWeapRect.getHeight() - drawWeapRect.getHeight()/2);
				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getWidth(), bodyH));
//				g.setStroke(new BasicStroke(3));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.15, drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.25), drawWeapRect.y+drawWeapRect.getHeight()));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.15), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.25, drawWeapRect.y+drawWeapRect.getHeight()));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.3):weaponHolder.getW()-getW()*0.3));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()+getH()/2));
			}
			@Override public double getShotSpeed() {return 999;}
			@Override public AESize getShotSize() {return new AESize(11,6);}
			@Override public AEColor getShotClr() {return new AEColor(255,229, 189, 27);}
			@Override public int getRecoil() {return 88;}
			@Override public double getShotGrav() {return 5;}
			@Override public double getDelay() {return 2.2;}
			@Override public double getDamage() {return 66;}
			@Override public double getBlowbackMultipler() {return 0.2;}
		};
	}
	public static RangedWeapon getWeapon_PISTOL(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.008);
		return new RangedWeapon((int) (h*1.4), h) {
			@Override public int getInitAmmo() {return 15;}
			@Override public String getName() {return "PISTOL";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
//				g.setStroke(new BasicStroke(3));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {//looking right
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()/2, drawWeapRect.y), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint (drawWeapRect.x+drawWeapRect.getWidth()/2, drawWeapRect.y));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()/2+1, drawWeapRect.y));
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()/2+1, drawWeapRect.y), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()):weaponHolder.getW()));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()));
			}
			@Override public double getShotSpeed() {return 555;}
			@Override public AESize getShotSize() {return new AESize(4,4);}
			@Override public AEColor getShotClr() {return new AEColor(255,229, 189, 27);}
			@Override public int getRecoil() {return 16;}
			@Override public double getShotGrav() {return 1;}
			@Override public double getDelay() {return 0.4;}
			@Override public double getDamage() {return 7;}
			@Override public double getBlowbackMultipler() {return 0.08;}
		};
	}
	public static RangedWeapon getWeapon_SHOTGUN(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.01);
		return new RangedWeapon(h*4, h) {
			@Override public int getInitAmmo() {return 8;}
			@Override public String getName() {return "SHOTGUN";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				int sgBodyH = (int) (drawWeapRect.getHeight()*0.8);
				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getWidth(), sgBodyH));
//				g.setStroke(new BasicStroke(3));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {//looking right
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()*0.1), drawWeapRect.y+sgBodyH), new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().fillRect(AEColor.GRAY, new AERect(drawWeapRect.x+drawWeapRect.getWidth()*0.5, drawWeapRect.y+(sgBodyH), drawWeapRect.getWidth()*0.25, drawWeapRect.getHeight()*0.2));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()*0.9), drawWeapRect.y+sgBodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
					pipe.getDrawer().fillRect(AEColor.GRAY, new AERect(drawWeapRect.x+drawWeapRect.getWidth()*0.3, drawWeapRect.y+(sgBodyH), drawWeapRect.getWidth()*0.25, drawWeapRect.getHeight()*0.2));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.3):weaponHolder.getW()-getW()*0.3));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()));
			}
			@Override public double getShotSpeed() {return 333;}
			@Override public AESize getShotSize() {return new AESize(5,4);}
			@Override public AEColor getShotClr() {return new AEColor(255,229, 189, 27);}
			@Override public int getRecoil() {return 99;}
			@Override public double getShotGrav() {return 1;}
			@Override public double getDelay() {return 2.2;}
			@Override public double getDamage() {return 8;}
			@Override public double getBlowbackMultipler() {return 0.3;}
			@Override public boolean shoot(ArrayList<Shot> shots) {
				if(needsAmmo() || weaponHolder==null || isInCooldown())return false;
				used();
				ArrayList<Shot> shotsToAdd = new ArrayList<>();
				int vy_deviation = -25;
				for(int i=0;i<8;i++) {
					Shot s=getShot();
					s.setV_Y(vy_deviation);
					shotsToAdd.add(s);
					vy_deviation+=(50*2)/8;
				}
				shots.addAll(shotsToAdd);
				amunition--;
				return true;
			}
		};
	}
	public static RangedWeapon getWeapon_JAFFA_STAFF(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.01);
		return new RangedWeapon(h*8, h) {
			@Override public int getInitAmmo() {return -1;}
			@Override public String getName() {return "STAFF";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				int bodyH = (int) (drawWeapRect.getHeight()/3);
				int bigEndH = (int)(drawWeapRect.getHeight()*0.7);
				pipe.getDrawer().fillRect(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y+(drawWeapRect.getHeight()/2-bodyH/2), drawWeapRect.getWidth(), bodyH));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {//looking right
					pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getHeight()*0.66, drawWeapRect.getHeight()));
					pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x + (drawWeapRect.getWidth()-drawWeapRect.getHeight()*3), drawWeapRect.y+(drawWeapRect.getHeight()/2-bigEndH/2), drawWeapRect.getHeight()*3, bigEndH));
				} else {
					pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x + (drawWeapRect.getWidth()-drawWeapRect.getHeight()*0.66), drawWeapRect.y, drawWeapRect.getHeight()*0.66, drawWeapRect.getHeight()));
					pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y+(drawWeapRect.getHeight()/2-bigEndH/2), drawWeapRect.getHeight()*3, bigEndH));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getMidAsPoint().x-getW()/2 + (weaponHolder.isLookingLeft()?-getW()*0.1:+getW()*0.1));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()+getH()/2));
			}
			@Override public double getShotSpeed() {return 555;}
			@Override public AESize getShotSize() {return new AESize((int)getH(),(int) (getH()/2));}
			@Override public AEColor getShotClr() {return new AEColor(255,229, 189, 27);}
			@Override public int getRecoil() {return 77;}
			@Override public double getShotGrav() {return 0;}
			@Override public double getDelay() {return 4.4;}
			@Override public double getDamage() {return 55;}
			@Override public double getBlowbackMultipler() {return 0.6;}
		};
	}
	public static RangedWeapon getWeapon_ZAT_NIK_TEL(AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.01);
		return new RangedWeapon((int) (h*0.8), h) {
			@Override public int getInitAmmo() {return -1;}
			@Override public String getName() {return "ZAT";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
//				if(drawWeapRect.getW()<=0||drawWeapRect.getH()<=0)
				pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y));
				pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {//looking right
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x, drawWeapRect.y), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y), new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.05):weaponHolder.getW()-getW()*0.05));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()));
			}
			@Override public double getShotSpeed() {return 333;}
			@Override public AESize getShotSize() {return new AESize(27,6);}
			@Override public AEColor getShotClr() {return new AEColor(255, 0, 255, 255);}
			@Override public int getRecoil() {return 0;}
			@Override public double getShotGrav() {return 0;}
			@Override public double getDelay() {return 3.3;}
			@Override public double getDamage() {return 5;}
			@Override public double getBlowbackMultipler() {return 0;}
			@Override public Shot getShot() {
        		AESize sS = getShotSize();
        		AEPoint p = getShotInitLocation();
        		p.x+=weaponHolder.isLookingLeft()?-sS.getWidth()/2:sS.getWidth()/2;
        		p.y+=weaponHolder.isLookingLeft()?-sS.getHeight()/2:sS.getHeight()/2;
				return new Shot(new AEPoint(p.x,p.y), weaponHolder.getV_X() + (weaponHolder.isLookingLeft()?-getShotSpeed():getShotSpeed()), getShotGrav(), sS.getWidth(), sS.getHeight(), getShotClr(), getDamage(), getBlowbackMultipler(), weaponHolder) {
					{
						drawParam=new AnimationObjectDrawer() {
							@Override public boolean canDraw(AnimationObject o, Object param) { return true; }
							@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
								AERect drawShotRect = pipe.getDrawBoundsFor(o);
								pipe.getDrawer().drawLine(getShotClr(), new AEPoint(drawShotRect.x, drawShotRect.y+drawShotRect.getHeight()/2), new AEPoint(drawShotRect.x+drawShotRect.getWidth(), drawShotRect.y+drawShotRect.getHeight()/2));
								pipe.getDrawer().drawRect(getShotClr(), new AERect(drawShotRect.x+UTIL.getRandomNr(0, drawShotRect.getWidth()), (int)(drawShotRect.y+UTIL.getRandomNr(0, drawShotRect.getHeight())), 1, 1));
								pipe.getDrawer().drawHalfOval(getShotClr(), new AERect(drawShotRect.x, drawShotRect.y, drawShotRect.getWidth()/2, drawShotRect.getHeight()/2), 0);

								pipe.getDrawer().drawHalfOval(getShotClr(), new AERect(drawShotRect.x+drawShotRect.getWidth()/2, drawShotRect.y+drawShotRect.getHeight()/2, drawShotRect.getWidth()/2, drawShotRect.getHeight()/2), 1);
//								drawer.draw(getShotClr(), new Arc2D.Double((drawShotRect.x+drawShotRect.getW()/2), drawShotRect.y+drawShotRect.getH()/2, drawShotRect.getW()/2, drawShotRect.getH()/2, 180, 180+90, Arc2D.OPEN));
							}
						};
					}
					@Override public boolean hitParticle(AERect lastShotBounds, AnimationObject p_g, List<LimitRangeMovingAnimationObject> particles) {
						if(p_g instanceof Player) {
							((Player)p_g).addWearable(Wearable.getWearable_ZattedEffect());
						}
						return true;
					}
				};
			}
		};
	}
	public static Weapon getWeapon_Grenade(final AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.015);
		return new RangedWeapon((h), h) {
			@Override public int getInitAmmo() {return 2;}
			@Override public String getName() {return "GRENADE";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()*0.4, drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.4, drawWeapRect.getHeight()-drawWeapRect.getHeight()*0.4));
				pipe.getDrawer().fillOval(AEColor.RED, new AERect(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.4), drawWeapRect.y+(drawWeapRect.getHeight()*0.4), drawWeapRect.getWidth()*0.4, drawWeapRect.getHeight()*0.4));
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.05):weaponHolder.getW()-getW()*0.05));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()));
			}
			@Override public double getShotSpeed() {return frameSize.getHeight()*0.3;}
			@Override public AESize getShotSize() {return new AESize((int)getW(),(int)getH());}
			@Override public AEColor getShotClr() {return new AEColor(255, 128, 128, 128);}
			@Override public int getRecoil() {return 0;}
			@Override public double getShotGrav() {return frameSize.getHeight()*0.3;}
			@Override public double getDelay() {return 4;}
			@Override public double getDamage() {return 1.6;}
			@Override public double getBlowbackMultipler() {return 0;}
			@Override public boolean shoot(final ArrayList<Shot> shots) {
				if(needsAmmo() || weaponHolder==null || isInCooldown())return false;
				used();
        		AESize sS = getShotSize();
        		AEPoint p = getShotInitLocation();
        		p.x+=weaponHolder.isLookingLeft()?-sS.getWidth()/2:sS.getWidth()/2;
        		p.y+=weaponHolder.isLookingLeft()?-sS.getHeight()/2:sS.getHeight()/2;
        		Shot s = new Shot(new AEPoint(p.x,p.y), weaponHolder.getV_X() + (weaponHolder.isLookingLeft()?-getShotSpeed():getShotSpeed()), getShotGrav(), sS.getWidth(), sS.getHeight(), getShotClr(), getDamage(), getBlowbackMultipler(), weaponHolder) {
        			{
        				drawParam=new AnimationObjectDrawer() {
        					@Override public boolean canDraw(AnimationObject o, Object param) {return true;}
							@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
								AERect drawShotRect = pipe.getDrawBoundsFor(o);
		        				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawShotRect.x, drawShotRect.y+drawShotRect.getHeight()*0.4, drawShotRect.getWidth()-drawShotRect.getWidth()*0.4, drawShotRect.getHeight()-drawShotRect.getHeight()*0.4));
								pipe.getDrawer().fillOval(AEColor.RED, new AERect(drawShotRect.x+(drawShotRect.getWidth()-drawShotRect.getWidth()*0.4), (int)(drawShotRect.y+(drawShotRect.getHeight()*0.4)), (int)(drawShotRect.getWidth()*0.4), (int) (drawShotRect.getHeight()*0.4)));
		        			}
        				};
        			}
        			boolean allreadyHit=false;
        			@Override public boolean hitParticle(AERect lastShotBounds, AnimationObject p_g, List<LimitRangeMovingAnimationObject> particles) {
        				if(allreadyHit)return true;
        				for(int counter=0;counter<66;counter++) {
	        		        double[] v_s = UTIL_2D.angleVelocityToXYVelocity(UTIL.getRandomNr(0, 360)-180, UTIL.getRandomNr(frameSize.getHeight()*0.05, frameSize.getHeight()*0.3));
	        		        Shot shot = new Shot(getMid(), 0, frameSize.getHeight()*0.2, 2, 2, new AEColor(255,255,128,0), 2, 0.5, shooter) {
	        		        	@Override public boolean hitParticle(AERect lastShotBounds_g, AnimationObject p_g_g_g, java.util.List<LimitRangeMovingAnimationObject> particles_g) {return true;}
	        		        };
	        				shot.setV_X(v_s[0]);
	        				shot.setV_Y(v_s[1]);
	                		shot.computeBoxBounce(new AERect(frameSize), 1, 1);
	        				shots.add(shot);
        				}
        				allreadyHit=true;
        				return false;
        			}
        		};
        		s.setV_Y(-frameSize.getHeight()*0.3);
				shots.add(s);
				amunition--;
				return true;
			}
			@Override public Shot getShot() {return null;}
		};
	}
	public static Weapon getWeapon_Mine(final AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.02);
		return new RangedWeapon(h, (int) (h*0.5)) {
			@Override public int getInitAmmo() {return 2;}
			@Override public String getName() {return "MINE";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				pipe.getDrawer().fillHalfOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getWidth(), drawWeapRect.getHeight()*2), 1);
				pipe.getDrawer().fillOval(AEColor.RED, new AERect(drawWeapRect.x+(drawWeapRect.getWidth()/2-(drawWeapRect.getWidth()*0.25)/2), drawWeapRect.y, drawWeapRect.getWidth()*0.25, drawWeapRect.getHeight()*0.5));
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.05):weaponHolder.getW()-getW()*0.05));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()));
			}
			@Override public double getShotSpeed() {return frameSize.getHeight()*0.1;}
			@Override public AESize getShotSize() {return new AESize((int)getW(),(int)getH());}
			@Override public AEColor getShotClr() {return new AEColor(255, 128, 128, 128);}
			@Override public int getRecoil() {return 0;}
			@Override public double getShotGrav() {return frameSize.getHeight()*0.3;}
			@Override public double getDelay() {return 4;}
			@Override public double getDamage() {return 1.333;}
			@Override public double getBlowbackMultipler() {return 0;}
			@Override public boolean shoot(final ArrayList<Shot> shots) {
				if(needsAmmo() || weaponHolder==null || isInCooldown())return false;
				used();
        		AESize sS = getShotSize();
        		AEPoint p = getShotInitLocation();
        		p.x+=weaponHolder.isLookingLeft()?-sS.getWidth()/2:sS.getWidth()/2;
        		p.y+=weaponHolder.isLookingLeft()?-sS.getHeight()/2:sS.getHeight()/2;
                Realistic_Game_Engine engine = weaponHolder.engine;
        		Shot s = new Shot(new AEPoint(p.x,p.y), weaponHolder.getV_X() + (weaponHolder.isLookingLeft()?-getShotSpeed():getShotSpeed()), getShotGrav(), sS.getWidth(), sS.getHeight(), getShotClr(), getDamage(), getBlowbackMultipler(), weaponHolder) {
        			{
        				drawParam=new AnimationObjectDrawer() {
        					@Override public boolean canDraw(AnimationObject o, Object param) {return true;}
							@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
		        				AERect drawShotRect = pipe.getDrawBoundsFor(o);
								pipe.getDrawer().fillHalfOval(AEColor.GRAY, new AERect(drawShotRect.x, drawShotRect.y, drawShotRect.getWidth(), drawShotRect.getHeight()*2), 1);
		        				pipe.getDrawer().fillOval(AEColor.RED, new AERect((int)(drawShotRect.x+(drawShotRect.getWidth()/2-(drawShotRect.getWidth()*0.25)/2)), (int) (drawShotRect.y), (int)(drawShotRect.getWidth()*0.25), (int) (drawShotRect.getHeight()*0.5)));
							}
						};
        			}
        			boolean allreadyHit=false;
        			double additioFX = 0;
        			@Override public boolean hitParticle(AERect lastShotBounds, AnimationObject p_g, List<LimitRangeMovingAnimationObject> particles) {
        				if(allreadyHit)return true;
        				if(p_g instanceof Player) {
	        				for(int counter=0;counter<99;counter++) {
		        		        double[] v_s = UTIL_2D.angleVelocityToXYVelocity(UTIL.getRandomNr(180, 360), UTIL.getRandomNr(frameSize.getHeight()*0.02, frameSize.getHeight()*0.2));
		        		        Shot shot = new Shot(getMid(), 0, frameSize.getHeight()*0.2, 2, 2, new AEColor(255,255,128,0), 3, 0.5, shooter) {
		        		        	@Override public boolean hitParticle(AERect lastShotBounds_g, AnimationObject p_g_g_g, java.util.List<LimitRangeMovingAnimationObject> particles_g) {return true;}
		        		        };
		        				shot.setV_X(v_s[0]);
		        				shot.setV_Y(v_s[1]);
		                		shot.computeBoxBounce(new AERect(frameSize), 1, 1);
		        				shots.add(shot);
	        				}
	        				allreadyHit=true;
        				}
        				return false;
        			}
        			@Override public void move(int ticksPerSecond) {
    					computeBoxStop(new AERect(engine.getVirtualBoundaries()));
    	                if(overlapingBoundsBottom(engine.getVirtualLimit_height()-1)) {
    	    				if(getV_X()<0) 	setF_X(99);
    	    				if(getV_X()>0) 	setF_X(-99);
    	                } else								setF_X(0);
    	                computeStops(engine.mapParticles);
    	                AnimationObject o;
    	                if((o = AnimationObject.intersectsWhich(new AnimationObject(new AERect(getX(), getY(), getW(), getH()+1), AnimationObject.RECT), engine.mapParticles))!=null && ((MapParticle)o).isSolid) {//important
    	                	setV_Y(0);
    	                	if(Math.abs(getV_X())<Math.abs(((MapParticle)o).getV_X())) {
    	                		double lblVX = ((MapParticle)o).getV_X();
    	                		if(lblVX!=0) {
    		                		additioFX=lblVX<0?-(Math.abs(lblVX)+99):Math.abs(lblVX)+99;
    	                		}
    	                	} else
    	                		additioFX=0;
    	    				if(getV_X()<0)	setF_X(99);
    	    				if(getV_X()>0)	setF_X(-99);
    	            	} else {
    	            		additioFX=0;
    	            	}
        				setF_X(getF_X()+additioFX);
        				super.move(ticksPerSecond);
        				setF_X(getF_X()-additioFX);
        			}
        		};
        		s.setV_Y(-frameSize.getHeight()*0.1);
				shots.add(s);
				amunition--;
				return true;
			}
			@Override public Shot getShot() {return null;}
		};
	}
	public static Weapon getWeapon_Flamethrower(final AESize frameSize) {
		int h = (int) (frameSize.getHeight()*0.013);
		return new RangedWeapon(h*6, h) {
			@Override public int getInitAmmo() {return 90;}
			@Override public String getName() {return "FLAMETHROWER";}
			@Override public void drawWeap(AnimationPipeline pipe) {
				AERect drawWeapRect = pipe.getDrawBoundsFor(this);
				int bodyH = (int) (drawWeapRect.getHeight() - drawWeapRect.getHeight()/3);
				pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x, drawWeapRect.y, drawWeapRect.getWidth(), bodyH));
//				g.setStroke(new BasicStroke(3));
				if(weaponHolder==null||!weaponHolder.isLookingLeft()) {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getWidth()*0.15, drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x, drawWeapRect.y+drawWeapRect.getHeight()));
//					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getW()-drawWeapRect.getW()*0.25)), (int)(drawWeapRect.y+bodyH), (int)(drawWeapRect.x+(drawWeapRect.getW()-drawWeapRect.getW()*0.25)), (int)(drawWeapRect.y+drawWeapRect.getH()));
					pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x+drawWeapRect.getWidth()*0.2, drawWeapRect.y, drawWeapRect.getWidth()*0.4, drawWeapRect.getHeight()));
				} else {
					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+(drawWeapRect.getWidth()-drawWeapRect.getWidth()*0.15), drawWeapRect.y+bodyH), new AEPoint(drawWeapRect.x+drawWeapRect.getWidth(), drawWeapRect.y+drawWeapRect.getHeight()));
//					pipe.getDrawer().drawLine(AEColor.GRAY, new AEPoint(drawWeapRect.x+drawWeapRect.getW()*0.25), (int)(drawWeapRect.y+bodyH), (int)(drawWeapRect.x+drawWeapRect.getW()*0.25), (int)(drawWeapRect.y+drawWeapRect.getH()));
					pipe.getDrawer().fillOval(AEColor.GRAY, new AERect(drawWeapRect.x+drawWeapRect.getWidth()*0.4, drawWeapRect.y, drawWeapRect.getWidth()*0.4, drawWeapRect.getHeight()));
				}
			}
			@Override public void updatePosition() {
        		setY(weaponHolder.getY()+weaponHolder.getH()/2);
        		setX(weaponHolder.getX()+(weaponHolder.isLookingLeft()?-(getW()-getW()*0.2):weaponHolder.getW()-getW()*0.2));
			}
			@Override public AEPoint getShotInitLocation(boolean player_looking_left) {
				if(weaponHolder==null)return new AEPoint();
				return new AEPoint((int)(getX()+(player_looking_left?0:getW())), (int)(getY()));
			}
			@Override public double getShotSpeed() {return frameSize.getHeight()*0.6;}
			@Override public AESize getShotSize() {return new AESize(6, 4);}
			@Override public AEColor getShotClr() {return new AEColor(255,255,128,0);}
			@Override public int getRecoil() {return 0;}
			@Override public double getShotGrav() {return frameSize.getHeight()*0.3;}
			@Override public double getDelay() {return 0.1;}
			@Override public double getDamage() {return 0;}
			@Override public double getBlowbackMultipler() {return 0;}
			@Override public boolean shoot(final ArrayList<Shot> shots) {
				if(needsAmmo() || weaponHolder==null || isInCooldown())return false;
				used();
				ArrayList<Shot> shotsToAdd = new ArrayList<>();
				for(int i=0;i<4;i++) {
					Shot s=getShot();
					s.setV_Y(UTIL.getRandomNr(0, 70)-65);
					shotsToAdd.add(s);
				}
				shots.addAll(shotsToAdd);
				amunition--;
				return true;
			}
			@Override public Shot getShot() {
				AESize sS = getShotSize();
				AEPoint p = getShotInitLocation();
				p.x+=weaponHolder.isLookingLeft()?-sS.getWidth()/2:sS.getWidth()/2;
				p.y+=weaponHolder.isLookingLeft()?-sS.getHeight()/2:sS.getHeight()/2;
				return new Shot(new AEPoint(p.x,p.y), weaponHolder.getV_X() + (weaponHolder.isLookingLeft()?-getShotSpeed():getShotSpeed()), getShotGrav(), sS.getWidth(), sS.getHeight(), getShotClr(), getDamage(), getBlowbackMultipler(), weaponHolder) {
					@Override public boolean hitParticle(AERect lastShotBounds, AnimationObject p_g, List<LimitRangeMovingAnimationObject> particles) {
						if(p_g instanceof Player)
							((Player)p_g).addWearable(Wearable.getWearable_BurningEffect());
						return true;
					}
				};
			}
		};
	}
}