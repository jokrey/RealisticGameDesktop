package realistic_game.engines;

import realistic_game.MapParticle;
import realistic_game.care_package.CarePackage;
import util.animation.pipeline.AnimationObject;

import java.util.List;

public abstract class Realistic_Game {
	public abstract String getName();
	public abstract int getMapCount();
	public abstract void initiateMap(int mapID, Realistic_Game_Engine engine, List<MapParticle> mapParticles);
	public abstract void doAdditionalLogic(Realistic_Game_Engine engine);
	public abstract List<AnimationObject> getAdditionalObjectsToDraw();
	public abstract void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine);
	public abstract double getPackageSpawnDelay();
}