package jokrey.game.realistic_game.engines;

import jokrey.game.realistic_game.Player;
import jokrey.utilities.animation.engine.AnimationEngine;
import jokrey.utilities.animation.pipeline.AnimationDrawer;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;

import java.text.DecimalFormat;
import java.util.Arrays;

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


        Player[] players = engine.getPlayers().toArray(new Player[0]);
        PlayerStats[] stats = new PlayerStats[players.length];
        for(int i=0;i<stats.length;i++)
            if (players[i]!=null)
                stats[i] = players[i].stats;

        Arrays.sort(stats);

        int draw_count = Math.min(8,stats.length);
        for(int i=0;i<draw_count;i++)
            getDrawer().drawString(stats[i].color, stats[i].toString(), new AERect(((double)(i)/draw_count)*drawBounds.w,0, drawBounds.w/draw_count, 30));
    }
}
