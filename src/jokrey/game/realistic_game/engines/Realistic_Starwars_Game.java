package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.care_package.CarePackage;
import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.engines.maps.Mustafa;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

import java.util.ArrayList;
import java.util.List;

public class Realistic_Starwars_Game extends Realistic_Game {
	@Override public String getName() {
		return "Starwars";
	}

    @Override public double getPackageSpawnDelay() {return 4;}

	@Override public RealisticMap[] initiateMaps() {
		return new RealisticMap[] {new Mustafa()};
	}

	@Override public void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine) {}

}