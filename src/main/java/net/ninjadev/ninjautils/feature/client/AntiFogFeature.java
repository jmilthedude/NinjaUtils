package net.ninjadev.ninjautils.feature.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.config.FeaturesConfig;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.feature.FeedbackFeature;
import net.ninjadev.ninjautils.init.client.ClientConfigs;

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
    public boolean setEnabledByDefault() {
        return false;
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ClientConfigs.FEATURES);
    }

    @Override
    public void sendFeedback() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.sendMessage(Text.literal(String.format("%s%s%s", this.getName(), ": ", this.isEnabled())), true);
    }
}
