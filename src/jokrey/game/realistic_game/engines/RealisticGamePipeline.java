package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.Player;
import jokrey.utilities.animation.engine.AnimationEngine;
import jokrey.utilities.animation.pipeline.AnimationDrawer;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;

import java.text.DecimalFormat;

public class RealisticGamePipeline extends AnimationPipeline {
    public RealisticGamePipeline(AnimationDrawer drawer) {
        super(drawer);
    }

    @Override protected void drawForeground(AERect drawBounds, AnimationEngine engine_raw) {
        Realistic_Game_Engine engine = ((Realistic_Game_Engine)engine_raw);
        Realistic_Game_Engine.WinStreakData winStreakDisplay = engine.winStreakData;
        if(winStreakDisplay.streakColor!=null)
            getDrawer().drawString(winStreakDisplay.streakColor, 44, winStreakDisplay.streakLength+"", drawBounds.x+drawBounds.getWidth()/2, drawBounds.y+drawBounds.getHeight()/8);

        if(winStreakDisplay.wonAtTime!=0) {
            double time_until_next_round = 4 - (System.nanoTime()-winStreakDisplay.wonAtTime)/1e9;
            getDrawer().drawString(winStreakDisplay.streakColor, 77, new DecimalFormat("0.00").format(time_until_next_round), drawBounds.x+drawBounds.getWidth()/2, drawBounds.y+drawBounds.getHeight()/2);
        }

        Player[] display = engine.getPlayers().toArray(new Player[0]);
        for(int i=0;i<display.length;i++) {
            if (display[i]!=null)
                getDrawer().drawString(display[i].getPlayerColor(), display[i].stats.toString(), new AERect(((double)(i)/display.length)*drawBounds.w,0, drawBounds.w/display.length, 30));
        }
    }
}
