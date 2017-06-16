package realistic_game.engines;

import realistic_game.MapParticle;
import realistic_game.RangedWeapon;
import realistic_game.care_package.CarePackage;
import realistic_game.care_package.WeaponPackage;
import util.UTIL;
import util.animation.pipeline.AnimationObject;
import util.animation.util.AEColor;

import java.util.ArrayList;
import java.util.List;

public class Realistic_Banana_Game extends Realistic_Game {
	@Override public String getName() {
		return "Banana";
	}

    @Override public void doAdditionalLogic(Realistic_Game_Engine engine) {}
    @Override public List<AnimationObject> getAdditionalObjectsToDraw() {return new ArrayList<>();}

    @Override public double getPackageSpawnDelay() {return 8;}
    @Override public void doPackageSpawn(List<CarePackage> packages, Realistic_Game_Engine engine) {
//		int whichPack = UTIL.getRandomNr(1, 4);
//		if(whichPack==2) {
//			packages.add(new HealthPackage(engine.getFrameSize().getSize()));
//		} else if(whichPack==3) {
			packages.add(new WeaponPackage(RangedWeapon.getWeapon_PlasmaGun(UTIL.getRandomNr(5, 45)), engine.getVirtualBoundaries()));
//		} else if(whichPack==4) {
//	//		int rand = UTIL.getRandomNr(1, 2);
//			Wearable w = null;
//	//		if(rand==1) {
//				w = Wearable.getWearable_ProtectiveVest();
//	//		} else if(rand==2) {
//	////			w = getWeapon_RIFLE();
//	//		}
//			packages.add(new WearablePackage(w, engine.getFrameSize().getSize()));
//		}
	}
	@Override public int getMapCount() {return 3;}
	@Override public void initiateMap(int map, Realistic_Game_Engine engine, List<MapParticle> mapParticles) {
    	mapParticles.clear();
//    	players.clear();
//		Dimension playerSize=Player.getStdSize(engine.getVirtualBoundaries());
//    	Player p1 = new Player((int)UTIL.getRandomNr(0, engine.getVirtualLimit_width()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getVirtualLimit_height()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), AEColor.lightGray.brighter(), engine);
////    	p1.controlUnit=new ControlUnit_Human(p1, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_COMMA);
//    	p1.controlUnit=new ControlUnit_AI(p1);
//    	p1.weapons.add(RangedWeapon.getWeapon_PlasmaGun(6, engine.getVirtualBoundaries()));
//    	players.add(p1);
//    	Player p2 = new Player((int)UTIL.getRandomNr(0, engine.getVirtualLimit_width()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getVirtualLimit_height()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), AEColor.green.brighter(), engine);
//    	p2.controlUnit=new ControlUnit_Human(p1, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_E);
////    	p2.keyCode_up=-1;//KI ENABLED
//    	p2.weapons.add(RangedWeapon.getWeapon_PlasmaGun(6, engine.getVirtualBoundaries()));
//    	players.add(p2);
//    	for(int i=0;i<4;i++) {
//        	Player p = new Player((int)UTIL.getRandomNr(0, engine.getVirtualLimit_width()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getVirtualLimit_height()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), .getRandomAEColor(), engine);
//        	p.controlUnit=new ControlUnit_AI(p);
//        	p.weapons.add(RangedWeapon.getWeapon_PlasmaGun(6, engine.getVirtualBoundaries()));
//        	players.add(p);
//    	}

    	if(map==1) {
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.15	)-100, engine.getVirtualLimit_height()*0.1, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5	)-100, engine.getVirtualLimit_height()*0.1, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.85	)-100, engine.getVirtualLimit_height()*0.1, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5	)-100, engine.getVirtualLimit_height()*0.19,  111, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.33	)-100, engine.getVirtualLimit_height()*0.28, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.66	)-100, engine.getVirtualLimit_height()*0.28, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5	)-100, engine.getVirtualLimit_height()*0.37,  -55, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.25	)-100, engine.getVirtualLimit_height()*0.46, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5	)-100, engine.getVirtualLimit_height()*0.46, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.75	)-100, engine.getVirtualLimit_height()*0.46, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5	)-100, engine.getVirtualLimit_height()*0.55,  55, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.33	)-100, engine.getVirtualLimit_height()*0.64, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.66	)-100, engine.getVirtualLimit_height()*0.64, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5	)-100, engine.getVirtualLimit_height()*0.73, -111, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.15	)-100, engine.getVirtualLimit_height()*0.82, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5	)-100, engine.getVirtualLimit_height()*0.82, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.85	)-100, engine.getVirtualLimit_height()*0.82, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.33	)-100, engine.getVirtualLimit_height()*0.91, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.66	)-100, engine.getVirtualLimit_height()*0.91, 0, 0, 0, 0, 200, 20, AnimationObject.RECT, new AEColor(255,255,255,255)));
    	} else if(map==2) {
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.1)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.2)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.7, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.3), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.3)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.55, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.45), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.4)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.4, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.6), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.25, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.75), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.6)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.4, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.6), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.7)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.55, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.45), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.8)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.7, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.3), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.9)-(engine.getVirtualLimit_width()*0.1)/2, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.1), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
    	} else if(map==3) {
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.1)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.2)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.3)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.4)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.5)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.6)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.7)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.8)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
        	mapParticles.add(new MapParticle((engine.getVirtualLimit_width()*0.9)-engine.getVirtualLimit_width()*0.02, engine.getVirtualLimit_height()*0.85, 0, 0, 0, 0, (int) (engine.getVirtualLimit_width()*0.02), (int) (engine.getVirtualLimit_height()*0.15), AnimationObject.RECT, new AEColor(255,255,255,255)));
    	}
	}
}