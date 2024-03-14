package net.ninjadev.ninjautils.config;

import net.ninjadev.ninjautils.feature.AntiFogFeature;
import net.ninjadev.ninjautils.feature.FullBrightnessFeature;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.feature.InventorySortFeature;

public class ClientFeaturesConfig extends FeaturesConfig<ClientFeaturesConfig> {

    @Override
    protected void reset() {
        features.add(new AntiFogFeature().initEnabled());
        features.add(new FullBrightnessFeature().initEnabled());
        features.add(new InventorySortFeature().initEnabled());
    }

    @Override
    public FeaturesConfig<ClientFeaturesConfig> get() {
        return new ClientFeaturesConfig();
    }

}
