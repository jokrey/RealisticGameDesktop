package starter;

import realistic_game.engines.RealisticGamePipeline;
import realistic_game.engines.Realistic_Game_Engine;
import realistic_game.engines.Realistic_Starwars_Game;
import util.animation.implementations.java_fx.display.JavaFX_FullScreenStarter;
import util.animation.implementations.java_fx.pipeline.AnimationDrawerJavaFX;

public class RealisticGame_Starwars_Starter {
	public static void main(String[] args) {
//		Swing_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Starwars_Game()), new RealisticGamePipeline(new AnimationDrawerSwing()));
		JavaFX_FullScreenStarter.start(new Realistic_Game_Engine(new Realistic_Starwars_Game()), new RealisticGamePipeline(new AnimationDrawerJavaFX()));
	}
}