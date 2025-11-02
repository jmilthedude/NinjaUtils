package net.ninjadev.ninjautils.feature.server;

import net.ninjadev.ninjautils.config.FeaturesConfig;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.init.server.ServerConfigs;

import java.util.Optional;

public class ShulkerDropsTwoFeature extends Feature {

    public static final String NAME = "shulker_drops_two";

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
