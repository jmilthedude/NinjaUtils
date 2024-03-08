package net.ninjadev.ninjautils.feature;


import com.google.gson.annotations.Expose;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.common.feature.FeedbackFeature;
import net.ninjadev.ninjautils.init.ModConfigs;

import java.util.Optional;

public class FullBrightnessFeature extends Feature implements FeedbackFeature {

    public static final String NAME = "fullbright";

    @Expose private double initialGamma;

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

    @Override
    public void sendFeedback() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.sendMessage(Text.literal(String.format("%s%s%s", this.getName(), ": ", this.isEnabled())), true);
    }
}
