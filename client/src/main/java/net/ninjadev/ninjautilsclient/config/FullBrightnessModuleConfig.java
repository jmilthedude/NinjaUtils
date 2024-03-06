package net.ninjadev.ninjautilsclient.config;


import com.google.gson.annotations.Expose;
import net.minecraft.client.MinecraftClient;
import net.ninjadev.ninjautilsclient.config.base.ModuleConfig;

public class FullBrightnessModuleConfig extends ModuleConfig {

    @Expose private double initialGamma;

    @Override
    public String getName() {
        return "full_bright";
    }

    @Override
    protected void reset() {
        this.initialGamma = MinecraftClient.getInstance().options.getGamma().getValue();
    }

    public double getInitialGamma() {
        return initialGamma;
    }

    public void setInitialGamma(double initialGamma) {
        this.initialGamma = initialGamma;
        this.save();
    }
}
