package jokrey.game.realistic_game.starter;

import jokrey.game.realistic_game.engines.RealisticGamePipeline;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.game.realistic_game.engines.Realistic_Starwars_Game;
import jokrey.utilities.animation.implementations.java_fx.display.JavaFX_FullScreenStarter;
import jokrey.utilities.animation.implementations.java_fx.pipeline.AnimationDrawerJavaFX;

public class RealisticGame_Starwars_Starter {
	public static void main(String[] args) {
//		Swing_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Starwars_Game()), new RealisticGamePipeline(new AnimationDrawerSwing()));
		JavaFX_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Starwars_Game()), new RealisticGamePipeline(new AnimationDrawerJavaFX()));
	}
}