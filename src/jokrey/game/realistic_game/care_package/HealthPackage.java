package jokrey.game.realistic_game.care_package;

import jokrey.game.realistic_game.Player;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObjectDrawer;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;

public class HealthPackage extends CarePackage {
	private int health = 10;
	public int getHealth() {return health;}
	public HealthPackage(AESize frameSize) {
		super(Realistic_Game_Engine.getRandomNr(0, frameSize.getWidth()-frameSize.getHeight()/22), -frameSize.getHeight()/22 + 1, 0, 0, 0, 44, (int)frameSize.getHeight()/22, (int)frameSize.getHeight()/22, OVAL, new AEColor(255,255,0,0).brighter());
		if(Realistic_Game_Engine.getRandomNr(0,5)==1)
			health= Realistic_Game_Engine.getRandomNr(25,40);
		else
			health=Realistic_Game_Engine.getRandomNr(5,18);
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
				pipe.getDrawer().fillTriangle(AEColor.RED.brighter(), new AERect(x,y,w,h));//to-do WHAT??
			}
		};
	}

	@Override public boolean onIntersectionWithPlayer(Player player) {
		player.setLifePs(player.getLifePs() + getHealth());
		return true;
	}
}