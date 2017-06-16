package realistic_game;

import util.animation.engine.MovingAnimationObject;
import util.animation.pipeline.AnimationObject;
import util.animation.util.AEColor;

public class Stargate_Pair {
	private final Stargate sg_1;
	private final Stargate sg_2;
	public Stargate_Pair(double x_1, double y_1, double x_2, double y_2, int sgH, AEColor clr) {
		sg_1 = new Stargate(x_1, y_1, sgH, clr);
		sg_2 = new Stargate(x_2, y_2, sgH, clr);
	}
//	public void draw(long delta, Graphics2D g) {
//		sg_1.draw(delta, g);
//		sg_2.draw(delta, g);
//	}
	public void calculateTravelFor(MovingAnimationObject p) {
		if(sg_1.getBounds().contains(p.getBounds())  ||  (p.getW()>sg_1.getW() && AnimationObject.collision(sg_1, p))) {//Particle.collision(new Particle(sg_1.getMid().x-1, sg_1.getY(), 2, sg_1.getH(), Particle.RECT), p)
			//int newX = (int) (sg_2.getMidAsPoint().x - (sg_1.getMidAsPoint().x - p.getX()));
			int newY = (int) (sg_2.getMidAsPoint().y - (sg_1.getMidAsPoint().y - p.getY()));
//			sg_1.fluktateAt(p.getMid(), p instanceof Player ? 12 : 6);
			p.setX(p.getV_X()<0?sg_2.getX()-1:sg_2.getX()+sg_2.getW()-(p.getW()-1));
			p.setY(newY);
//			sg_2.fluktateAt(new Point((int)(newX+p.getW()/2),(int) (newY+p.getH()/2)), p instanceof Player ? 12 : 6);
		} else if(sg_2.getBounds().contains(p.getBounds())  ||  (p.getW()>sg_2.getW() && AnimationObject.collision(sg_2, p))) {
			//int newX = (int) (sg_1.getMidAsPoint().x - (sg_2.getMidAsPoint().x - p.getX()));
			int newY = (int) (sg_1.getMidAsPoint().y - (sg_2.getMidAsPoint().y - p.getY()));
//			sg_2.fluktateAt(p.getMid(), p instanceof Player ? 12 : 6);
			p.setX(p.getV_X()<0?sg_1.getX()-1:sg_1.getX()+sg_1.getW()-(p.getW()-1));//must be outside of the gate! otherwise it would be directly transported back
			p.setY(newY);
//			sg_1.fluktateAt(new Point((int)(newX+p.getW()/2),(int) (newY+p.getH()/2)), p instanceof Player ? 12 : 6);
		}
	}
	public Stargate getSG1() {
		return sg_1;
	}
	public Stargate getSG2() {
		return sg_2;
	}
}