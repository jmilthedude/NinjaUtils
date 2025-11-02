package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.ninjadev.ninjautils.feature.server.SpawnDragonEggFeature;
import net.ninjadev.ninjautils.init.server.ServerConfigs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {
    @Shadow private boolean previouslyKilled;

    @Shadow
    @Final
    private ServerWorld world;

    @Shadow
    @Final
    private BlockPos origin;

    @Inject(method = "dragonKilled", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonFight;generateNewEndGateway()V", shift = At.Shift.AFTER))
    public void spawnDragonEgg(EnderDragonEntity dragon, CallbackInfo ci) {
        if (!ServerConfigs.FEATURES.isEnabled(SpawnDragonEggFeature.NAME)) return;
        if (this.previouslyKilled) {
            SpawnDragonEggFeature feature = ServerConfigs.FEATURES.getFeature(SpawnDragonEggFeature.NAME);
            feature.onDragonKilled(dragon, this.world, this.origin);
        }
    }
}
