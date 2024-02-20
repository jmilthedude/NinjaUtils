package net.ninjadev.ninjautils.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.feature.InventorySortFeature;
import net.ninjadev.ninjautils.feature.NameColorFeature;
import net.ninjadev.ninjautils.feature.NetherPortalCalcFeature;

import java.util.ArrayList;
import java.util.List;

public class FeaturesConfig extends Config<FeaturesConfig> {

    @Expose
    public List<Feature> features = new ArrayList<>();

    @Override
    public String getName() {
        return "features";
    }

    @Override
    protected void reset() {
        features.add(new NameColorFeature(true));
        features.add(new NetherPortalCalcFeature(true));
        features.add(new InventorySortFeature(true));
    }

    public boolean isEnabled(String name) {
        for (Feature feature : this.features) {
            if (feature.getName().equalsIgnoreCase(name) && feature.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected FeaturesConfig validate(FeaturesConfig config) {
        FeaturesConfig fresh = new FeaturesConfig();
        fresh.reset();
        for (Feature feature : fresh.features) {
            if (!config.hasFeature(feature)) {
                config.addFeature(feature);
            }
        }
        return config;
    }

    private void addFeature(Feature feature) {
        this.features.add(feature);
        this.markDirty();
    }

    private boolean hasFeature(Feature feature) {
        return this.features.stream().anyMatch(other -> feature.getName().equalsIgnoreCase(other.getName()));
    }


}
