package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Stargate;
import jokrey.game.realistic_game.Stargate_Pair;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class Supergate extends AbstractGateMap {
    @Override public Stargate_Pair[] initiateGates(Realistic_Game_Engine engine) {
        int sgH = engine.getLimitPerc_H(37);
        return new Stargate_Pair[] {
            new Stargate_Pair(
                    engine.getLimitPerc_W(50) - Stargate.getStargateStdWidth(sgH) - engine.getLimitPerc_W(2.5), engine.getLimitPerc_H(65) - sgH/2,
                    engine.getLimitPerc_W(50) + engine.getLimitPerc_W(2.5), engine.getLimitPerc_H(65) - sgH/2,
                    sgH, new AEColor(255,0,0,255))

        };
    }

    @Override public MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        return new MapParticle[] {
                //splitter
            new MapParticle(engine.getLimitPerc_W(50)-engine.getLimitPerc_W(10)/2, 0, 0, 0, 0, 0, engine.getLimitPerc_W(10),engine.getVirtualLimit_height(), AnimationObject.RECT, new AEColor(255,255,255,255)),

                //gate step
            new MapParticle(engine.getLimitPerc_W(40	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(60	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),

            //further down
            new MapParticle(engine.getLimitPerc_W(25	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 + 25/3 	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(75	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 + 25/3	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(10	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 + 25/3*2	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(90	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 + 25/3*2	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),

            //further up
            new MapParticle(engine.getLimitPerc_W(25	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 - 25/3 	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(75	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 - 25/3	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(10	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 - 25/3*2	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
            new MapParticle(engine.getLimitPerc_W(90	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(75 - 25/3*2	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)),
        };
    }
}
