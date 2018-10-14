package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.care_package.CarePackage;
import jokrey.game.realistic_game.care_package.WeaponPackage;
import jokrey.game.realistic_game.RangedWeapon;
import jokrey.game.realistic_game.engines.maps.PartiallyMovingPlatforms;
import jokrey.game.realistic_game.engines.maps.Mustafa;
import jokrey.game.realistic_game.engines.maps.Cubes;
import java.util.List;

public class Realistic_Banana_Game extends Realistic_Game {
	@Override public String getName() {
		return "Banana";
	}
    @Override public double getPackageSpawnDelay() {return 8;}

	@Override public RealisticMap[] initiateMaps() {
		return new RealisticMap[] {
				new PartiallyMovingPlatforms(),
				new Mustafa(),
				new Cubes()
		};
	}

	@Override public void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine) {
			packages.add(new WeaponPackage(RangedWeapon.getWeapon_PlasmaGun(Realistic_Game_Engine.getRandomNr(5, 45)), engine.getVirtualBoundaries()));
	}
}