package jokrey.game.realistic_game.control_units;

import jokrey.game.realistic_game.engines.Realistic_Game_Engine;
import jokrey.game.realistic_game.Player;

public abstract class PlayerControlUnit {
	private long lastMove = System.nanoTime();
	private boolean canCalculateNewActions() {
		return (System.nanoTime()-lastMove)/1e9>0.1;
	}
	public boolean calculateNewActions(Player player, Realistic_Game_Engine engine) {
		if(!canCalculateNewActions())return false;
		performLeftAction = false;
		performRightAction = false;
		performUpAction = false;
		performAttackAction = false;
		performSwitchWeaponAction = false;
		performDiscardWeaponAction=false;
		doCalculations(player, engine);
		if(performLeftAction||performRightAction||performUpAction||performAttackAction||performSwitchWeaponAction||performDiscardWeaponAction)
			lastMove = System.nanoTime();
		return true;
	}
	protected abstract void doCalculations(Player player, Realistic_Game_Engine engine);
	public boolean performLeftAction = false;
		public boolean isLeftAction() {return performLeftAction;}
	public boolean performRightAction = false;
		public boolean isRightAction() {return performRightAction;}
	public boolean performUpAction = false;
		public boolean isUpAction() {return performUpAction;}
	public boolean performAttackAction = false;
		public boolean isAttackAction() {return performAttackAction;}
	public boolean performSwitchWeaponAction = false;
		public boolean isSwitchAction() {return performSwitchWeaponAction;}
	public boolean performDiscardWeaponAction = false;
		public boolean isDiscardAction() {return performDiscardWeaponAction;}
}