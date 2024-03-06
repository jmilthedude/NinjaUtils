package net.ninjadev.ninjautils.init;

import net.ninjadev.ninjautils.config.FeaturesConfig;

public class ModConfigs {

    public static FeaturesConfig FEATURES;

    public static void init() {
        FEATURES = new FeaturesConfig().readConfig();
    }

    public static void saveAll() {
        FEATURES.save();
    }
}
