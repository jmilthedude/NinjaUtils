package net.ninjadev.ninjautils.feature;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ninjadev.ninjautils.init.ModConfigs;

public abstract class Feature {

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

    public abstract void writeJson(JsonWriter writer);
    public abstract <T extends Feature> T readJson(JsonReader reader);

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
