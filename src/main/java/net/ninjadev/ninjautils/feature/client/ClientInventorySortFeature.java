package net.ninjadev.ninjautils.feature.client;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.config.FeaturesConfig;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.init.client.ClientConfigs;

import java.util.Optional;

public class ClientInventorySortFeature extends Feature {

    public static final String NAME = "inventory_sort";
    @Expose private boolean useKeybind = true;

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

    public boolean useKeybind() {
        return useKeybind;
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ClientConfigs.FEATURES);
    }
}
