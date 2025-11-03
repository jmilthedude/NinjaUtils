package net.ninjadev.ninjautils.feature;

import net.ninjadev.ninjautils.config.FeaturesConfig;
import net.ninjadev.ninjautils.init.server.ServerConfigs;

import java.util.Optional;

public class NameColorFeature extends Feature {
    public static final String NAME = "name_color";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ServerConfigs.FEATURES);
    }
}
