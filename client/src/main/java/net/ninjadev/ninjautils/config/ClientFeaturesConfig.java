package net.ninjadev.ninjautils.config;

import net.ninjadev.ninjautils.feature.AntiFogFeature;
import net.ninjadev.ninjautils.feature.FullBrightnessFeature;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;

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
