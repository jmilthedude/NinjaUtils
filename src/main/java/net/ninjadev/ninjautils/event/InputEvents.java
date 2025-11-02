package net.ninjadev.ninjautils.event;

import net.ninjadev.ninjautils.feature.client.AntiFogFeature;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.feature.client.FullBrightnessFeature;
import net.ninjadev.ninjautils.init.client.ModKeybinds;
import net.ninjadev.ninjautils.init.server.ServerConfigs;

public class InputEvents {

    public static void handleInput() {
        if (ModKeybinds.toggleFogKey.wasPressed()) {
            Feature feature = ServerConfigs.FEATURES.getFeature(AntiFogFeature.NAME);
            feature.setEnabled(!feature.isEnabled());
        }
        if (ModKeybinds.toggleFullbrightKey.wasPressed()) {
            Feature feature = ServerConfigs.FEATURES.getFeature(FullBrightnessFeature.NAME);
            feature.setEnabled(!feature.isEnabled());
        }
    }
}
