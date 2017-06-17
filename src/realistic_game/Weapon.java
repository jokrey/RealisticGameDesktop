package realistic_game;

import util.UTIL;
import util.animation.pipeline.AnimationObject;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AEColor;
import util.animation.util.AERect;
import util.animation.util.AESize;

public abstract class Weapon extends AnimationObject {
	public Player weaponHolder = null;
	public Weapon(int w, int h) {
		super(new AERect(0,0,w,h), OVAL);
	}

	public void draw(AnimationPipeline drawer) {
		if(weaponHolder!=null) {
			updatePosition();
		}
		drawWeap(drawer);
	}
	public abstract void drawWeap(AnimationPipeline drawer);
	public abstract String getName();
	public abstract double getDamage();
	public abstract double getDelay();
	public abstract double getBlowbackMultipler();
	public abstract void updatePosition();
	public void intersectsWith(@SuppressWarnings("unused") AnimationObject p){}
	@Override public boolean equals(Object o) {
		if(o instanceof Weapon) {
			Weapon wo=(Weapon)o;
			return getName().equals(wo.getName());
		} else
			return super.equals(o);
	}



	public static Weapon getWeaponRandom(AESize frameSize) {
		int randomNumber = UTIL.getRandomNr(0,15);
		switch(randomNumber) {
			case(0): return AnimatedCloseCombatWeapon.getKnife();
			case(1): return AnimatedCloseCombatWeapon.getForceLightening(frameSize);
			case(2): return AnimatedCloseCombatWeapon.getLightsaber(AEColor.getRandomColor());
			case(3): return RangedWeapon.getWeapon_BLASTER(frameSize);
			case(4): return RangedWeapon.getWeapon_Flamethrower(frameSize);
			case(5): return RangedWeapon.getWeapon_FORCE_PUSH(frameSize);
			case(6): return RangedWeapon.getWeapon_Grenade(frameSize);
			case(7): return RangedWeapon.getWeapon_JAFFA_STAFF(frameSize);
			case(8): return RangedWeapon.getWeapon_Mine(frameSize);
			case(9): return RangedWeapon.getWeapon_PISTOL(frameSize);
			case(10): return RangedWeapon.getWeapon_PlasmaGun(UTIL.getRandomNr(10,44));
			case(11): return RangedWeapon.getWeapon_RIFLE(frameSize);
			case(12): return RangedWeapon.getWeapon_SHOTGUN(frameSize);
			case(13): return RangedWeapon.getWeapon_SMG(frameSize);
			case(14): return RangedWeapon.getWeapon_SNIPER(frameSize);
			case(15): return RangedWeapon.getWeapon_ZAT_NIK_TEL(frameSize);
		}
		return RangedWeapon.getWeapon_RIFLE(frameSize);
	}
}