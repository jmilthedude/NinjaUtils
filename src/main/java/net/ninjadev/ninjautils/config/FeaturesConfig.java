package net.ninjadev.ninjautils.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.feature.Feature;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class FeaturesConfig<C extends FeaturesConfig<?>> extends Config<C> implements Supplier<FeaturesConfig<C>> {

    @Expose
    public Map<String, Feature> features = new HashMap<>();

    public boolean isEnabled(String name) {
        return this.features.containsKey(name) && this.features.get(name).isEnabled();
    }

    public <T extends Feature> T getFeature(String name) {
        return (T) this.features.get(name);
    }

    @Override
    protected C validate(C config) {
        FeaturesConfig<C> fresh = this.get();
        fresh.reset();
        for (Feature feature : fresh.features.values()) {
            if (!config.hasFeature(feature)) {
                config.addFeature(feature);
            }
        }
        return config;
    }

    public void addFeature(Feature feature) {
        this.features.put(feature.getName(), feature);
        this.markDirty();
    }

    public boolean hasFeature(Feature feature) {
        return this.features.containsKey(feature.getName());
    }
}
