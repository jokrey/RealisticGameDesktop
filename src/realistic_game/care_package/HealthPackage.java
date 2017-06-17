package realistic_game.care_package;

import util.UTIL;
import util.animation.pipeline.AnimationObject;
import util.animation.pipeline.AnimationObjectDrawer;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AEColor;
import util.animation.util.AERect;
import util.animation.util.AESize;

public class HealthPackage extends CarePackage {
	int health = 10;
	public int getHealth() {return health;}
	public HealthPackage(AESize frameSize) {
		super(UTIL.getRandomNr(0, frameSize.getWidth()-frameSize.getHeight()/22), 1, 0, 0, 0, 44, (int)frameSize.getHeight()/22, (int)frameSize.getHeight()/22, OVAL, new AEColor(255,255,0,0).brighter());
		if(UTIL.getRandomNr(0,5)==1)
			health=UTIL.getRandomNr(25,40);
		else
			health=UTIL.getRandomNr(5,18);
		drawParam = new AnimationObjectDrawer() {
			@Override public boolean canDraw(AnimationObject o, Object param) {return true;}
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
				AERect packRect = pipe.getDrawBoundsFor(o);
				pipe.getDrawer().drawOval(AEColor.RED.brighter(), packRect);
				int w = health;
				int h=w;
				int x = (int)(packRect.getX()+(packRect.getWidth()-w)/2);
				int y = (int)(packRect.getY()+(packRect.getHeight()-h)/2);
				pipe.getDrawer().fillOval(AEColor.RED.brighter(), new AERect(x, y, w/2, h/2));
				pipe.getDrawer().fillOval(AEColor.RED.brighter(), new AERect(x + w/2, y, w/2, h/2));
				pipe.getDrawer().fillOval(AEColor.RED.brighter(), new AERect(x + w/4, y + h/4, w/2, h/2));
				pipe.getDrawer().fillTriangle(AEColor.RED.brighter(), new AERect(x,y,w,h));//TODO
			}
		};
	}
}