package net.ninjadev.ninjautilsclient.module.base;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.ninjadev.ninjautilsclient.config.base.ModuleConfig;

public abstract class AbstractModule<C extends ModuleConfig> {

    private final C config;

    public AbstractModule(C config) {
        this.config = config;
    }

    public abstract String getName();

    public C getConfig() {
        return this.config;
    }

    public boolean isEnabled() {
        return this.getConfig().isEnabled();
    }

    private void setEnabled(boolean enabled) {
        this.getConfig().setEnabled(enabled);
        this.sendFeedback();
    }

    public void toggle() {
        this.setEnabled(!this.getConfig().isEnabled());
        if(this.isEnabled()) {
            this.onActivate();
        } else {
            this.onDeactivate();
        }
    }

    public void onActivate() {}
    public void onDeactivate() {}

    public void sendFeedback() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) return;
        player.sendMessage(Text.literal(String.format("%s%s%s", this.getName(), ": ", this.isEnabled())), true);
    }
}
