package net.ninjadev.ninjautils.feature;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class NameColorFeature extends Feature {
    public static final String NAME = "name_color";
    public NameColorFeature(boolean enabled) {
        super(enabled);
    }

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
    public void writeJson(JsonWriter writer) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Feature> T readJson(JsonReader reader) {
        return (T) this;
    }
}
