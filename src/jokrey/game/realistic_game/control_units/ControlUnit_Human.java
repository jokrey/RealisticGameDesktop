package jokrey.game.realistic_game.control_units;

import jokrey.game.realistic_game.Player;
import jokrey.game.realistic_game.engines.Realistic_Game_Engine;

public class ControlUnit_Human extends PlayerControlUnit {
	private final char keyChar_up;
	private final char keyChar_left;
	private final char keyChar_right;
	private final char keyChar_attack;
	private final char keyChar_switchWeapon;
	private final char keyChar_discardWeapon;
	public ControlUnit_Human(char up_key, char left_key, char right_key, char attack_key, char switchWeapon_key, char discardWeapon_key) {
		keyChar_up=up_key;
		keyChar_left=left_key;
		keyChar_right=right_key;
		keyChar_attack=attack_key;
		keyChar_switchWeapon=switchWeapon_key;
		keyChar_discardWeapon=discardWeapon_key;
	}
	@Override public void doCalculations(Player player, Realistic_Game_Engine engine) {
	    if(engine.isKeyPressed(keyChar_up))			    performUpAction=true;
	    if(engine.isKeyPressed(keyChar_left))			performLeftAction=true;
	    if(engine.isKeyPressed(keyChar_right))			performRightAction=true;
	    if(engine.isKeyPressed(keyChar_attack))		    performAttackAction=true;
		if(engine.isKeyPressed(keyChar_switchWeapon))	performSwitchWeaponAction=true;
		if(engine.isKeyPressed(keyChar_discardWeapon))	performDiscardWeaponAction=true;
	}
}