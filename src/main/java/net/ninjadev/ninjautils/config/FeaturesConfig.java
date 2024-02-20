package net.ninjadev.ninjautils.config;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.feature.NameColorFeature;
import net.ninjadev.ninjautils.feature.NetherPortalCalcFeature;

import java.util.ArrayList;
import java.util.List;

public class FeaturesConfig extends Config {

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
    }

    public boolean isEnabled(String name) {
        for (Feature feature : this.features) {
            if(feature.getName().equalsIgnoreCase(name) && feature.isEnabled()) {
                return true;
            }
        }
        return false;
    }
}
