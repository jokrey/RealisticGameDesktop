package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.engines.RealisticMap;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

public class Cubes extends RealisticMap {
    @Override
    protected MapParticle[] initiateMapParticles(Realistic_Game_Engine engine) {
        int size = engine.getLimitPerc_W(5);
        return new MapParticle[] {
                new MapParticle(engine.getLimitPerc_W(10)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(20)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(30)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(40)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(50)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(60)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(70)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(80)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(90)-size/2, engine.getVirtualLimit_height()-size, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),

                new MapParticle(engine.getLimitPerc_W(15)-size/2, engine.getVirtualLimit_height()-size*3, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(35)-size/2, engine.getVirtualLimit_height()-size*3, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(65)-size/2, engine.getVirtualLimit_height()-size*3, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(85)-size/2, engine.getVirtualLimit_height()-size*3, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),

                new MapParticle(engine.getLimitPerc_W(10)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(20)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(30)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(40)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(50)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(60)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(70)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(80)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(90)-size/2, engine.getVirtualLimit_height()-size*5, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),

                new MapParticle(engine.getLimitPerc_W(25)-size/2, engine.getVirtualLimit_height()-size*7, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(45)-size/2, engine.getVirtualLimit_height()-size*7, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(55)-size/2, engine.getVirtualLimit_height()-size*7, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),
                new MapParticle(engine.getLimitPerc_W(75)-size/2, engine.getVirtualLimit_height()-size*7, 0, 0, 0, 0, size, size, AnimationObject.RECT, new AEColor(255,255,255,255)),


        };
    }
}
