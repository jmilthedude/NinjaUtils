package net.ninjadev.ninjautils.common.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.common.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class FeaturesConfig<C extends FeaturesConfig<?>> extends Config<C> implements Supplier<FeaturesConfig<C>> {

    @Expose
    public List<Feature> features = new ArrayList<>();

    @Override
    public String getName() {
        return "features";
    }

    public boolean isEnabled(String name) {
        for (Feature feature : this.features) {
            if (feature.getName().equalsIgnoreCase(name) && feature.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    public <T extends Feature> T getFeature(String name) {
        return (T) features.stream().filter(feature -> feature.getName().equalsIgnoreCase(name)).findFirst().orElseThrow();
    }

    @Override
    protected C validate(C config) {
        FeaturesConfig<C> fresh = this.get();
        fresh.reset();
        for (Feature feature : fresh.features) {
            if (!config.hasFeature(feature)) {
                config.addFeature(feature);
            }
        }
        return config;
    }

    public void addFeature(Feature feature) {
        this.features.add(feature);
        this.markDirty();
    }

    public boolean hasFeature(Feature feature) {
        return this.features.stream().anyMatch(other -> feature.getName().equalsIgnoreCase(other.getName()));
    }
}
