package net.ninjadev.ninjautils.feature;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ninjadev.ninjautils.init.ModConfigs;

public abstract class Feature {

    protected boolean registered = false;

    public Feature(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract String getName();

    private boolean enabled;


    public boolean isEnabled() {
        return this.enabled;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void writeJson(JsonWriter writer) {

    }

    public <T extends Feature> T readJson(JsonReader reader) {
        return (T) this;
    }

    public void onTick() {

    }

    public void enable() {
        this.onEnable();
        ModConfigs.FEATURES.markDirty();
    }

    public void disable() {
        this.onDisable();
        ModConfigs.FEATURES.markDirty();
    }

}
