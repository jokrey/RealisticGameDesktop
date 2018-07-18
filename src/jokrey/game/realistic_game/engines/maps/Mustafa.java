package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.engines.RealisticMap;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class Mustafa extends RealisticMap {
    @Override
    protected MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
            new MapParticle(0, engine.getVirtualLimit_height() - engine.getLimitPerc_W(1), 0, 0, 0, 0, engine.getVirtualLimit_width(), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,255,128,0), false, 10),
            new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(40) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)),
            new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(20)/2, engine.getLimitPerc_H(50) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(20), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)),
            new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(30)/2, engine.getLimitPerc_H(60) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(30), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)),
            new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(40)/2, engine.getLimitPerc_H(70) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(40), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)),
            new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(50)/2, engine.getLimitPerc_H(80) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(50), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)),
            new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(60)/2, engine.getLimitPerc_H(90) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(60), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)),
        };
    }
}
