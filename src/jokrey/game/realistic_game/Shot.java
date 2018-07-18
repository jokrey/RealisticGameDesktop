package jokrey.game.realistic_game;

import jokrey.utilities.animation.engine.LimitRangeMovingAnimationObject;
import jokrey.utilities.animation.engine.MovingAnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AEPoint;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;
import util.UTIL;

import java.util.List;

public class Shot extends MovingAnimationObject {
	public final Player shooter;
	public final double damage;
	final double blowbackMultipler;
	public Shot(AEPoint p, double vX, double fY, double width, double height,
			AEColor c, double damage_g, double blowbackMultipler_g, Player shooter) {
		super(0, 0, vX, 0, 0, fY, width, height, OVAL, c);
		damage=damage_g;
		blowbackMultipler=blowbackMultipler_g;
		this.shooter=shooter;
		setMid(p);
		if(shooter==null)
			Thread.dumpStack();
	}
	void executeHitAnimation(List<LimitRangeMovingAnimationObject> particles) {
		if(drawParam instanceof AEColor) {
			AEColor c = (AEColor) drawParam;

			for (int counter=0;counter!=(int)Math.min(damage/2, 25);counter++)
				particles.add(new LimitRangeMovingAnimationObject(getMidAsPoint(), new AnimationObject(getMidAsPoint().x-getW()*10,getMidAsPoint().y-getW()*10,getW()*20,getW()*20,AnimationObject.OVAL),
                        getV_X()/3 + UTIL.getRandomNr(-100,100), UTIL.getRandomNr(getV_Y()-100, getV_Y()+100), c));
		}
	}
	/**
	 * 
	 * @param aeRect
	 * @param p
	 * @param particles
	 * @return if the shot should be removed
	 */
	public boolean hitParticle(AERect aeRect, AnimationObject p, List<LimitRangeMovingAnimationObject> particles) {
		executeHitAnimation(particles);
		return true;
	}
}