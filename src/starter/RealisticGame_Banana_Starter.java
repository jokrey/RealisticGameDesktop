package starter;

import realistic_game.engines.RealisticGamePipeline;
import realistic_game.engines.Realistic_Banana_Game;
import realistic_game.engines.Realistic_Game_Engine;
import util.animation.implementations.java_fx.display.JavaFX_FullScreenStarter;
import util.animation.implementations.java_fx.pipeline.AnimationDrawerJavaFX;

public class RealisticGame_Banana_Starter {
	public static void main(String[] args) {
//		Swing_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Banana_Game()), new RealisticGamePipeline(new AnimationDrawerSwing()));
		JavaFX_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Banana_Game()), new RealisticGamePipeline(new AnimationDrawerJavaFX()));
	}
}