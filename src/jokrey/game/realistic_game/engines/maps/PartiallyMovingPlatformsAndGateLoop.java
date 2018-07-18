package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Stargate;
import jokrey.game.realistic_game.Stargate_Pair;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class PartiallyMovingPlatformsAndGateLoop extends AbstractGateMap {
    @Override public Stargate_Pair[] initiateGates(Realistic_Game_Engine engine) {
        return new Stargate_Pair[] {
                new Stargate_Pair(engine.getLimitPerc_W(33) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(91) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                        engine.getLimitPerc_W(85) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)),
            new Stargate_Pair(engine.getLimitPerc_W(15) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                engine.getLimitPerc_W(66) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(91) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0,0))
        };
    }

    @Override public MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
            new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
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
