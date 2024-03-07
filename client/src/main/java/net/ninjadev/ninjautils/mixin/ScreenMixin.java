package net.ninjadev.ninjautils.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.ninjadev.ninjautils.common.network.C2SSortInventoryPacket;
import net.ninjadev.ninjautils.init.ModKeybinds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        System.out.println(MinecraftClient.getInstance().currentScreen);
        if(MinecraftClient.getInstance().currentScreen == null) return;
        if(ModKeybinds.sortInventory.matchesKey(keyCode, scanCode)) {
            cir.setReturnValue(true);
            ClientPlayNetworking.send(new C2SSortInventoryPacket(keyCode));
        }
    }
}
