package net.ninjadev.ninjautils.init.client;


import net.ninjadev.ninjautils.feature.Feature;

public class ClientFeatures {

    public static void init() {
        for (Feature feature : ClientConfigs.FEATURES.features) {
            if (feature.isEnabled()) {
                feature.onEnable();
            }
        }
    }
}
