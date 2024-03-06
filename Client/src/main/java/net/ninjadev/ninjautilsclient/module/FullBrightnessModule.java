package net.ninjadev.ninjautilsclient.module;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.ninjadev.ninjautilsclient.config.FullBrightnessModuleConfig;
import net.ninjadev.ninjautilsclient.init.ModConfigs;
import net.ninjadev.ninjautilsclient.module.base.AbstractModule;

public class FullBrightnessModule extends AbstractModule<FullBrightnessModuleConfig> {

    public FullBrightnessModule() {
        super(ModConfigs.FULL_BRIGHT);
    }

    @Override
    public String getName() {
        return "Fullbright";
    }

    @Override
    public void onActivate() {
        GameOptions options = MinecraftClient.getInstance().options;
        double gamma = options.getGamma().getValue();
        if (gamma < 0) gamma = 1.0d;
        this.getConfig().setInitialGamma(gamma);
        double max = 15d;
        options.getGamma().setValue(max);
    }

    @Override
    public void onDeactivate() {
        GameOptions options = MinecraftClient.getInstance().options;
        options.getGamma().setValue(this.getConfig().getInitialGamma());
    }
}
