package jokrey.game.realistic_game;

import util.UTIL;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public abstract double getBlowbackMultipler();
	public abstract void updatePosition();
	public void intersectsWith(AnimationObject p){}
	public abstract double getDelay();
	@Override public boolean equals(Object o) {
		if(o instanceof Weapon) {
			Weapon wo=(Weapon)o;
			return getName().equals(wo.getName());
		} else
			return super.equals(o);
	}

	@Override public String toString() {
		return getName();
	}

	private long lastShot = System.nanoTime();
	public void used() {
		lastShot=System.nanoTime();
	}
	public boolean isInCooldown() {
		return (System.nanoTime()-lastShot)/1e9 < getDelay();
	}


	public static Weapon getWeaponRandom(AESize frameSize) {
	    List<Weapon> list = getHierarchicalWeaponList(frameSize);
		int randomNumber = UTIL.getRandomNr(0,list.size()-1);
		return list.get(randomNumber);
	}
	public static List<Weapon> getHierarchicalWeaponList(AESize frameSize) {
	    return Arrays.asList(
	            RangedWeapon.getWeapon_SNIPER(frameSize),
                AnimatedCloseCombatWeapon.getLightsaber(AEColor.getRandomColor()),
                RangedWeapon.getWeapon_SHOTGUN(frameSize),
                RangedWeapon.getWeapon_RIFLE(frameSize),
                RangedWeapon.getWeapon_JAFFA_STAFF(frameSize),
                RangedWeapon.getWeapon_BLASTER(frameSize),
                RangedWeapon.getWeapon_SMG(frameSize),
                RangedWeapon.getWeapon_Mine(frameSize),
                RangedWeapon.getWeapon_Flamethrower(frameSize),
                RangedWeapon.getWeapon_PISTOL(frameSize),
                AnimatedCloseCombatWeapon.getForceLightening(frameSize),
                RangedWeapon.getWeapon_Grenade(frameSize),
                RangedWeapon.getWeapon_PlasmaGun(UTIL.getRandomNr(10,44)),
                RangedWeapon.getWeapon_ZAT_NIK_TEL(frameSize),
                AnimatedCloseCombatWeapon.getKnife(),
                RangedWeapon.getWeapon_FORCE_PUSH(frameSize)
            );
    }
}