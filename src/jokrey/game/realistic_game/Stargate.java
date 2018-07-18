package jokrey.game.realistic_game;

import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.pipeline.StandardAODrawer;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;

public class Stargate extends AnimationObject {
	public static int getStargateStdWidth(int sgH) {
		return sgH/4;
	}
	public static int getStargateStdHeight(int frmH) {
		return frmH/10;
	}

//    ArrayList<ExplosionParticle> sg_particles = new ArrayList<>();
	public Stargate(double x, double y, int sgH, final AEColor clr) {
		super(x, y, getStargateStdWidth(sgH), sgH, OVAL, new StandardAODrawer() {
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object param) {
				super.draw(o, pipe, clr);
				
				AEColor gray = AEColor.GRAY;
                AERect r = pipe.getDrawBoundsFor(o);
				pipe.getDrawer().drawOval(gray, new AERect(r.x, r.y, r.getWidth(), r.getHeight()));
				pipe.getDrawer().drawOval(gray, new AERect(r.x+1, r.y+1, r.getWidth()-2, r.getHeight()-2));
				pipe.getDrawer().drawOval(gray, new AERect(r.x+2, r.y+2, r.getWidth()-4, r.getHeight()-4));
				pipe.getDrawer().drawOval(gray, new AERect(r.x+3, r.y+3, r.getWidth()-6, r.getHeight()-6));
				pipe.getDrawer().drawOval(gray, new AERect(r.x+4, r.y+4, r.getWidth()-8, r.getHeight()-8));
			}
		});
	}
//	public void fluktateAt(Point p, int howMuch) {
//		if(sg_particles.size()>o.getH()*2)return;
//		ExplosionParticle.executeHitAnimation(sg_particles, howMuch, p, new Dimension(6,6), new Particle(o.getX()+6, o.getY()+6, o.getW()-6*2, o.getH()-6*2, OVAL), 55,
//				c, c.brighter(), c.brighter(), c.brighter(), c.brighter().brighter());
//	}
}