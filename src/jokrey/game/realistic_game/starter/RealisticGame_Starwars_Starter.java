package jokrey.game.realistic_game.starter;

import jokrey.game.realistic_game.AnimatedCloseCombatWeapon;
import jokrey.game.realistic_game.Player;
import jokrey.game.realistic_game.RangedWeapon;
import jokrey.game.realistic_game.Wearable;
import jokrey.game.realistic_game.control_units.ControlUnit_AI;
import jokrey.game.realistic_game.control_units.ControlUnit_Human;
import jokrey.game.realistic_game.engines.RealisticGamePipeline;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.game.realistic_game.engines.Realistic_Stargate_Game;
import jokrey.game.realistic_game.engines.Realistic_Starwars_Game;
import jokrey.utilities.animation.implementations.swing.display.Swing_FullScreenStarter;
import jokrey.utilities.animation.implementations.swing.pipeline.AnimationDrawerSwing;
import jokrey.utilities.animation.util.AEColor;

import java.util.function.Function;

public class RealisticGame_Starwars_Starter {
	public static void main(String[] args) {
		Realistic_Game_Engine engine = new Realistic_Game_Engine(new Realistic_Starwars_Game()) {
            @Override protected Player[] getWinners() {
                if(getAlivePlayers().stream().map((Function<Player, Object>) Player::getPlayerColor).anyMatch(clr -> clr.equals(AEColor.GREEN))) {
                    if(getAlivePlayers().size() == 1)
                        return new Player[] {getAlivePlayers().get(0)};
                    else
                        return null;
                } else {
                    return getAlivePlayers().toArray(new Player[0]);
                }
            }
        };

		engine.setPlayer(AEColor.GREEN,
				new ControlUnit_Human('w', 'a', 'd', 's', 'e', 'q'),
				AnimatedCloseCombatWeapon.getLightsaber(AEColor.GREEN),
				RangedWeapon.getWeapon_FORCE_PUSH(engine.getVirtualBoundaries()));
        engine.getPlayer(AEColor.GREEN).wearables.add(Wearable.getWearable_DoubleJump());

		AEColor[] enemyColors = {AEColor.GRAY, new AEColor(255, 255, 255),
                new AEColor(255, 255, 255),new AEColor(255, 255, 254),new AEColor(255, 254, 255),
                new AEColor(255, 254, 254),new AEColor(254, 255, 255),new AEColor(254, 255, 254),
//                new AEColor(254, 254, 255),new AEColor(254, 254, 254)
		};
		engine.setPlayer(enemyColors[0],
				new ControlUnit_AI(enemyColors),
				AnimatedCloseCombatWeapon.getLightsaber(AEColor.RED));
		for(int i=1;i<enemyColors.length;i++) {
            engine.setPlayer(enemyColors[i],
                    new ControlUnit_AI(enemyColors),
                    RangedWeapon.getWeapon_BLASTER(engine.getVirtualBoundaries()));
        }

		Swing_FullScreenStarter.start(engine, new RealisticGamePipeline(new AnimationDrawerSwing()));
//		JavaFX_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Starwars_Game()), new RealisticGamePipeline(new AnimationDrawerJavaFX()));
	}
}