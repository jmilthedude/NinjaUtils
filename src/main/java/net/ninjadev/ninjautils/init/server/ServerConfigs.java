package net.ninjadev.ninjautils.init.server;

import net.ninjadev.ninjautils.config.ServerFeaturesConfig;

public class ServerConfigs {

    public static ServerFeaturesConfig FEATURES;

    public static void init() {
        FEATURES = new ServerFeaturesConfig().readConfig();
    }

    public static void saveAll() {
        FEATURES.save();
    }
}
