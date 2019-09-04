package jokrey.game.realistic_game.starter;

import jokrey.game.realistic_game.RangedWeapon;
import jokrey.game.realistic_game.control_units.ControlUnit_AI;
import jokrey.game.realistic_game.control_units.ControlUnit_Human;
import jokrey.game.realistic_game.engines.RealisticGamePipeline;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.game.realistic_game.engines.Realistic_Stargate_Game;
import jokrey.utilities.animation.implementations.swing.display.Swing_FullScreenStarter;
import jokrey.utilities.animation.implementations.swing.pipeline.AnimationDrawerSwing;
import jokrey.utilities.animation.util.AEColor;

public class RealisticGame_Stargate_Starter {
	public static void main(String[] args) {
		Realistic_Game_Engine engine = new Realistic_Game_Engine(new Realistic_Stargate_Game());

		engine.setPlayer(new AEColor(0,255,0),
				new ControlUnit_Human('w', 'a', 'd', 's', 'e', 'q'),
				RangedWeapon.getWeapon_JAFFA_STAFF(engine.getVirtualBoundaries()));

		engine.setPlayer(new AEColor(255,255,255),
				new ControlUnit_AI()
				,RangedWeapon.getWeapon_JAFFA_STAFF(engine.getVirtualBoundaries())
		);
		engine.setPlayer(new AEColor(0,128,255),
				new ControlUnit_AI(),
				RangedWeapon.getWeapon_JAFFA_STAFF(engine.getVirtualBoundaries()));
		engine.setPlayer(new AEColor(55,0,55),
				new ControlUnit_AI(),
				RangedWeapon.getWeapon_ZAT_NIK_TEL(engine.getVirtualBoundaries()));
		engine.setPlayer(AEColor.CYAN,
				new ControlUnit_AI(),
				RangedWeapon.getWeapon_SMG(engine.getVirtualBoundaries()),
				RangedWeapon.getWeapon_PISTOL(engine.getVirtualBoundaries()));
		engine.setPlayer(new AEColor(128,128,128),
				new ControlUnit_AI(),
				RangedWeapon.getWeapon_SMG(engine.getVirtualBoundaries()),
				RangedWeapon.getWeapon_PISTOL(engine.getVirtualBoundaries()));

		Swing_FullScreenStarter.start(engine, new RealisticGamePipeline(new AnimationDrawerSwing()));
//		JavaFX_FullScreenStarter.start(engine, new RealisticGamePipeline(new AnimationDrawerJavaFX()));
	}
}