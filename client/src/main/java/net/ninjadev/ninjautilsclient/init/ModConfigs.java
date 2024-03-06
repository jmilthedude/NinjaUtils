package net.ninjadev.ninjautilsclient.init;

import net.ninjadev.ninjautilsclient.config.ClientFeaturesConfig;

public class ModConfigs {

    public static ClientFeaturesConfig FEATURES;

    public static void register() {
        FEATURES = new ClientFeaturesConfig().readConfig();
    }
}
