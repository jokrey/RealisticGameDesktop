package jokrey.game.realistic_game.control_units;

import jokrey.game.realistic_game.Player;

public class ControlUnit_Human extends PlayerControlUnit {
	private final char keyChar_up;
	private final char keyChar_left;
	private final char keyChar_right;
	private final char keyChar_attack;
	private final char keyChar_switchWeapon;
	public ControlUnit_Human(Player p, char keyChar_up_g, char keyChar_left_g, char keyChar_right_g, char keyChar_attack_g, char keyChar_switchWeapon_g) {
		super(p);
		keyChar_up=keyChar_up_g;
		keyChar_left=keyChar_left_g;
		keyChar_right=keyChar_right_g;
		keyChar_attack=keyChar_attack_g;
		keyChar_switchWeapon=keyChar_switchWeapon_g;
	}
	@Override public void doCalculations() {
	    if(getEngine().isKeyPressed(keyChar_up))			performUpAction=true;
	    if(getEngine().isKeyPressed(keyChar_left))			performLeftAction=true;
	    if(getEngine().isKeyPressed(keyChar_right))			performRightAction=true;
	    if(getEngine().isKeyPressed(keyChar_attack))		performAttackAction=true;
	    if(getEngine().isKeyPressed(keyChar_switchWeapon))	performSwitchWeaponAction=true;
	}
}