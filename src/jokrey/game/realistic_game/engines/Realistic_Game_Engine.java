package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.*;
import jokrey.game.realistic_game.care_package.*;
import jokrey.game.realistic_game.control_units.PlayerControlUnit;
import jokrey.utilities.animation.engine.MovingAnimationObject;
import util.UTIL;
import jokrey.utilities.animation.engine.LimitRangeMovingAnimationObject;
import jokrey.utilities.animation.engine.TickEngine;
import jokrey.utilities.animation.pipeline.AnimationObject;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AEPoint;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.util.AESize;
import java.util.*;

public class Realistic_Game_Engine extends TickEngine {
	private Realistic_Game realisticGame;
    private HashMap<AEColor, Weapon[]> players_orig_weapons = new HashMap<>();
	private HashMap<AEColor, Player> players = new HashMap<>();
	public Player getPlayer(AEColor color) {
	    return players.get(color);
    }
	public Collection<Player> getPlayers() {
	    return players.values();
    }
	public ArrayList<Player> getAlivePlayers() {
	    Collection<Player> col = getPlayers();
	    ArrayList<Player> list = new ArrayList<>(col.size());
	    for(Player p:col)
            if(p.getLifePs()>0)
                list.add(p);
	    return list;
    }
	private Player mainPlayer = null;
	public void setPlayer(AEColor color, PlayerControlUnit control, Weapon... weapons) {
        AESize playerSize=Player.getStdSize(getVirtualBoundaries());
        Player p = new Player((int)playerSize.getWidth(), (int)playerSize.getHeight(), this);
        p.controlUnit=control;
        p.weapons.addAll(Arrays.asList(weapons));

	    if(mainPlayer==null)mainPlayer=p;
        players.put(color, p);
        players_orig_weapons.put(color, weapons);
        p.setPlayerColor(color);
    }
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
		return mainPlayer==null?null:mainPlayer.getMid();
	}

	@Override public void initiate() {
    	initiateEverythingButPlayers();
    	initiateNotInitiatedPlayers();
	}


	public void initiateNotInitiatedPlayers() {
        for(AEColor key:players.keySet()) {
            Player p = players.get(key);

            if(p.getLifePs()<=0) {
                p.wearables.clear();
                p.weapons.clear();
                p.weapons.addAll(Arrays.asList(players_orig_weapons.get(key)));
                for(Weapon w:p.weapons)
                    if(w instanceof RangedWeapon)
                        ((RangedWeapon)w).amunition = ((RangedWeapon)w).getInitAmmo();
                p.setLifePs(100);
                p.setX((int) UTIL.getRandomNr(0, getVirtualLimit_width() - p.getW()));
                p.setY((int) UTIL.getRandomNr(0, getVirtualLimit_height() - p.getH()));
            }
            while(AnimationObject.intersect(p, mapParticles)) {
                p.setX(UTIL.getRandomNr(0, getVirtualLimit_width()-p.getW()));
                p.setY(UTIL.getRandomNr(0, getVirtualLimit_height()-p.getH()));
            }
        }
	}

	public void initiateEverythingButPlayers() {
        winStreakData.wonAtTime = 0;

		mapParticles = new ArrayList<>();
		particles = new ArrayList<>();
		packages = new ArrayList<>();
		shots = new ArrayList<>();

    	int mapID = UTIL.getRandomNr(0, realisticGame.getMapCount()-1);
    	System.out.println(mapID);
    	realisticGame.initiateMap(mapID, this, mapParticles);
	}

	@Override public List<AnimationObject> getAllObjectsToDraw() {
		ArrayList<AnimationObject> list = new ArrayList<>();
        if(players!=null)list.addAll(getAlivePlayers());
        if(mapParticles!=null)list.addAll(mapParticles);
        if(particles!=null)list.addAll(particles);
        if(packages!=null)list.addAll(packages);
        if(shots!=null)list.addAll(shots);
		list.addAll(realisticGame.getAdditionalObjectsToDraw());
		return list;
	}

	public static class WinStreakData {
		AEColor streakColor = null;
		int streakLength = 0;
        long wonAtTime = 0;
	}
	public WinStreakData winStreakData = new WinStreakData();
    public ArrayList<MapParticle> mapParticles;
    public ArrayList<LimitRangeMovingAnimationObject> particles;
    public ArrayList<CarePackage> packages;
    public ArrayList<Shot> shots;
    private double lastPackageSpawn = System.nanoTime()/1e9;
    @Override protected void calculateTick() {
    	AERect frameSize = new AERect(getVirtualBoundaries());
        calculateRoundOver();
        calculateCarePackageSpawn();
        realisticGame.doAdditionalLogic(this);
        moveAllNonPlayers(frameSize);

        for (Player player : getAlivePlayers()) {
            player.computeInsideBoxStop(frameSize);

            for(Weapon w:player.weapons)
                if(w instanceof RangedWeapon && ((RangedWeapon)w).amunition==0)
                    player.throwAwayWeapon(w);

            //calculate restitution on screen bottom limit
            if (player.overlapingBoundsBottom(getVirtualLimit_height() - 1)) {
                if (player.getV_X() < 0) player.setF_X(99);
                if (player.getV_X() > 0) player.setF_X(-99);
            } else player.setF_X(0);

            player.computeStops(mapParticles);

            computePlayersDragMovement(player);
            calculateDamageFromDamagingParticles(player);
            player.controlUnit_compute();//VERY VERY IMPORTANT - calculates the players actions
            player.move(getTicksPerSecond());

            calculateCloseCombatLogic(player);
            calculateCarePackageIntersections(player);
            calculateKilled(player);
        }

        calculateShotLogic(frameSize);
    }

    private void computeAppropiateFrameStop(AERect frameSize, MovingAnimationObject thing) {
        int maxY = (int) (frameSize.getHeight() - (int)thing.getH());
        int maxX = (int) (frameSize.getWidth() - (int)thing.getW());
        if (thing.getY() > maxY) {
            thing.setY(maxY);
            thing.setV_Y(0);
        } else if (thing.getY() < -thing.getH()+1) {
            thing.setY(-thing.getH()+1);
            thing.setV_Y(0);
        }
        if (thing.getX() > maxX) {
            thing.setX(maxX);
            thing.setV_X(0);
        } else if (thing.getX() < 0) {
            thing.setX(0);
            thing.setV_X(0);
        }
    }

    private void calculateCarePackageIntersections(Player player) {
        Iterator<CarePackage> packages_iter = packages.iterator();
        while(packages_iter.hasNext()) {
            CarePackage mp = packages_iter.next();
            if(AnimationObject.collision(mp, player)) {
                if(mp.onIntersectionWithPlayer(player))
                    packages_iter.remove();
            }
        }
    }

    private void calculateCloseCombatLogic(Player player) {
        Weapon currentWeapon = player.getCurrentWeapon();
        if(currentWeapon instanceof CloseCombatWeapon) {
            for(Player otherPlayer: getAlivePlayers()) {
                if(otherPlayer!=player) {
                    if(AnimationObject.intersect(currentWeapon, otherPlayer.getCurrentWeapon())) {
                        currentWeapon.intersectsWith(otherPlayer.getCurrentWeapon());
                    }
                    if(AnimationObject.collision(otherPlayer, currentWeapon) && !(currentWeapon instanceof AnimatedCloseCombatWeapon && !((AnimatedCloseCombatWeapon)currentWeapon).isAttacking())) {
                        Shot virtualShot = new Shot(currentWeapon.getMid(), player.isLookingLeft()?-1:1, 0, 1, 1, otherPlayer.getPlayerColor(), currentWeapon.getDamage(), currentWeapon.getBlowbackMultipler(), player);
                        otherPlayer.gotHit(virtualShot);
                        player.stats.add_dealt_damage(currentWeapon.getDamage());
                        otherPlayer.stats.add_received_damage(currentWeapon.getDamage());
                        if(otherPlayer.getLifePs()<=0)player.stats.add_kill();
                        if(currentWeapon instanceof AnimatedCloseCombatWeapon)
                            ((AnimatedCloseCombatWeapon)currentWeapon).hitParticle(otherPlayer);
                    }
                }
            }
        }
    }

    private void calculateDamageFromDamagingParticles(Player player) {
        ArrayList<AnimationObject> collidesWith = AnimationObject.collidesWith(player, mapParticles);
        for(AnimationObject p:collidesWith) {
            double damage = ((MapParticle) p).damagePerSecond * (1.0 / getTicksPerSecond());
            player.setLifePs(player.getLifePs() - damage);
            player.stats.add_received_damage(damage);
        }
    }

    private void computePlayersDragMovement(Player player) {
        AnimationObject o;
        if((o = AnimationObject.intersectsWhich(new AnimationObject(new AERect(player.getX(), player.getY(), player.getW(), player.getH()+1), AnimationObject.RECT), mapParticles))!=null && ((MapParticle)o).isSolid) {//important
            player.setV_Y(0); //should already happened with "computeStops", but just to be sure..
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
            player.setV_X(player.getV_X() + (player.additioFX*(1.0/getTicksPerSecond()))); //no idea why this is done tbh
            player.additioFX=0;
        }
    }

    private void calculateShotLogic(AERect frameSize) {
        Iterator<Shot> shots_iter = shots.iterator();
        while(shots_iter.hasNext()) {
            Shot shot = shots_iter.next();
            shot.move(getTicksPerSecond());
            if(!frameSize.intersects(shot.getBounds())) {
                if(shot.hitParticle(shot.getBounds(), null, particles))
                    shots_iter.remove();
                continue;
            }
            for(Player player: getAlivePlayers()) {
                if(player.getCurrentWeapon() instanceof CloseCombatWeapon && AnimationObject.collision(player.getCurrentWeapon(), shot)) {
                    player.getCurrentWeapon().intersectsWith(shot);
                }
                if(AnimationObject.collision(player, shot)) {
                    player.gotHit(shot);
                    player.stats.add_received_damage(shot.damage);
                    if(shot.shooter!=null)
                        shot.shooter.stats.add_dealt_damage(shot.damage);
                    if (player.getLifePs() <= 0) {
                        if(shot.shooter!=null)
                            shot.shooter.stats.add_kill();
                        calculateKilled(player);
                    }
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
    }

    private void moveAllNonPlayers(AERect frameSize) {
        Iterator<MapParticle> mrs_iter = mapParticles.iterator();
        while(mrs_iter.hasNext()) {
            MapParticle mr = mrs_iter.next();
            mr.move(getTicksPerSecond());
            if(!frameSize.intersects((mr.getBounds())))
                mrs_iter.remove();
            mr.computeBoxBounce(new AERect(getVirtualBoundaries()), 1, 1);//perfect bounce - no change in virtual energy
        }

        Iterator<LimitRangeMovingAnimationObject> particles_iter = particles.iterator();
        while(particles_iter.hasNext()) {
            LimitRangeMovingAnimationObject partcl = particles_iter.next();
            partcl.setF_Y(333);
            partcl.move(getTicksPerSecond());
            if(!AnimationObject.collision(partcl, partcl.explBounds))
                particles_iter.remove();
        }

        Iterator<CarePackage> packages_iter = packages.iterator();
        while(packages_iter.hasNext()) {
            CarePackage mp = packages_iter.next();
            mp.move(getTicksPerSecond());
            if(mp.isSolid)
                computeAppropiateFrameStop(frameSize, mp);
            mp.computeStops(mapParticles);
            if(!frameSize.intersects((mp.getBounds())))
                packages_iter.remove();
        }
    }

    private void calculateCarePackageSpawn() {
        if(System.nanoTime()/1e9-lastPackageSpawn>realisticGame.getPackageSpawnDelay()) {
            lastPackageSpawn=System.nanoTime()/1e9;
            realisticGame.doPackageSpawn(packages, this);
        }
    }

    private void calculateRoundOver() {
        ArrayList<Player> players = getAlivePlayers();

        if(players.isEmpty()) {
            initiateEverythingButPlayers();
            initiateNotInitiatedPlayers();
        } else if(players.size()==1) {
            if(winStreakData.wonAtTime !=0 && (System.nanoTime()- winStreakData.wonAtTime) / 1e9 > 4) {
                initiateEverythingButPlayers();
                initiateNotInitiatedPlayers();
            } else if(winStreakData.wonAtTime ==0) {
                winStreakData.wonAtTime =System.nanoTime();
                if(players.get(0).getPlayerColor().equals(winStreakData.streakColor)) {
                    winStreakData.streakLength++;
                } else {
                    winStreakData.streakLength=1;
                    winStreakData.streakColor=players.get(0).getPlayerColor();
                }
                players.get(0).stats.add_win();
                players.get(0).stats.calc_streak(winStreakData.streakLength);
            }
        }
    }

    private void calculateKilled(Player player) {
        if(player.getLifePs()<=0) {
            player.stats.add_death();
            LimitRangeMovingAnimationObject.startExplosion(particles, 55, player.getMidAsPoint(), new AESize(4,4),
                    new AnimationObject(player.getMidAsPoint().x-100,player.getMidAsPoint().y-100,200,200,AnimationObject.OVAL), 66, new AEColor(255,255,0,0).darker(), new AEColor(255,255,0,0), new AEColor(255,255,0,0).darker().darker());
        }
    }
}