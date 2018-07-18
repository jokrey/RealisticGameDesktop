package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.care_package.CarePackage;
import jokrey.game.realistic_game.MapParticle;
import jokrey.utilities.animation.pipeline.AnimationObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Realistic_Game {
	public abstract String getName();

    public abstract void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine);
    public abstract double getPackageSpawnDelay();
	
    
	private int currentMap = -1;
	private RealisticMap[] maps = initiateMaps();
    public abstract RealisticMap[] initiateMaps();
    
    
    private RealisticMap getCurrentMap() {
        if(currentMap<0) throw new IllegalStateException("No Map Initialized");
        return maps[currentMap];
    }
	public final int getMapCount() {
		return maps.length;
	}
    public final void initiateMap(int mapID, Realistic_Game_Engine engine, List<MapParticle> mapParticles) {
        currentMap = mapID;
        mapParticles.addAll(Arrays.asList(getCurrentMap().initiate(engine)));
    }

    //allowed for override
    public void doAdditionalLogic(Realistic_Game_Engine engine) {
        getCurrentMap().doAdditionalLogic(engine);
    }
    public List<AnimationObject> getAdditionalObjectsToDraw() {
        if(currentMap<0)
            return Collections.emptyList();
        return getCurrentMap().getAdditionalObjectsToDraw();
    }
}