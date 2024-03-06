package net.ninjadev.ninjautils.init;

import net.ninjadev.ninjautils.feature.Feature;

public class ModFeatures {

    public static void init() {
        for (Feature feature : ModConfigs.FEATURES.features) {
            if (feature.isEnabled()) {
                feature.onEnable();
            }
        }
    }
}
