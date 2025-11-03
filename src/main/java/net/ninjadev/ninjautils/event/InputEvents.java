package net.ninjadev.ninjautils.event;

import net.ninjadev.ninjautils.feature.AntiFogFeature;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.feature.FullBrightnessFeature;
import net.ninjadev.ninjautils.init.client.ClientConfigs;
import net.ninjadev.ninjautils.init.client.ModKeybinds;

public class InputEvents {

    public static void handleInput() {
        if (ModKeybinds.toggleFogKey.wasPressed()) {
            Feature feature = ClientConfigs.FEATURES.getFeature(AntiFogFeature.NAME);
            feature.setEnabled(!feature.isEnabled());
        }
        if (ModKeybinds.toggleFullbrightKey.wasPressed()) {
            Feature feature = ClientConfigs.FEATURES.getFeature(FullBrightnessFeature.NAME);
            feature.setEnabled(!feature.isEnabled());
        }
    }
}
