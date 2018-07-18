package jokrey.game.realistic_game.care_package;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Player;
import jokrey.utilities.animation.engine.MovingAnimationObject;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;

import java.util.ArrayList;

public abstract class CarePackage extends MapParticle {
	public CarePackage(double x, double y, double vX, double vY, double fX,
			double fY, int width, int height, int shape_type, AEColor c) {
		super(x, y, vX, vY, fX, fY, width, height, shape_type, c);
	}

	public abstract boolean onIntersectionWithPlayer(Player player);

    @Override public boolean computeInsideBoxStop(AERect box) {
        if(!isSolid)return false;
        return super.computeInsideBoxStop(box);
    }

    public void computeStops(ArrayList<? extends MovingAnimationObject> mapParticles) {
	    if(!isSolid)return;
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

                if (l_r_t_b == 0 || l_r_t_b == 1) {
                    setV_X(0);
//        				double oldply_vx=player_1.getV_X();
//        				player_1.setV_X(mr.getV_X());
//    			    	mr.setV_X(oldply_vx);
//        				if(Math.abs(mr.getV_X())<=22)
//            				mr.setV_X(mr.getV_X()<0?-22:22);
                } else if (l_r_t_b == 2 || l_r_t_b == 3) {
                    setV_Y(0);
                    if (getV_X() < 0)
                        setF_X(66);
                    if (getV_X() > 0)
                        setF_X(-66);
                } else
                    setF_X(0);

                setX(getX() + shiftX);
                setY(getY() + shiftY);
    		}
        }
	}
}