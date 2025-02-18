package net.ninjadev.ninjautils.mixin;

import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.ninjadev.ninjautils.init.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {

    @Inject(method = "getPortalDelay", at = @At("HEAD"), cancellable = true)
    public void getPortalDelay(ServerWorld world, Entity entity, CallbackInfoReturnable<Integer> cir) {
        if (!ModConfigs.FEATURES.isEnabled("nether_portal_cooldown")) {
            return;
        }
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }
        if (player.isSneaking()) {
            cir.setReturnValue(0);
        }
    }
}
