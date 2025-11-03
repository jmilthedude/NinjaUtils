package net.ninjadev.ninjautils.config;

import net.ninjadev.ninjautils.feature.*;

import java.util.List;

public class ServerFeaturesConfig extends FeaturesConfig<ServerFeaturesConfig> {

    @Override
    public String getName() {
        return "server_features";
    }

    @Override
    protected void reset() {
        this.addFeature(new NameColorFeature().initEnabled());
        this.addFeature(new NetherPortalCalcFeature().initEnabled());
        this.addFeature(new ServerInventorySortFeature().initEnabled());
        this.addFeature(new PlayerSleepFeature().initEnabled());
        this.addFeature(new FastXPFeature().initEnabled());
        this.addFeature(new DeathPointFeature(5).initEnabled());
        this.addFeature(new HarvestCropFeature().initEnabled());
        this.addFeature(new InventorySaveFeature().initEnabled());
        this.addFeature(new ShulkerDropsTwoFeature().initEnabled());
        this.addFeature(new SpawnDragonEggFeature().initEnabled());
        this.addFeature(new PeacefulPlayerFeature(List.of("player1", "player2")).initEnabled());
        this.addFeature(new DimensionSymbolFeature().initEnabled());
        this.addFeature(new RandomFlowersFeature().initEnabled());
        this.addFeature(new BeaconVisualizerFeature().initEnabled());
        this.addFeature(new NetherPortalCooldownFeature().initEnabled());
    }



    @Override
    public FeaturesConfig<ServerFeaturesConfig> get() {
        return new ServerFeaturesConfig();
    }
}
