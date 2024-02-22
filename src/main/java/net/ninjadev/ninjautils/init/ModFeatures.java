package net.ninjadev.ninjautils.init;

import com.google.gson.stream.JsonReader;
import net.ninjadev.ninjautils.feature.*;

import java.io.IOException;
import java.util.Optional;

public class ModFeatures {

    public static void init() {
        for (Feature feature : ModConfigs.FEATURES.features) {
            if (feature.isEnabled()) {
                feature.onEnable();
            }
        }
    }

    public static <T extends Feature> Optional<T> create(String name, boolean enabled, JsonReader in) throws IOException {
        switch (name) {

        }
        return Optional.empty();
    }
}
