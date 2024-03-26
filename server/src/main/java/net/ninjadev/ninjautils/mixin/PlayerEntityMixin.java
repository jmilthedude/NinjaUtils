package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.ninjadev.ninjautils.event.impl.PlayerEntityCollisionEvent;
import net.ninjadev.ninjautils.feature.PeacefulPlayerFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModEvents;
import net.ninjadev.ninjautils.util.TextUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList()Ljava/util/ArrayList;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void preEntityCollision(CallbackInfo ci, float f, Box box, List<Entity> list) {
        ModEvents.PLAYER_ENTITY_COLLISION.invoke(new PlayerEntityCollisionEvent.Data((PlayerEntity) (Object) this, list));
    }

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    public void onGetDisplayName(CallbackInfoReturnable<Text> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player instanceof ServerPlayerEntity serverPlayer) {
            cir.cancel();
            cir.setReturnValue(TextUtils.getPlayerNameStyled(serverPlayer, false));
        }
    }

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    public void onDropInventory(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        PeacefulPlayerFeature feature = ModConfigs.FEATURES.getFeature(PeacefulPlayerFeature.NAME);
        if (feature.isEnabled() && feature.getPlayers().contains(player.getNameForScoreboard())) {
            ci.cancel();
        }
    }

}
