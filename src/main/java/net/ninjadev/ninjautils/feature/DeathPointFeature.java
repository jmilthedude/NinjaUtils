package net.ninjadev.ninjautils.feature;

import com.google.gson.annotations.Expose;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.data.DeathPointState;

public class DeathPointFeature extends Feature {

    public static final String NAME = "deathpoint";
    @Expose private int maxDeathpoints;

    public DeathPointFeature() {
    }

    public DeathPointFeature(int maxDeathpoints) {
        this.maxDeathpoints = maxDeathpoints;
    }

    public int getMaxDeathpoints() {
        return maxDeathpoints;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        if (this.registered) return;
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            BlockPos pos = player.getBlockPos();
            RegistryKey<World> key = player.getWorld().getRegistryKey();
            long timeStamp = System.currentTimeMillis();

            DeathPointState.Entry entry = new DeathPointState.Entry(pos, key.getValue(), timeStamp);
            DeathPointState.get().addEntry(player.getUuid(), entry);
            player.sendMessage(Text.literal("You died at: ").append(entry.getMessage(false)));
        });
        this.registered = true;
    }

    @Override
    public void onDisable() {

    }
}
