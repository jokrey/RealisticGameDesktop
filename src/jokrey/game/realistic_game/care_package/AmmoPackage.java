package jokrey.game.realistic_game.care_package;

import util.UTIL;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObjectDrawer;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;

public class AmmoPackage extends CarePackage {
	public String ammoFor = "";
	public int ammo = 10;
	public AmmoPackage(int ammo_g, String ammoFor_g, AESize frameSize) {
		super(UTIL.getRandomNr(0, frameSize.getWidth()-frameSize.getHeight()/22), 1, 0, 0, 0, 44, (int)frameSize.getHeight()/22, (int)frameSize.getHeight()/22, OVAL, new AEColor(255,255,0,0).brighter());
		ammo=ammo_g;
		ammoFor=ammoFor_g;
		drawParam = new AnimationObjectDrawer() {
			@Override public boolean canDraw(AnimationObject o, Object param) {return true;}
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object param) {
				AERect packRect = pipe.getDrawBoundsFor(o);
				pipe.getDrawer().drawOval(AEColor.LIGHT_GRAY, packRect);
				double stringH = pipe.getDrawer().drawString(AEColor.LIGHT_GRAY, 12, ammoFor, packRect.x+packRect.getWidth()/2, packRect.y+packRect.getHeight()/2);
				pipe.getDrawer().drawString(AEColor.LIGHT_GRAY, 12, ammo+"", packRect.x+packRect.getWidth()/2, packRect.y+packRect.getHeight()/2 + stringH);
			}
		};
	}
}