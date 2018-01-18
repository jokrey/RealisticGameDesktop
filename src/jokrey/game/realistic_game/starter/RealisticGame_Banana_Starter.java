package jokrey.game.realistic_game.starter;

import jokrey.game.realistic_game.engines.RealisticGamePipeline;
import jokrey.game.realistic_game.engines.Realistic_Banana_Game;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.utilities.animation.implementations.java_fx.display.JavaFX_FullScreenStarter;
import jokrey.utilities.animation.implementations.java_fx.pipeline.AnimationDrawerJavaFX;

public class RealisticGame_Banana_Starter {
	public static void main(String[] args) {
//		Swing_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Banana_Game()), new RealisticGamePipeline(new AnimationDrawerSwing()));
		JavaFX_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Banana_Game()), new RealisticGamePipeline(new AnimationDrawerJavaFX()));
	}
}