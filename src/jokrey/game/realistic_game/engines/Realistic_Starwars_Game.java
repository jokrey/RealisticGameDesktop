package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.care_package.CarePackage;
import jokrey.game.realistic_game.MapParticle;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;

import java.util.ArrayList;
import java.util.List;

public class Realistic_Starwars_Game extends Realistic_Game {
	@Override public String getName() {
		return "Starwars";
	}

    @Override public void doAdditionalLogic(Realistic_Game_Engine engine) {}
    @Override public List<AnimationObject> getAdditionalObjectsToDraw() {return new ArrayList<>();}

    @Override public double getPackageSpawnDelay() {return 4;}
    @Override public void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine) {}
	@Override public int getMapCount() {return 1;}
	@Override public void initiateMap(int map, Realistic_Game_Engine engine, List<MapParticle> mapParticles) {
    	mapParticles.clear();
//    	players.clear();
//		Dimension playerSize=Player.getStdSize(engine.getVirtualBoundaries());
//    	Player p1 = new Player((int)UTIL.getRandomNr(0, engine.getVirtualLimit_width()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getVirtualLimit_height()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), Color.magenta.brighter(), engine);
//    	p1.controlUnit=new ControlUnit_Human(p1, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_COMMA);
////    	p1.controlUnit=new ControlUnit_AI(p1);
//    	p1.weapons.add(AnimatedCloseCombatWeapon.getLightsaber(Color.green));
//    	p1.weapons.add(RangedWeapon.getWeapon_FORCE_PUSH(engine.getVirtualBoundaries()));
//    	p1.weapons.add(AnimatedCloseCombatWeapon.getForceLightening(engine.getVirtualBoundaries()));
//    	p1.addWearable(Wearable.getWearable_DoubleJump());
////    	p1.weapons.add(RangedWeapon.getWeapon_BLASTER(engine.getFrameSize().getSize()));
//    	players.add(p1);
//    	Player p2 = new Player((int)UTIL.getRandomNr(0, engine.getVirtualLimit_width()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getVirtualLimit_height()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), Color.green.brighter(), engine);
//    	p2.controlUnit=new ControlUnit_Human(p1, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_E);
////    	p2.weapons.add(RangedWeapon.getWeapon_ZAT_NIK_TEL(engine.getFrameSize().getSize()));
//    	p2.weapons.add(AnimatedCloseCombatWeapon.getLightsaber(Color.cyan));
//    	p2.weapons.add(RangedWeapon.getWeapon_FORCE_PUSH(engine.getVirtualBoundaries()));
//    	p2.weapons.add(AnimatedCloseCombatWeapon.getForceLightening(engine.getVirtualBoundaries()));
//    	p2.addWearable(Wearable.getWearable_DoubleJump());
////    	p2.weapons.add(RangedWeapon.getWeapon_BLASTER(engine.getFrameSize().getSize()));
////    	p2.keyCode_up=-1;//KI ENABLED
//    	players.add(p2);
//    	for(int i=0;i<8;i++) {
//        	Player p = new Player((int)UTIL.getRandomNr(0, engine.getVirtualLimit_width()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getVirtualLimit_height()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), Color.white, engine);
//        	p.controlUnit=new ControlUnit_AI(p);
//        	p.weapons.add(RangedWeapon.getWeapon_BLASTER(engine.getVirtualBoundaries()));
//        	players.add(p);
//    	}
    	if(map==1) {
        	mapParticles.add(new MapParticle(0, engine.getVirtualLimit_height() - engine.getLimitPerc_W(1), 0, 0, 0, 0, engine.getVirtualLimit_width(), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,255,128,0), false, 10));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(10)/2, engine.getLimitPerc_H(40) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(10), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(20)/2, engine.getLimitPerc_H(50) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(20), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(30)/2, engine.getLimitPerc_H(60) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(30), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(40)/2, engine.getLimitPerc_H(70) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(40), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(50)/2, engine.getLimitPerc_H(80) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(50), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
        	mapParticles.add(new MapParticle(engine.getLimitPerc_W(50) - engine.getLimitPerc_W(60)/2, engine.getLimitPerc_H(90) - engine.getLimitPerc_W(1)/2, 0, 0, 0, 0, engine.getLimitPerc_W(60), engine.getLimitPerc_W(1), AnimationObject.RECT, new AEColor(255,128,128,128)));
    	}
	}
}