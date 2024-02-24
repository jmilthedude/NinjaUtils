package net.ninjadev.ninjautils.feature;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.ninjadev.ninjautils.data.InventorySaveState;

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

            InventorySaveState.get().addInventory(player);
            return true;
        });
        this.registered = true;
    }

    @Override
    public void onDisable() {

    }
}
