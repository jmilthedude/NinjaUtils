package net.ninjadev.ninjautils.config;

import net.ninjadev.ninjautils.feature.AntiFogFeature;
import net.ninjadev.ninjautils.feature.ClientInventorySortFeature;
import net.ninjadev.ninjautils.feature.FullBrightnessFeature;

public class ClientFeaturesConfig extends FeaturesConfig<ClientFeaturesConfig> {

    @Override
    public String getName() {
        return "client_features";
    }

    @Override
    protected void reset() {
        this.addFeature(new AntiFogFeature().initEnabled());
        this.addFeature(new FullBrightnessFeature().initEnabled());
        this.addFeature(new ClientInventorySortFeature().initEnabled());
    }

    @Override
    public FeaturesConfig<ClientFeaturesConfig> get() {
        return new ClientFeaturesConfig();
    }

}
