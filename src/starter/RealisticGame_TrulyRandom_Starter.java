package starter;

import realistic_game.engines.RealisticGamePipeline;
import realistic_game.engines.Realistic_Game_Engine;
import realistic_game.engines.Realistic_TrulyRandom_Game;
import util.animation.implementations.java_fx.display.JavaFX_FullScreenStarter;
import util.animation.implementations.java_fx.pipeline.AnimationDrawerJavaFX;
import util.animation.implementations.swing.display.Swing_FullScreenStarter;
import util.animation.implementations.swing.pipeline.AnimationDrawerSwing;

public class RealisticGame_TrulyRandom_Starter {
	public static void main(String[] args) {
		Swing_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_TrulyRandom_Game()), new RealisticGamePipeline(new AnimationDrawerSwing()));
//		JavaFX_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_TrulyRandom_Game()), new RealisticGamePipeline(new AnimationDrawerJavaFX()));
	}
}