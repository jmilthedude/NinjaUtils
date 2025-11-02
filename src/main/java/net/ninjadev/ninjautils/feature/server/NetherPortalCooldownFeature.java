package net.ninjadev.ninjautils.feature.server;

import net.ninjadev.ninjautils.config.FeaturesConfig;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.init.server.ServerConfigs;

import java.util.Optional;

public class NetherPortalCooldownFeature extends Feature {

    public static final String NAME = "nether_portal_cooldown";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        // No-op
    }

    @Override
    public void onDisable() {
        // No-op
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ServerConfigs.FEATURES);
    }
}
