package net.ninjadev.ninjautils.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.ninjadev.ninjautils.feature.DimensionSymbolFeature;
import net.ninjadev.ninjautils.init.server.ServerConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Inject(method = "addPlayer", at = @At("RETURN"))
    public void updatePlayerList(ServerPlayerEntity player, CallbackInfo ci) {
        if (!ServerConfigs.FEATURES.isEnabled(DimensionSymbolFeature.NAME)) return;
        DimensionSymbolFeature feature = ServerConfigs.FEATURES.getFeature(DimensionSymbolFeature.NAME);
        Identifier worldId = player.getEntityWorld().getRegistryKey().getValue();
        feature.updatePlayer(player, worldId);
    }
}
