package net.ninjadev.ninjautils.init;

import net.ninjadev.ninjautils.config.ServerFeaturesConfig;

public class ModConfigs {

    public static ServerFeaturesConfig FEATURES;

    public static void init() {
        FEATURES = new ServerFeaturesConfig().readConfig();
    }

    public static void saveAll() {
        FEATURES.save();
    }
}
