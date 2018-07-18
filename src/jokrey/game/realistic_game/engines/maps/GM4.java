package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Stargate;
import jokrey.game.realistic_game.Stargate_Pair;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class GM4 extends AbstractGateMap {
    @Override public Stargate_Pair[] initiateGates(Realistic_Game_Engine engine) {
        return new Stargate_Pair[] {
                new Stargate_Pair(
                        engine.getLimitPerc_W(5) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                        engine.getLimitPerc_W(95) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                        Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)),
            new Stargate_Pair(
                    engine.getLimitPerc_W(15) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    engine.getLimitPerc_W(85) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0,0)),
            new Stargate_Pair(
                    engine.getLimitPerc_W(25) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    engine.getLimitPerc_W(75) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), AEColor.ORANGE),
            new Stargate_Pair(
                    engine.getLimitPerc_W(35) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    engine.getLimitPerc_W(65) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0)),
            new Stargate_Pair(
                    engine.getLimitPerc_W(45) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    engine.getLimitPerc_W(55) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(0,255,0)),
        };
    }

    @Override public MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
                new MapParticle(engine.getLimitPerc_W(10)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(20)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(30)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(40)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(50)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(60)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(70)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(80)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(90)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)),
        };
    }
}
