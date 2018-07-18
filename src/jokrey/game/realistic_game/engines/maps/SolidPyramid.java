package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Stargate;
import jokrey.game.realistic_game.Stargate_Pair;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class SolidPyramid extends AbstractGateMap {
    @Override public Stargate_Pair[] initiateGates(Realistic_Game_Engine engine) {
        return new Stargate_Pair[] {
                new Stargate_Pair(
                        0, 																							engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                        engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                        Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)),
        new Stargate_Pair(
                0, 																							engine.getLimitPerc_H(70) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getLimitPerc_H(70) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)),
        new Stargate_Pair(
                0, 																							engine.getLimitPerc_H(40) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getLimitPerc_H(40) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)),
        };
    }

    @Override public MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
                new MapParticle(engine.getLimitPerc_W(10)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(85	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(15	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(20)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(70	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(30	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(30)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(55	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(45	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(40)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(40	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(60	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(50)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(25	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(75	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(60)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(40	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(60	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(70)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(55	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(45	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(80)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(70	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(30	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        new MapParticle(engine.getLimitPerc_W(90)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(85	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(15	), AnimationObject.RECT, new AEColor(255,255,255,255)),
        };
    }
}
