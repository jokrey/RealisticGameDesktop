package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Stargate;
import jokrey.game.realistic_game.Stargate_Pair;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class TwoGateWorlds extends AbstractGateMap {
    @Override public Stargate_Pair[] initiateGates(Realistic_Game_Engine engine) {
        int splitterSize = engine.getLimitPerc_H(1.3);
        int stepH=engine.getLimitPerc_H(1.3);
        return new Stargate_Pair[] {
                new Stargate_Pair(
                    0, engine.getVirtualLimit_height()-splitterSize/2-stepH*3 - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getLimitPerc_H(50)-splitterSize/2-stepH*3 - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
                    Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255).brighter().brighter())
        };
    }

    @Override public MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        int splitterSize = engine.getLimitPerc_H(1.3);
        int stepH=engine.getLimitPerc_H(1.3);
        int stepW=engine.getLimitPerc_W(3);
        return new MapParticle[] {
                new MapParticle(0, engine.getLimitPerc_H(50)-splitterSize/2, 0, 0, 0, 0, engine.getVirtualLimit_width(),splitterSize, AnimationObject.RECT, new AEColor(255,40,40,40)),
                new MapParticle(engine.getVirtualLimit_width()-stepW*3	, engine.getLimitPerc_H(50)-splitterSize/2-stepH		, 0, 0, 0, 0, stepW*3	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)),
                new MapParticle(engine.getVirtualLimit_width()-stepW*2	, engine.getLimitPerc_H(50)-splitterSize/2-stepH*2	, 0, 0, 0, 0, stepW*2	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)),
                new MapParticle(engine.getVirtualLimit_width()-stepW	, engine.getLimitPerc_H(50)-splitterSize/2-stepH*3	, 0, 0, 0, 0, stepW		,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)),
                new MapParticle(0, engine.getVirtualLimit_height()-splitterSize/2-stepH		, 0, 0, 0, 0, stepW*3	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)),
                new MapParticle(0, engine.getVirtualLimit_height()-splitterSize/2-stepH*2	, 0, 0, 0, 0, stepW*2	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)),
                new MapParticle(0, engine.getVirtualLimit_height()-splitterSize/2-stepH*3	, 0, 0, 0, 0, stepW		,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)),

        };
//        TODO do also one with 4 worlds and a DHD (fake)
//        //TODO cool looking world stuff
    }
}
