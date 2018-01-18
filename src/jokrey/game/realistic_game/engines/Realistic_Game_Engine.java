package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.*;
import jokrey.game.realistic_game.care_package.*;
import jokrey.game.realistic_game.control_units.ControlUnit_AI;
import jokrey.game.realistic_game.control_units.ControlUnit_Human;
import util.UTIL;
import jokrey.utilities.animation.engine.LimitRangeMovingAnimationObject;
import jokrey.utilities.animation.engine.TickEngine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AEPoint;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Realistic_Game_Engine extends TickEngine {
	private Realistic_Game realisticGame;
	public Realistic_Game_Engine(Realistic_Game realisticGame_g) {
		realisticGame=realisticGame_g;
	}

	@Override public double getInitialPixelsPerBox() {
		return -1;
	}
	@Override public AESize getVirtualBoundaries() {
		return new AESize(1920, 1080);
	}
	@Override public AEPoint getDrawerMidOverride() {
		return players==null||players.isEmpty()||
				players.get(0)==null?null:players.get(0).getMid();
	}

	@Override public void initiate() {
    	initiateEverythingButPlayers();
    	players = new ArrayList<>();
    	initiateNotInitiatedPlayers();
	}

	public void initiateNotInitiatedPlayers() {
		AESize playerSize=Player.getStdSize(getVirtualBoundaries());
//    	Player p1 = new Player((int)UTIL.getRandomNr(0, engine.getW()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getH()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), Color.lightGray.brighter(), engine);
//    	p1.controlUnit=new ControlUnit_Human(p1, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_COMMA);
////    	p1.weapons.add(AnimatedCloseCombatWeapon.getLightsaber(Color.red));
//    	p1.weapons.add(RangedWeapon.getWeapon_SMG(engine.getFrameSize().getSize()));
////    	p1.addWearable(Wearable.getWearable_SpeedBoost());
//    	players.add(p1);
		//System.out.println(isPlayerInitiated(Color.green));
		if(!isPlayerInitiated(new AEColor(255,0,255,0))) {
	    	Player p2 = new Player((int)UTIL.getRandomNr(0, getVirtualLimit_width()-playerSize.getWidth()), (int)UTIL.getRandomNr(0, getVirtualLimit_height()-playerSize.getHeight()), (int)playerSize.getWidth(), (int)playerSize.getHeight(), new AEColor(255,0,255,0), this);
	    	p2.controlUnit=new ControlUnit_Human(p2, 'w', 'a', 'd', 's', 'e');
			p2.weapons.add(RangedWeapon.getWeapon_SMG(getVirtualBoundaries()));
			p2.weapons.add(RangedWeapon.getWeapon_PlasmaGun(40));
			p2.weapons.add(RangedWeapon.getWeapon_ZAT_NIK_TEL(getVirtualBoundaries()));
			p2.weapons.add(RangedWeapon.getWeapon_FORCE_PUSH(getVirtualBoundaries()));
	    	players.add(p2);
		}
//    	Player p3 = new Player((int)UTIL.getRandomNr(0, engine.getW()-playerSize.getW()), (int)UTIL.getRandomNr(0, engine.getH()-playerSize.getH()), (int)playerSize.getW(), (int)playerSize.getH(), Color.cyan, engine);
//    	p3.controlUnit=new ControlUnit_Human(p3, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD9);
////    	p3.weapons.add(AnimatedCloseCombatWeapon.getLightsaber(Color.MAGENTA));
//    	p3.weapons.add(RangedWeapon.getWeapon_SMG(engine.getFrameSize().getSize()));
////    	p3.addWearable(Wearable.getWearable_SpeedBoost());
//    	players.add(p3);
    	AEColor[] clrA = {new AEColor(255,0,0,255), new AEColor(255,0,255,255), new AEColor(255, 255, 0, 255), new AEColor(255, 255, 128, 0), new AEColor(255, 255, 0, 0), new AEColor(255, 180, 180, 180)};
    	for(int i=0;i<clrA.length;i++) {
    		if(isPlayerInitiated(clrA[i]))continue;
        	Player p = new Player((int)UTIL.getRandomNr(0, getVirtualLimit_width()-playerSize.getWidth()), (int)UTIL.getRandomNr(0, getVirtualLimit_height()-playerSize.getHeight()), (int)playerSize.getWidth(), (int)playerSize.getHeight(), clrA[i], this);
        	p.controlUnit=new ControlUnit_AI(p);
        	p.weapons.add(RangedWeapon.getWeaponRandom(getVirtualBoundaries()));
        	p.setLifePs(150);
        	players.add(p);
    	}

    	double plyW = Player.getStdSize(getVirtualBoundaries()).getWidth();
    	double plyH = Player.getStdSize(getVirtualBoundaries()).getHeight();
    	for(Player p:players) {
    		while(AnimationObject.intersect(p, mapParticles)) {
    			p.setX(UTIL.getRandomNr(0, getVirtualLimit_width()-plyW));
    			p.setY(UTIL.getRandomNr(0, getVirtualLimit_height()-plyH));
    		}
    	}
	}
	public boolean isPlayerInitiated(AEColor clr) {
		for(Player p:players)
			if(p.getPlayerColor().equals(clr))
				return true;
		return false;
	}

	public void initiateEverythingButPlayers() {
		wonTime=0;

		mapParticles = new ArrayList<>();
		particles = new ArrayList<>();
		packages = new ArrayList<>();
		shots = new ArrayList<>();

    	int mapID = UTIL.getRandomNr(1, realisticGame.getMapCount());
    	realisticGame.initiateMap(mapID, this, mapParticles);
	}

	@Override public List<AnimationObject> getAllObjectsToDraw() {
		ArrayList<AnimationObject> list = new ArrayList<>();
		if(players!=null)list.addAll(players);
        if(mapParticles!=null)list.addAll(mapParticles);
        if(particles!=null)list.addAll(particles);
        if(packages!=null)list.addAll(packages);
        if(shots!=null)list.addAll(shots);
		list.addAll(realisticGame.getAdditionalObjectsToDraw());
		return list;
	}

	long wonTime = 0;
	class WinStreakDisplay {
		AEColor streakColor = null;
		int streakLength = 0;
	}
	WinStreakDisplay winStrDisplay = new WinStreakDisplay();
	public ArrayList<Player> players;
    public ArrayList<MapParticle> mapParticles;
    public ArrayList<LimitRangeMovingAnimationObject> particles;
    public ArrayList<CarePackage> packages;
    public ArrayList<Shot> shots;
    double lastPackageSpawn = System.nanoTime()/1e9;
    @Override protected void calculateTick() {
    	AERect frameSize = new AERect(getVirtualBoundaries());
    	Iterator<Player> players_iter = players.iterator();
    	while(players_iter.hasNext()) {
    		Player player = players_iter.next();
    		if(player.getLifePs()<=0) {
    			LimitRangeMovingAnimationObject.startExplosion(particles, 55, player.getMidAsPoint(), new AESize(4,4), 
    					new AnimationObject(player.getMidAsPoint().x-100,player.getMidAsPoint().y-100,200,200,AnimationObject.OVAL), 66, new AEColor(255,255,0,0).darker(), new AEColor(255,255,0,0), new AEColor(255,255,0,0).darker().darker());
    			players_iter.remove();
    		}
    	}

    	if(players.size()==1) {
    		if(wonTime!=0 && (System.nanoTime()-wonTime) / 1e9 > 4) {
	    		if(players.get(0).getPlayerColor().equals(winStrDisplay.streakColor)) {
	        		winStrDisplay.streakLength++;
	    		} else {
	        		winStrDisplay.streakLength=1;
	        		winStrDisplay.streakColor=players.get(0).getPlayerColor();
	    		}
            	initiateEverythingButPlayers();
            	initiateNotInitiatedPlayers();
    		} else if(wonTime==0) {
    			wonTime=System.nanoTime();
    		}
        }
//    	else
//        {
        	if(System.nanoTime()/1e9-lastPackageSpawn>realisticGame.getPackageSpawnDelay()) {
        		lastPackageSpawn=System.nanoTime()/1e9;
        		realisticGame.doPackageSpawn(packages, this);
        	}

        	realisticGame.doAdditionalLogic(this);

            Iterator<MapParticle> mrs_iter = mapParticles.iterator();
            while(mrs_iter.hasNext()) {
            	MapParticle mr = mrs_iter.next();
            	mr.move(getTicksPerSecond());
              	if(!frameSize.intersects((mr.getBounds()))) {
              		mrs_iter.remove();
            	}
              	mr.computeBoxBounce(new AERect(getVirtualBoundaries()), 1, 1);
            }
            Iterator<LimitRangeMovingAnimationObject> particles_iter = particles.iterator();
            while(particles_iter.hasNext()) {
            	LimitRangeMovingAnimationObject partcl = particles_iter.next();
            	partcl.setF_Y(333);
            	partcl.move(getTicksPerSecond());
            	if(!AnimationObject.collision(partcl, partcl.explBounds))
            		particles_iter.remove();
            }
            for(CarePackage mp:packages) {
            	mp.move(getTicksPerSecond());
            	mp.computeBoxStop(frameSize);
            	mp.computeStops(mapParticles);
            }

//PLAYERS
            for(int i=0;i<players.size();i++) {
            	Player player = players.get(i);
				player.computeBoxStop(frameSize);
                if(player.overlapingBoundsBottom(getVirtualLimit_height()-1)) {
    				if(player.getV_X()<0) 	player.setF_X(99);
    				if(player.getV_X()>0) 	player.setF_X(-99);
                } else								player.setF_X(0);
                player.computeStops(mapParticles);
                AnimationObject o;
                if((o = AnimationObject.intersectsWhich(new AnimationObject(new AERect(player.getX(), player.getY(), player.getW(), player.getH()+1), AnimationObject.RECT), mapParticles))!=null && ((MapParticle)o).isSolid) {//important
                	player.setV_Y(0);
                	if(Math.abs(player.getV_X())<Math.abs(((MapParticle)o).getV_X())) {
                		double lblVX = ((MapParticle)o).getV_X();
                		if(lblVX!=0) {
	                		player.additioFX=lblVX<0?-(Math.abs(lblVX)+99):Math.abs(lblVX)+99;
                		}
                	} else
                		player.additioFX=0;
    				if(player.getV_X()<0)	player.setF_X(99);
    				if(player.getV_X()>0)	player.setF_X(-99);
            	} else {
            		player.setV_X(player.getV_X() + (player.additioFX*(1.0/getTicksPerSecond())));
            		player.additioFX=0;
            	}

                ArrayList<AnimationObject> collidesWith = AnimationObject.collidesWith(player, mapParticles);
                for(AnimationObject p:collidesWith)
                	player.setLifePs(player.getLifePs() - ((MapParticle)p).damagePerSecond*(1.0/getTicksPerSecond()));

                player.controlUnit_compute();
                player.move(getTicksPerSecond());

                Weapon currentWeapon = player.getCurrentWeapon();
                if(currentWeapon instanceof CloseCombatWeapon) {
                	for(Player otherPlayer:players) {
	                	if(otherPlayer!=player) {
	                		if(AnimationObject.intersect(currentWeapon, otherPlayer.getCurrentWeapon())) {
	                			currentWeapon.intersectsWith(otherPlayer.getCurrentWeapon());
	                		}
	                		if(AnimationObject.collision(otherPlayer, currentWeapon) && !(currentWeapon instanceof AnimatedCloseCombatWeapon && !((AnimatedCloseCombatWeapon)currentWeapon).isAttacking())) {
		                		Shot virtualShot = new Shot(currentWeapon.getMid(), player.isLookingLeft()?-1:1, 0, 1, 1, otherPlayer.getPlayerColor(), currentWeapon.getDamage(), currentWeapon.getBlowbackMultipler());
	                			otherPlayer.gotHit(virtualShot);
	                			if(currentWeapon instanceof AnimatedCloseCombatWeapon)
	                				((AnimatedCloseCombatWeapon)currentWeapon).hitParticle(otherPlayer);
		                	}
	                	}
                	}
                }

                Iterator<CarePackage> packages_iter = packages.iterator();
                while(packages_iter.hasNext()) {
                	CarePackage mp = packages_iter.next();
                	if(AnimationObject.collision(mp, player)) {
	                	if(mp instanceof AmmoPackage) {
	                		AmmoPackage amp = (AmmoPackage)mp;
	                		for(Weapon w:player.weapons) {
	                			if(w instanceof RangedWeapon && w.getName().equals(amp.ammoFor)) {
	                				((RangedWeapon)w).amunition+=amp.ammo;
			                		packages_iter.remove();
			                		break;
	                			}
	                		}
	                	} else if(mp instanceof HealthPackage) {
	                		player.setLifePs(player.getLifePs() + ((HealthPackage)mp).getHealth());
//	                		player.draw(g);
	                		packages_iter.remove();
	                	} else if(mp instanceof WeaponPackage) {
                			Weapon w = ((WeaponPackage)mp).w;
	                		if(player.weapons.contains(w)) {
	                			if(w instanceof RangedWeapon) {
			                		for(Weapon ow:player.weapons) {
			                			if(ow.equals(w)) {
			                				((RangedWeapon)ow).amunition+=((RangedWeapon)ow).getInitAmmo();
					                		packages_iter.remove();
					                		break;
			                			}
			                		}
	                			} else {
		                			player.weapons.add(w);
		                			packages_iter.remove();
	                			}
	                		} else {
	                			player.weapons.add(w);
	                			packages_iter.remove();
	                		}
	                	} else if(mp instanceof WearablePackage) {
	                		player.addWearable(((WearablePackage)mp).w);
	                		packages_iter.remove();
	                	}
                	}
                }
            }
            Iterator<Shot> shots_iter = shots.iterator();
            try {
	            while(shots_iter.hasNext()) {
	            	Shot shot = shots_iter.next();
	            	shot.move(getTicksPerSecond());
	            	if(!frameSize.intersects(shot.getBounds())) {
	            		if(shot.hitParticle(shot.getBounds(), null, particles))
	            			shots_iter.remove();
	            		continue;
	            	}
	            	for(Player player:players) {
	        			if(player.getCurrentWeapon() instanceof CloseCombatWeapon && AnimationObject.collision(player.getCurrentWeapon(), shot)) {
	        				player.getCurrentWeapon().intersectsWith(shot);
	        			}
	                	if(AnimationObject.collision(player, shot)) {
	                		player.gotHit(shot);
	                		if(shot.hitParticle(shot.getBounds(), player, particles))
	                    		shots_iter.remove();
	                		shot=null;
	                		break;
	                	}
	            	}
	            	if(shot==null)continue;
	            	for(MapParticle lp:mapParticles) {
	            		if(lp.isSolid && lp.getBounds().intersects(shot.getBounds())) {
	                		if(shot.hitParticle(shot.getBounds(), lp, particles))
	                			shots_iter.remove();
	                		break;
	            		}
	            	}
	            }
            } catch(Exception ex) {
            	ex.printStackTrace();
            }
//        }
    }
}