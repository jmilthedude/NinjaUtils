package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.ninjadev.ninjautils.data.NameColorState;
import net.ninjadev.ninjautils.event.impl.PlayerEntityCollisionEvent;
import net.ninjadev.ninjautils.feature.NameColorFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList()Ljava/util/ArrayList;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void preEntityCollision(CallbackInfo ci, float f, Box box, List<Entity> list) {
        ModEvents.PLAYER_ENTITY_COLLISION.invoke(new PlayerEntityCollisionEvent.Data((PlayerEntity) (Object) this, list));
    }

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    public void onGetDisplayName(CallbackInfoReturnable<Text> cir) {
        if (!ModConfigs.FEATURES.isEnabled(NameColorFeature.NAME)) return;
        PlayerEntity player = (PlayerEntity) (Object) this;
        Color color = NameColorState.get().getPlayerColor(player.getUuid());
        Text name = Text.literal(player.getNameForScoreboard()).withColor(color.getRGB());

        cir.cancel();
        cir.setReturnValue(name);

    }

}
