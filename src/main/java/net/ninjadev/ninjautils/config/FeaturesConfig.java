package net.ninjadev.ninjautils.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.feature.*;

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
        features.add(new NameColorFeature().setEnabled(true));
        features.add(new NetherPortalCalcFeature().setEnabled(true));
        features.add(new InventorySortFeature().setEnabled(true));
        features.add(new PlayerSleepFeature().setEnabled(true));
        features.add(new FastXPFeature().setEnabled(true));
        features.add(new DeathPointFeature(5).setEnabled(true));
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
