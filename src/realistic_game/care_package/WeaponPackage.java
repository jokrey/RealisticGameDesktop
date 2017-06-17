package realistic_game.care_package;

import realistic_game.Weapon;
import util.UTIL;
import util.animation.pipeline.AnimationObject;
import util.animation.pipeline.AnimationObjectDrawer;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AEColor;
import util.animation.util.AERect;
import util.animation.util.AESize;

public class WeaponPackage extends CarePackage {
	public final Weapon w;
	public WeaponPackage(Weapon w_g, AESize frameSize) {
		super(UTIL.getRandomNr(0, frameSize.getWidth()-frameSize.getHeight()/22), 1, 0, 0, 0, 44, (int)frameSize.getHeight()/22, (int)frameSize.getHeight()/22, OVAL, new AEColor(255,255,0,0).brighter());
		w=w_g;
		drawParam = new AnimationObjectDrawer() {
			@Override public boolean canDraw(AnimationObject o, Object param) { return false; }
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
				AERect packRect = pipe.getDrawBoundsFor(o);
				pipe.getDrawer().drawOval(AEColor.RED.brighter(), packRect);
				w.setY(getY()+getH()/2);
				w.setX(getX()+getW()/4);
				w.drawWeap(pipe);
//				drawer.getDrawingUnitForParam(w, w.drawParam);
			}
		};
	}
}