package realistic_game;

import util.animation.engine.MovingAnimationObject;
import util.animation.util.AEColor;

public class MapParticle extends MovingAnimationObject {
	public MapParticle(){super(0,0,0,0,0,0,0,0,0,new AEColor(255,255,255,255));}
	public boolean isSolid=true;
	public int damagePerSecond=0;
	public MapParticle(double x,double y,double vX,double vY,double fX,double fY,int width,int height,int shape_type,AEColor c) {
		super(x,y,vX,vY,fX,fY,width,height,shape_type,c);
	}
	public MapParticle(double x,double y,double vX,double vY,double fX,double fY,int width,int height,int shape_type,AEColor c, boolean isSolid_g) {
		super(x,y,vX,vY,fX,fY,width,height,shape_type,c);
		isSolid=isSolid_g;
	}
	public MapParticle(double x,double y,double vX,double vY,double fX,double fY,int width,int height,int shape_type,AEColor c, boolean isSolid_g, int damagePerSecond_g) {
		super(x,y,vX,vY,fX,fY,width,height,shape_type,c);
		isSolid=isSolid_g;
		damagePerSecond=damagePerSecond_g;
	}
}