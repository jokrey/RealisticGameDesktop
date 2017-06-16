package realistic_game;

import util.animation.engine.LimitRangeMovingAnimationObject;
import util.animation.engine.MovingAnimationObject;
import util.animation.pipeline.AnimationObject;
import util.animation.util.AEColor;
import util.animation.util.AEPoint;
import util.animation.util.AERect;
import util.animation.util.AESize;

import java.util.List;

public class Shot extends MovingAnimationObject {
	double damage;
	double blowbackMultipler;
	public Shot(AEPoint p, double vX, double fY, double width, double height,
			AEColor c, double damage_g, double blowbackMultipler_g) {
		super(0, 0, vX, 0, 0, fY, width, height, OVAL, c);
		damage=damage_g;
		blowbackMultipler=blowbackMultipler_g;
		setMid(p);
	}
	void startExplosion(List<LimitRangeMovingAnimationObject> particles) {
		if(drawParam instanceof AEColor) {
			AEColor c = (AEColor) drawParam;
			LimitRangeMovingAnimationObject.startExplosion(particles, (int)damage, getMidAsPoint(), new AESize(4,4), 
					new AnimationObject(getMidAsPoint().x-getW()*10,getMidAsPoint().y-getW()*10,getW()*20,getW()*20,AnimationObject.OVAL), 66, c, c.brighter(), c.darker());
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
		startExplosion(particles);
		return true;
	}
}