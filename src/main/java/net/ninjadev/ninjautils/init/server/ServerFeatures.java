package net.ninjadev.ninjautils.init.server;


import net.ninjadev.ninjautils.feature.Feature;

public class ServerFeatures {

    public static void init() {
        for (Feature feature : ServerConfigs.FEATURES.features) {
            if (feature.isEnabled()) {
                feature.onEnable();
            }
        }
    }
}
