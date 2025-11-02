package net.ninjadev.ninjautils.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.ninjadev.ninjautils.client.NinjaUtilsClient;
import net.ninjadev.ninjautils.feature.client.ClientInventorySortFeature;
import net.ninjadev.ninjautils.init.client.ClientConfigs;
import net.ninjadev.ninjautils.init.client.ModKeybinds;
import net.ninjadev.ninjautils.network.C2SSortInventoryPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(KeyInput input, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().currentScreen == null) return;
        if (!NinjaUtilsClient.IS_SERVER_INSTALLED) return;
        ClientInventorySortFeature feature = ClientConfigs.FEATURES.getFeature(ClientInventorySortFeature.NAME);
        if (!feature.isEnabled() || !feature.useKeybind()) return;
        if (ModKeybinds.sortInventory.matchesKey(input)) {
            cir.setReturnValue(true);
            ClientPlayNetworking.send(new C2SSortInventoryPacket(input.getKeycode()));
        }
    }
}
