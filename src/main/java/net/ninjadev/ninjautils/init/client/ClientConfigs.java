package net.ninjadev.ninjautils.init.client;

import net.ninjadev.ninjautils.config.ClientFeaturesConfig;

public class ClientConfigs {

    public static ClientFeaturesConfig FEATURES;

    public static void register() {
        FEATURES = new ClientFeaturesConfig().readConfig();
    }
}
