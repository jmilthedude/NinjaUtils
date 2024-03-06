package net.ninjadev.ninjautils.init;

import net.ninjadev.ninjautils.config.ClientFeaturesConfig;

public class ModConfigs {

    public static ClientFeaturesConfig FEATURES;

    public static void register() {
        FEATURES = new ClientFeaturesConfig().readConfig();
    }
}
