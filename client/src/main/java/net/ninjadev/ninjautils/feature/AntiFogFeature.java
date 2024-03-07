package net.ninjadev.ninjautils.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.common.feature.FeedbackFeature;
import net.ninjadev.ninjautils.init.ModConfigs;

import java.util.Optional;

public class AntiFogFeature extends Feature implements FeedbackFeature {

    public static final String NAME = "anti_fog";

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
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }

    @Override
    public void sendFeedback() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.sendMessage(Text.literal(String.format("%s%s%s", this.getName(), ": ", this.isEnabled())), true);
    }
}
