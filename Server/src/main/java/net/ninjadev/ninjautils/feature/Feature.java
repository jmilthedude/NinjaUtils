package net.ninjadev.ninjautils.feature;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.init.ModConfigs;

public abstract class Feature {

    protected boolean registered = false;
    @Expose private boolean enabled;

    public abstract String getName();
    public void onTick() {}

    public void enable() {
        this.enabled = true;
        this.onEnable();
        ModConfigs.FEATURES.markDirty();
    }
    public abstract void onEnable();

    public void disable() {
        this.enabled = false;
        this.onDisable();
        ModConfigs.FEATURES.markDirty();
    }
    public abstract void onDisable();

    public <T extends Feature> T setEnabled(boolean enabled) {
        this.enabled = enabled;
        return (T) this;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

}
