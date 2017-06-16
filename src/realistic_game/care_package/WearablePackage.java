package realistic_game.care_package;

import realistic_game.Player;
import realistic_game.Wearable;
import util.UTIL;
import util.animation.pipeline.AnimationPipeline;
import util.animation.pipeline.AnimationObject;
import util.animation.pipeline.AnimationObjectDrawer;
import util.animation.util.AEColor;
import util.animation.util.AERect;
import util.animation.util.AESize;

public class WearablePackage extends CarePackage {
	public final Wearable w;
	AESize frameSize;
	public WearablePackage(Wearable w_g, AESize frameSize_g) {
		super(UTIL.getRandomNr(0, frameSize_g.getWidth()-frameSize_g.getHeight()/22), 1, 0, 0, 0, 44, (int)frameSize_g.getHeight()/22, (int)frameSize_g.getHeight()/22, OVAL, new AEColor(255,255,0,0).brighter());
		w=w_g;
		this.frameSize=frameSize_g;
		drawParam = new AnimationObjectDrawer() {
			@Override public boolean canDraw(AnimationObject o, Object param) {return true;}
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
				AERect packRect = pipe.getDrawBoundsFor(o);
				pipe.getDrawer().drawOval(AEColor.RED.brighter(), new AERect(packRect.x, packRect.y, packRect.getWidth(), packRect.getHeight()));
				w.drawWearable(pipe, (packRect.x+packRect.getWidth()/2)-Player.getStdSize(frameSize).getWidth()/2, (packRect.y+packRect.getHeight()/2)-Player.getStdSize(frameSize).getHeight()/2, Player.getStdSize(frameSize).getWidth(), Player.getStdSize(frameSize).getHeight());
			}
		};
	}
}