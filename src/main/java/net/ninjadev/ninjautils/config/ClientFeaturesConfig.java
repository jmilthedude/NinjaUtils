package net.ninjadev.ninjautils.config;

import net.ninjadev.ninjautils.feature.client.AntiFogFeature;
import net.ninjadev.ninjautils.feature.client.ClientInventorySortFeature;
import net.ninjadev.ninjautils.feature.client.FullBrightnessFeature;

public class ClientFeaturesConfig extends FeaturesConfig<ClientFeaturesConfig> {

    @Override
    public String getName() {
        return "client_features";
    }

    @Override
    protected void reset() {
        features.add(new AntiFogFeature().initEnabled());
        features.add(new FullBrightnessFeature().initEnabled());
        features.add(new ClientInventorySortFeature().initEnabled());
    }

    @Override
    public FeaturesConfig<ClientFeaturesConfig> get() {
        return new ClientFeaturesConfig();
    }

}
