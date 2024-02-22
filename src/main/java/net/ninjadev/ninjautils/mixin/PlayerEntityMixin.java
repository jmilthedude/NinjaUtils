package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.ninjadev.ninjautils.event.impl.PlayerEntityCollisionEvent;
import net.ninjadev.ninjautils.init.ModEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList()Ljava/util/ArrayList;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    public void preEntityCollision(CallbackInfo ci, float f, Box box, List<Entity> list) {
        ModEvents.PLAYER_ENTITY_COLLISION.invoke(new PlayerEntityCollisionEvent.Data((PlayerEntity)(Object)this, list));
    }
}
