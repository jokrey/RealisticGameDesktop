package realistic_game.control_units;

import realistic_game.Player;
import realistic_game.engines.Realistic_Game_Engine;

public abstract class PlayerControlUnit {
	protected Player player;
	public Realistic_Game_Engine getEngine(){return player.engine;}
	public PlayerControlUnit(Player player_g) {
		player=player_g;
	}
	double lastMove = System.nanoTime()/1e9;
	public boolean canCalculateNewActions() {
		return System.nanoTime()/1e9-lastMove>0.1;
	}
	public void calculateNewActions() {
		performLeftAction = false;
		performRightAction = false;
		performUpAction = false;
		performAttackAction = false;
		performSwitchWeaponAction = false;
		doCalculations();
		if(performLeftAction||performRightAction||performUpAction||performAttackAction||performSwitchWeaponAction)
			lastMove = System.nanoTime()/1e9;
	}
	protected abstract void doCalculations();
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
}