package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.*;
import jokrey.game.realistic_game.care_package.*;
import jokrey.game.realistic_game.engines.maps.*;
import util.UTIL;

import java.util.List;

public class Realistic_Stargate_Game extends Realistic_Game {
	@Override public String getName() {
		return "Stargate";
	}

    @Override public double getPackageSpawnDelay() {return 4;}
    @Override public void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine) {
		int whichPack = UTIL.getRandomNr(2, 4);
		if(whichPack==2) {
			packages.add(new HealthPackage(engine.getVirtualBoundaries()));
		} else if(whichPack==3) {
			int rand = UTIL.getRandomNr(1, 7);
			Weapon w = null;
			if(rand==1) {
				w = RangedWeapon.getWeapon_SMG(engine.getVirtualBoundaries());
			} else if(rand==2) {
				w = RangedWeapon.getWeapon_RIFLE(engine.getVirtualBoundaries());
			} else if(rand==3) {
				w = RangedWeapon.getWeapon_SNIPER(engine.getVirtualBoundaries());
			} else if(rand==4) {
				w = RangedWeapon.getWeapon_JAFFA_STAFF(engine.getVirtualBoundaries());
			} else if(rand==5) {
				w = RangedWeapon.getWeapon_ZAT_NIK_TEL(engine.getVirtualBoundaries());
			} else if(rand==6) {
				w = RangedWeapon.getWeapon_SHOTGUN(engine.getVirtualBoundaries());
			} else if(rand==7) {
				w = RangedWeapon.getWeapon_Grenade(engine.getVirtualBoundaries());
			}
			boolean allPlayersHaveWeapon = true;
			for(Player p:engine.getAlivePlayers())
				if(!p.weapons.contains(w))
					allPlayersHaveWeapon = false;
			if(!allPlayersHaveWeapon)
				packages.add(new WeaponPackage(w, engine.getVirtualBoundaries()));
		}
	}

	@Override
	public RealisticMap[] initiateMaps() {
		return new RealisticMap[] {
				new TwoGateWorlds(),
				new SolidPyramid(),
				new PartiallyMovingPlatformsAndGateLoop(),
				new GM4(),
				new BigGatedPlatforms(),
				new Supergate()
		};
	}
}