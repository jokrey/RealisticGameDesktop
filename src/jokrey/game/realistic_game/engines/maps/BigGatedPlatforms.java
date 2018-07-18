package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Stargate;
import jokrey.game.realistic_game.Stargate_Pair;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class BigGatedPlatforms extends AbstractGateMap {
    @Override public Stargate_Pair[] initiateGates(Realistic_Game_Engine engine) {
        return new Stargate_Pair[] {
            new Stargate_Pair(
                    engine.getLimitPerc_W(20) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//20 : 20
                    engine.getLimitPerc_W(20) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(80) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//20 : 80
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)),
            new Stargate_Pair(
                    engine.getLimitPerc_W(66) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(56) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//66 : 60
                    engine.getLimitPerc_W(33) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(100) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//50 : 100
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0,0)),
            new Stargate_Pair(
                    engine.getLimitPerc_W(50) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(32) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//50 : 40
                    engine.getLimitPerc_W(50) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(100) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//50 : 100
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,255,0)),
            new Stargate_Pair(
                    engine.getLimitPerc_W(33) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(56) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//33 : 60
                    engine.getLimitPerc_W(66) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(100) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//60 : 100
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,128,0)),
            new Stargate_Pair(
                    engine.getLimitPerc_W(80) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(80) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//80 : 80
                    engine.getLimitPerc_W(80) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//80 : 20
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,255,255)),
        };
    }

    @Override public MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
            new MapParticle(engine.getLimitPerc_W(20	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(10	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(80	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(10	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(32	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(56	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(56	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(20	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(80	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(80	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(80	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
        };
    }
}
