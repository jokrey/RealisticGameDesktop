package realistic_game.engines;

import util.animation.engine.AnimationEngine;
import util.animation.pipeline.AnimationDrawer;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AERect;

public class RealisticGamePipeline extends AnimationPipeline {
    public RealisticGamePipeline(AnimationDrawer drawer) {
        super(drawer);
    }

    @Override protected void drawForeground(AERect drawBounds, AnimationEngine engine) {
        Realistic_Game_Engine.WinStreakDisplay winStreakDisplay = ((Realistic_Game_Engine)engine).winStrDisplay;
        getDrawer().drawString(winStreakDisplay.streakColor, 55, winStreakDisplay.streakLength+"", drawBounds.x+drawBounds.getWidth()/2, drawBounds.y+drawBounds.getHeight()/8);
    }
}
