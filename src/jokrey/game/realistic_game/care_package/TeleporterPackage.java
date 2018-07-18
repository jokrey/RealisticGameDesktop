package jokrey.game.realistic_game.care_package;

import jokrey.game.realistic_game.teleporter_function.Teleporter_Wearable;
import jokrey.utilities.animation.util.AESize;

public class TeleporterPackage extends WearablePackage {
    public TeleporterPackage(AESize frameSize) {
        super(new Teleporter_Wearable(), frameSize);
    }
}
