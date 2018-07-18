package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.MapParticle;
import jokrey.utilities.animation.pipeline.AnimationObject;

import java.util.Collections;
import java.util.List;

public abstract class RealisticMap {
    public MapParticle[] initiate(Realistic_Game_Engine engine) {
        return initiateMapParticles(engine);
    }
    protected abstract MapParticle[] initiateMapParticles(Realistic_Game_Engine engine);
    public void doAdditionalLogic(Realistic_Game_Engine engine) {}
    public List<AnimationObject> getAdditionalObjectsToDraw() {return Collections.emptyList();}
}
