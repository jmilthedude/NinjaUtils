package net.ninjadev.ninjautils.config;

import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.feature.*;

import java.util.List;

public class ServerFeaturesConfig extends FeaturesConfig<ServerFeaturesConfig> {

    @Override
    protected void reset() {
        features.add(new NameColorFeature().setEnabled(true));
        features.add(new NetherPortalCalcFeature().setEnabled(true));
        features.add(new InventorySortFeature().setEnabled(true));
        features.add(new PlayerSleepFeature().setEnabled(true));
        features.add(new FastXPFeature().setEnabled(true));
        features.add(new DeathPointFeature(5).setEnabled(true));
        features.add(new HarvestCropFeature().setEnabled(true));
        features.add(new InventorySaveFeature().setEnabled(true));
        features.add(new ShulkerDropsTwoFeature().setEnabled(true));
        features.add(new SpawnDragonEggFeature().setEnabled(true));
        features.add(new PeacefulPlayerFeature(List.of("jmilthedude", "player2")).setEnabled(true));
        features.add(new DimensionSymbolFeature().setEnabled(true));
        features.add(new RandomFlowersFeature().setEnabled(true));
    }


    @Override
    public FeaturesConfig<ServerFeaturesConfig> get() {
        return new ServerFeaturesConfig();
    }
}
