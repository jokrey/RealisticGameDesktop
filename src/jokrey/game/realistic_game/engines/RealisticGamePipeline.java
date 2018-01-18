package jokrey.game.realistic_game.engines;

import jokrey.utilities.animation.engine.AnimationEngine;
import jokrey.utilities.animation.pipeline.AnimationDrawer;
import jokrey.utilities.animation.pipeline.AnimationPipeline;
import jokrey.utilities.animation.util.AERect;

public class RealisticGamePipeline extends AnimationPipeline {
    public RealisticGamePipeline(AnimationDrawer drawer) {
        super(drawer);
    }

    @Override protected void drawForeground(AERect drawBounds, AnimationEngine engine) {
        Realistic_Game_Engine.WinStreakDisplay winStreakDisplay = ((Realistic_Game_Engine)engine).winStrDisplay;
        getDrawer().drawString(winStreakDisplay.streakColor, 55, winStreakDisplay.streakLength+"", drawBounds.x+drawBounds.getWidth()/2, drawBounds.y+drawBounds.getHeight()/8);
    }
}
