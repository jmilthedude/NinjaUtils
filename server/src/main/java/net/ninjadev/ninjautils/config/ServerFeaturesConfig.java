package net.ninjadev.ninjautils.config;

import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.feature.*;

import java.util.List;

public class ServerFeaturesConfig extends FeaturesConfig<ServerFeaturesConfig> {

    @Override
    protected void reset() {
        features.add(new NameColorFeature().initEnabled());
        features.add(new NetherPortalCalcFeature().initEnabled());
        features.add(new InventorySortFeature().initEnabled());
        features.add(new PlayerSleepFeature().initEnabled());
        features.add(new FastXPFeature().initEnabled());
        features.add(new DeathPointFeature(5).initEnabled());
        features.add(new HarvestCropFeature().initEnabled());
        features.add(new InventorySaveFeature().initEnabled());
        features.add(new ShulkerDropsTwoFeature().initEnabled());
        features.add(new SpawnDragonEggFeature().initEnabled());
        features.add(new PeacefulPlayerFeature(List.of("jmilthedude", "player2")).initEnabled());
        features.add(new DimensionSymbolFeature().initEnabled());
        features.add(new RandomFlowersFeature().initEnabled());
        features.add(new BeaconVisualizerFeature().initEnabled());
    }


    @Override
    public FeaturesConfig<ServerFeaturesConfig> get() {
        return new ServerFeaturesConfig();
    }
}
