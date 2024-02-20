package net.ninjadev.ninjautils.init;

import com.google.gson.stream.JsonReader;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.feature.InventorySortFeature;
import net.ninjadev.ninjautils.feature.NameColorFeature;
import net.ninjadev.ninjautils.feature.NetherPortalCalcFeature;

import java.util.Optional;

public class ModFeatures {

    public static void init() {
        for (Feature feature : ModConfigs.FEATURES.features) {
            if (feature.isEnabled()) {
                feature.onEnable();
            }
        }
    }

    public static <T extends Feature> Optional<T> create(String name, boolean enabled, JsonReader in) {
        switch (name) {
            case NameColorFeature.NAME -> {
                return Optional.of(new NameColorFeature(enabled).readJson(in));
            }
            case NetherPortalCalcFeature.NAME -> {
                return Optional.of(new NetherPortalCalcFeature(enabled).readJson(in));
            }
            case InventorySortFeature.NAME -> {
                return Optional.of(new InventorySortFeature(enabled).readJson(in));
            }
        }
        return Optional.empty();
    }
}
