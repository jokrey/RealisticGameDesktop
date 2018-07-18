package jokrey.game.realistic_game.engines.maps;

import jokrey.game.realistic_game.MapParticle;
import jokrey.game.realistic_game.Player;
import jokrey.game.realistic_game.Shot;
import jokrey.game.realistic_game.Stargate_Pair;
import jokrey.game.realistic_game.engines.RealisticMap;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.pipeline.AnimationObject;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGateMap extends RealisticMap {
    private Stargate_Pair[] gates = new Stargate_Pair[0];

    @Override public MapParticle[] initiate(Realistic_Game_Engine engine) {
        gates = initiateGates(engine);
        if(gates==null)gates=new Stargate_Pair[0];
        return super.initiate(engine);
    }

    public abstract Stargate_Pair[] initiateGates(Realistic_Game_Engine engine);

    @Override public void doAdditionalLogic(Realistic_Game_Engine engine) {
        for(Stargate_Pair sgp:gates) {
            for(Player p:engine.getAlivePlayers())
                sgp.calculateTravelFor(p);
            for(Shot s:engine.shots)
                sgp.calculateTravelFor(s);
        }
    }
    @Override public List<AnimationObject> getAdditionalObjectsToDraw() {
        ArrayList<AnimationObject> list = new ArrayList<>();
        for(Stargate_Pair sgp:gates) {
            list.add(sgp.getSG1());
            list.add(sgp.getSG2());
        }
        return list;
    }
}
