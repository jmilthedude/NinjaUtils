package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {


    @Inject(method = "tickPortal", at = @At("HEAD"))
    public void getPortalTime(CallbackInfo ci) {
        Entity e = (Entity)(Object)this;
        if(!(e instanceof PlayerEntity player)) return;
        if(player.isSneaking()) ((EntityAccessor)e).setNetherPortalTime(player.getMaxNetherPortalTime() + 1);
    }
}
