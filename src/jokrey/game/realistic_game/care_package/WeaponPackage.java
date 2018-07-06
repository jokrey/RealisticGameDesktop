package jokrey.game.realistic_game.care_package;

import jokrey.game.realistic_game.Player;
import jokrey.game.realistic_game.RangedWeapon;
import jokrey.game.realistic_game.Weapon;
import util.UTIL;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationObjectDrawer;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;

public class WeaponPackage extends CarePackage {
	public final Weapon w;
	public WeaponPackage(Weapon w_g, AESize frameSize) {
		super(UTIL.getRandomNr(0, frameSize.getWidth()-frameSize.getHeight()/22), 1, 0, 0, 0, 44, (int)frameSize.getHeight()/22, (int)frameSize.getHeight()/22, OVAL, new AEColor(255,255,0,0).brighter());
		w=w_g;
		drawParam = new AnimationObjectDrawer() {
			@Override public boolean canDraw(AnimationObject o, Object param) { return false; }
			@Override public void draw(AnimationObject o, AnimationPipeline pipe, Object drawParam_g) {
				isSolid=!(w instanceof RangedWeapon  && ((RangedWeapon)w).amunition == 0);
				AERect packRect = pipe.getDrawBoundsFor(o);
				pipe.getDrawer().drawOval(isSolid ? AEColor.RED.brighter() : AEColor.LIGHT_GRAY, packRect);
				w.setY(getY()+getH()/2);
				w.setX(getX()+getW()/4);
				w.drawWeap(pipe);
//				drawer.getDrawingUnitForParam(w, w.drawParam);
			}
		};
	}

    @Override public boolean onIntersectionWithPlayer(Player player) {
		if(player.weapons.contains(w)) {
			if(w instanceof RangedWeapon) {
				for(Weapon ow:player.weapons) {
					if(ow.equals(w)) {
						((RangedWeapon)ow).amunition+=((RangedWeapon)w).amunition;
						return true;
					}
				}
				return false;
			} else {
                w.weaponHolder=player;
				player.weapons.add(w);
				return true;
			}
		} else {
            if(player.weapons.size()>=3)return false;
			player.weapons.add(w);
            w.weaponHolder=player;
			return true;
		}
	}
}