package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.*;
import jokrey.game.realistic_game.care_package.*;
import jokrey.game.realistic_game.engines.maps.*;

import java.util.List;

public class Realistic_TrulyRandom_Game extends Realistic_Game {
	@Override public String getName() {
		return "Truly_Random";
	}

    @Override public double getPackageSpawnDelay() {return 2.6;}

    @Override public RealisticMap[] initiateMaps() {
        return new RealisticMap[] {
                new TwoGateWorlds(),
                new PartiallyMovingPlatformsAndGateLoop(),
                new SolidPyramid(),
                new GM4(),
                new BigGatedPlatforms(),
                new Supergate(),
                new PartiallyMovingPlatforms(),
                new Mustafa(),
                new Cubes(),
                new M4()
        };
    }

    @Override public void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine) {
		int whichPack = Realistic_Game_Engine.getRandomNr(1, 6);
		if(whichPack==2) {
			packages.add(new HealthPackage(engine.getVirtualBoundaries()));
		} else if(whichPack==3) {
			Weapon w = Weapon.getWeaponRandom(engine.getVirtualBoundaries());
//			boolean allPlayersHaveWeapon = true;
//			for(Player p:engine.getAlivePlayers())
//				if(!p.weapons.contains(w))
//					allPlayersHaveWeapon = false;
//			if(!allPlayersHaveWeapon)
				packages.add(new WeaponPackage(w, engine.getVirtualBoundaries()));
			if(Realistic_Game_Engine.getRandomNr(0,40) == 10)//rare
				packages.add(new TeleporterPackage(engine.getVirtualBoundaries()));
		} else if(whichPack==4) {
			int rand = Realistic_Game_Engine.getRandomNr(1,4);
			Wearable w = null;
			if(rand==1) {
				w = Wearable.getWearable_ProtectiveVest();
			} else if(rand==2) {
				w = Wearable.getWearable_DoubleJump();
			} else if(rand==3) {
				w = Wearable.getWearable_SpeedBoost();
			} else if(rand==4) {
				w = Wearable.getWearable_JetPack();
			}
			packages.add(new WearablePackage(w, engine.getVirtualBoundaries()));
		}
	}
}