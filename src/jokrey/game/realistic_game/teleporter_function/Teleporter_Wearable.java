package jokrey.game.realistic_game.teleporter_function;

import jokrey.game.realistic_game.Wearable;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;

public class Teleporter_Wearable extends Wearable {
    @Override public String getName() {
        return "TELEPORTER";
    }
    @Override public boolean newWearableAddedRemoveThis(Wearable newWear){
        return newWear.getName().equals("TELEPORTER");
    }

    public static final double DELAY = 0.5;
    double lastBoost = System.nanoTime()/1e9;
    @Override public boolean upAction() {
        if(System.nanoTime()/1e9-lastBoost>DELAY && getWearer().getCurrentWeapon()==null) {//delay == 4
            lastBoost = System.nanoTime()/1e9;
            getWearer().setY(getWearer().getY()-222);
            getWearer().setV_Y(0);
            return true;
        }
        return false;
    }
    @Override public boolean attackAction() {
        if(System.nanoTime()/1e9-lastBoost>DELAY && getWearer().getCurrentWeapon()==null) {//delay == 4
            lastBoost = System.nanoTime()/1e9;
            getWearer().setY(getWearer().getY()+222);
            getWearer().setV_Y(0);
            return true;
        }
        return false;
    }
    @Override public boolean leftAction() {
        if(System.nanoTime()/1e9-lastBoost>DELAY && getWearer().getCurrentWeapon()==null) {//delay == 4
            lastBoost = System.nanoTime()/1e9;
            getWearer().setX(getWearer().getX()-222);
            getWearer().setV_Y(0);
            return true;
        }
        return false;
    }
    @Override public boolean rightAction() {
        if(System.nanoTime()/1e9-lastBoost>DELAY && getWearer().getCurrentWeapon()==null) {//delay == 4
            lastBoost = System.nanoTime()/1e9;
            getWearer().setX(getWearer().getX()+222);
            getWearer().setV_Y(0);
            return true;
        }
        return false;
    }

    @Override public boolean imDeadPleaseKillMe() {return false;}
    @Override public void drawWearable(AnimationPipeline pipe, double x, double y, double w, double h) {
        int act_size = (int) (h/10);
        pipe.getDrawer().fillOval(System.nanoTime()/1e9-lastBoost>DELAY?AEColor.CYAN:AEColor.RED, new AERect(getWearer()==null?x:x+w/2-act_size/2, getWearer()==null?y:y+h/2-act_size/2, act_size, act_size));
    }
}
