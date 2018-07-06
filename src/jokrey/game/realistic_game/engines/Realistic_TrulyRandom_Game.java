package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.*;
import jokrey.game.realistic_game.care_package.*;
import util.ObjectWithProbability;
import util.UTIL;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

import java.util.ArrayList;
import java.util.List;

public class Realistic_TrulyRandom_Game extends Realistic_Game {
	@Override public String getName() {
		return "Truly_Random";
	}

    ArrayList<Stargate_Pair> gates = new ArrayList<>();
    @Override public void doAdditionalLogic(Realistic_Game_Engine engine) {
    	for(Stargate_Pair sgp:gates) {
    		for(Player p:engine.getAlivePlayers())
    			sgp.calculateTravelFor(p);
    		for(Shot s:engine.shots)
    			sgp.calculateTravelFor(s);
    	}
    }
    @Override public List<AnimationObject> getAdditionalObjectsToDraw() {
		ArrayList<AnimationObject> list = new ArrayList<>();
		for(Stargate_Pair sgp:gates) {
			list.add(sgp.getSG1());
			list.add(sgp.getSG2());
		}
		return list;
	}

    @Override public double getPackageSpawnDelay() {return 2.6;}
    @Override public void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine) {
		int whichPack = UTIL.getRandomNr(1, 6);
//		if(whichPack==1) {//package spawn
//			String ammoFor = ObjectWithProbability.getObjectWithProbability(
//					new ObjectWithProbability("PISTOL", 100/8),
//					new ObjectWithProbability("SMG", 100/8),
//					new ObjectWithProbability("RIFLE", 100/8),
//					new ObjectWithProbability("SNIPER", 100/8),
//					new ObjectWithProbability("SHOTGUN", 100/8),
//					new ObjectWithProbability("GRENADE", 100/8),
//					new ObjectWithProbability("MINE", 100/8),
//					new ObjectWithProbability("FLAMETHROWER", 100/8)
//				).toString();
//			int ammo = 0;
//			if(ammoFor.equals("SMG")) {
//				ammo=UTIL.getRandomNr(8, 28);
//			} else if(ammoFor.equals("RIFLE")) {
//				ammo=UTIL.getRandomNr(5, 15);
//			} else if(ammoFor.equals("SNIPER")) {
//				ammo=UTIL.getRandomNr(1, 5);
//			} else if(ammoFor.equals("PISTOL")) {
//				ammo=UTIL.getRandomNr(8, 28);
//			} else if(ammoFor.equals("SHOTGUN")) {
//				ammo=UTIL.getRandomNr(3, 8);
//			} else if(ammoFor.equals("GRENADE")) {
//				ammo=UTIL.getRandomNr(1, 4);
//			} else if(ammoFor.equals("MINE")) {
//				ammo=UTIL.getRandomNr(1, 3);
//			} else if(ammoFor.equals("FLAMETHROWER")) {
//				ammo=UTIL.getRandomNr(50, 200);
//			}
//			packages.add(new AmmoPackage(ammo, ammoFor, engine.getVirtualBoundaries()));
//		} else
		if(whichPack==2) {
			packages.add(new HealthPackage(engine.getVirtualBoundaries()));
		} else if(whichPack==3) {
			Weapon w = Weapon.getWeaponRandom(engine.getVirtualBoundaries());
			boolean allPlayersHaveWeapon = true;
			for(Player p:engine.getAlivePlayers())
				if(!p.weapons.contains(w))
					allPlayersHaveWeapon = false;
			if(!allPlayersHaveWeapon)
				packages.add(new WeaponPackage(w, engine.getVirtualBoundaries()));
		} else if(whichPack==4) {
			int rand = UTIL.getRandomNr(1,4);
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
	@Override public int getMapCount() {return 9;}
	@Override public void initiateMap(int map, Realistic_Game_Engine engine, List<MapParticle> mapParticles) {
    	mapParticles.clear();
    	gates.clear();
    	if(map==1) {
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10), 0			, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10), 0			, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10), 0			, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(19	), 111		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(37	), -55		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(25	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(75	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(55	), 55		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(64	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(64	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(73	), -111		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(91	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(91	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
    	} else if(map==2) {
        	mapParticles.add(new MapParticle(0, engine.getVirtualLimit_height() - engine.getLimitPerc_W(1), 0, 0, 0, 0, engine.getVirtualLimit_width(), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,255,128,0), false, 10));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(40) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(20)/2, engine.getLimitPerc_H(50) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(20), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(30)/2, engine.getLimitPerc_H(60) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(30), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(40)/2, engine.getLimitPerc_H(70) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(40), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(50)/2, engine.getLimitPerc_H(80) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(50), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(60)/2, engine.getLimitPerc_H(90) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(60), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
    	} else if(map==3) {
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(10)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(20)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(30)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(40)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(60)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(70)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(80)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(90)-engine.getLimitPerc_W(2), engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
    	} else if(map==4) {
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(10	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(19	), 111		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(37	), -55		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(25	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(75	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(55	), 55		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(64	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(64	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(73	), -111		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(82	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(91	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(91	), 0		, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	gates.add(new Stargate_Pair(engine.getLimitPerc_W(33) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(91) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getLimitPerc_W(85) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)));
        	gates.add(new Stargate_Pair(engine.getLimitPerc_W(15) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getLimitPerc_W(66) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(91) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0,0)));
    	} else if(map==5) {
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(10)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(85	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(15	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(20)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(70	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(30	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(30)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(55	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(45	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(40)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(40	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(60	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(25	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(75	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(60)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(40	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(60	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(70)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(55	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(45	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(80)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(70	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(30	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(90)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(85	), 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_H(15	), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	gates.add(new Stargate_Pair(
        			0, 																							engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)));
        	gates.add(new Stargate_Pair(
        			0, 																							engine.getLimitPerc_H(70) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getLimitPerc_H(70) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)));
        	gates.add(new Stargate_Pair(
        			0, 																							engine.getLimitPerc_H(40) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getLimitPerc_H(40) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)));
    	} else if(map==6) {
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(10)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(20)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(30)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(40)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(60)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(70)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(80)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(90)-engine.getLimitPerc_W(2)/2, engine.getLimitPerc_H(85), 0, 0, 0, 0, engine.getLimitPerc_W(2), engine.getLimitPerc_H(15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(5) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getLimitPerc_W(95) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(15) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getLimitPerc_W(85) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0,0)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(25) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getLimitPerc_W(75) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), AEColor.ORANGE));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(35) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getLimitPerc_W(65) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(45) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getLimitPerc_W(55) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getVirtualLimit_height() - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(0,255,0)));
    	} else if(map==7) {
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(20	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(10	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(80	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(10	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(32	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(33	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(56	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(66	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(56	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(20	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(80	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(80	)-engine.getLimitPerc_W(15)/2, engine.getLimitPerc_H(80	) - (engine.getLimitPerc_W(10)/10)/2, 0, 0, 0, 0, engine.getLimitPerc_W(15), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));

        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(20) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//20 : 20
        			engine.getLimitPerc_W(20) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(80) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//20 : 80
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(66) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(56) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//66 : 60
        			engine.getLimitPerc_W(33) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(100) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//50 : 100
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,0,0)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(50) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(32) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//50 : 40
        			engine.getLimitPerc_W(50) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(100) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//50 : 100
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,255,0)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(33) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(56) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//33 : 60
        			engine.getLimitPerc_W(66) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(100) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//60 : 100
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,255,128,0)));
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(80) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(80) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//80 : 80
        			engine.getLimitPerc_W(80) - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height()))/2, engine.getLimitPerc_H(10) - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),//80 : 20
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,255,255)));
    	} else if(map==8) {
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50)-engine.getLimitPerc_W(5)/2, 0, 0, 0, 0, 0, engine.getLimitPerc_W(5),engine.getVirtualLimit_height(), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	int sgH = engine.getLimitPerc_H(40);
        	gates.add(new Stargate_Pair(
        			engine.getLimitPerc_W(50) - Stargate.getStargateStdWidth(sgH) - engine.getLimitPerc_W(5), engine.getLimitPerc_H(50) - sgH/2,
        			engine.getLimitPerc_W(50) + engine.getLimitPerc_W(5), engine.getLimitPerc_H(50) - sgH/2,
        			sgH, new AEColor(255,0,0,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(28	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(25	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(75	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(46	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(40	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(60	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(60	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(60	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(25	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(74	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(75	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(74	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(15	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(88	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(85	)-engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(88	) - 10, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(10)/10, AnimationObject.RECT, new AEColor(255,255,255,255)));
    	} else if(map==9) {//2 worlds  TODO do also one with 4 worlds and a DHD
    		int splitterSize = engine.getLimitPerc_H(1.3);
        	mapParticles.add(new MapParticle(0, engine.getLimitPerc_H(50)-splitterSize/2, 0, 0, 0, 0, engine.getVirtualLimit_width(),splitterSize, AnimationObject.RECT, new AEColor(255,40,40,40)));//splitter
        	int stepH=engine.getLimitPerc_H(1.3);
        	int stepW=engine.getLimitPerc_W(3);
        	//up left
        	mapParticles.add(new MapParticle(engine.getVirtualLimit_width()-stepW*3	, engine.getLimitPerc_H(50)-splitterSize/2-stepH		, 0, 0, 0, 0, stepW*3	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)));//splitter
        	mapParticles.add(new MapParticle(engine.getVirtualLimit_width()-stepW*2	, engine.getLimitPerc_H(50)-splitterSize/2-stepH*2	, 0, 0, 0, 0, stepW*2	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)));//splitter
        	mapParticles.add(new MapParticle(engine.getVirtualLimit_width()-stepW	, engine.getLimitPerc_H(50)-splitterSize/2-stepH*3	, 0, 0, 0, 0, stepW		,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)));//splitter
        	//down right
        	mapParticles.add(new MapParticle(0, engine.getVirtualLimit_height()-splitterSize/2-stepH		, 0, 0, 0, 0, stepW*3	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)));//splitter
        	mapParticles.add(new MapParticle(0, engine.getVirtualLimit_height()-splitterSize/2-stepH*2	, 0, 0, 0, 0, stepW*2	,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)));//splitter
        	mapParticles.add(new MapParticle(0, engine.getVirtualLimit_height()-splitterSize/2-stepH*3	, 0, 0, 0, 0, stepW		,stepH, AnimationObject.RECT, new AEColor(255,40,40,40)));//splitter
        	gates.add(new Stargate_Pair(
        			0, engine.getVirtualLimit_height()-splitterSize/2-stepH*3 - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			engine.getVirtualLimit_width() - Stargate.getStargateStdWidth(Stargate.getStargateStdHeight(engine.getVirtualLimit_height())), 	engine.getLimitPerc_H(50)-splitterSize/2-stepH*3 - Stargate.getStargateStdHeight(engine.getVirtualLimit_height()),
        			Stargate.getStargateStdHeight(engine.getVirtualLimit_height()), new AEColor(255,0,0,255).brighter().brighter()));


        	//TODO cool looking world stuff
    	}
	}
}