package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.engines.RealisticMap;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class PartiallyMovingPlatforms extends RealisticMap {
    @Override
    protected MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
            new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10), 0			, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10), 0			, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10), 0			, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(19	), 111		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(37	), -55		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(25	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(75	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(55	), 55		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(64	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(64	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(73	), -111		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(91	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(91	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
        };
    }
}
