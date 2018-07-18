package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.engines.RealisticMap;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

//TODO
public class M4 extends RealisticMap {
    @Override
    protected MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
            new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10), 0			, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
        };
    }
}
