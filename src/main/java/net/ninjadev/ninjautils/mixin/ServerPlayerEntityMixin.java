package net.ninjadev.ninjautils.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.data.NameColorState;
import net.ninjadev.ninjautils.feature.NameColorFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "getPlayerListName", at = @At("RETURN"), cancellable = true)
    public void onGetPlayerListName(CallbackInfoReturnable<Text> cir) {
        if (!ModConfigs.FEATURES.isEnabled(NameColorFeature.NAME)) return;
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        Color color = NameColorState.get().getPlayerColor(player);
        Text name = Text.literal(player.getNameForScoreboard()).withColor(color.getRGB());
        cir.setReturnValue(name);
    }
}
