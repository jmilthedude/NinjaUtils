package net.ninjadev.ninjautilsclient.config;

import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautilsclient.feature.AntiFogFeature;
import net.ninjadev.ninjautilsclient.feature.FullBrightnessFeature;

public class ClientFeaturesConfig extends FeaturesConfig<ClientFeaturesConfig> {

    @Override
    protected void reset() {
        features.add(new AntiFogFeature().setEnabled(true));
        features.add(new FullBrightnessFeature().setEnabled(true));
    }

    @Override
    public FeaturesConfig<ClientFeaturesConfig> get() {
        return new ClientFeaturesConfig();
    }

}
