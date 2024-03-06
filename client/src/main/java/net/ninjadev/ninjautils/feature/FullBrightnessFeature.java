package net.ninjadev.ninjautils.feature;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.init.ModConfigs;

import java.util.Optional;

public class FullBrightnessFeature extends Feature {

    public static final String NAME = "fullbright";

    private double initialGamma;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        GameOptions options = MinecraftClient.getInstance().options;
        double gamma = options.getGamma().getValue();
        if (gamma < 0) gamma = 1.0d;
        this.initialGamma = gamma;
        double max = 15d;
        options.getGamma().setValue(max);
    }

    @Override
    public void onDisable() {
        GameOptions options = MinecraftClient.getInstance().options;
        options.getGamma().setValue(this.initialGamma);
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }
}
