package net.ninjadev.ninjautils.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.ninjadev.ninjautils.common.network.C2SSortInventoryPacket;
import net.ninjadev.ninjautils.feature.InventorySortFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModKeybinds;
import net.ninjadev.ninjautils.init.ModNetwork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().currentScreen == null) return;
        if (!ModNetwork.isServerInstalled()) return;
        InventorySortFeature feature = ModConfigs.FEATURES.getFeature(InventorySortFeature.NAME);
        if (!feature.isEnabled() || !feature.useKeybind()) return;
        if (ModKeybinds.sortInventory.matchesKey(keyCode, scanCode)) {
            cir.setReturnValue(true);
            ClientPlayNetworking.send(new C2SSortInventoryPacket(keyCode));
        }
    }
}
