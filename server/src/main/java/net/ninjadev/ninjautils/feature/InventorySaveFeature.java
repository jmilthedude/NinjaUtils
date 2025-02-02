package net.ninjadev.ninjautils.feature;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.data.InventorySaveState;
import net.ninjadev.ninjautils.init.ModConfigs;

import java.util.Optional;

public class InventorySaveFeature extends Feature {
    public static final String NAME = "inventory_save";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        if (this.registered) return;
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damage) -> {
            if (!this.isEnabled()) return true;
            if (!(entity instanceof ServerPlayerEntity player)) return true;
            SharedConstants.LOG.info("Saving inventory for dying player: {}", player.getName());
            InventorySaveState.get().addInventory(player);
            SharedConstants.LOG.info("Inventory saved.");
            return true;
        });
        this.registered = true;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }
}
