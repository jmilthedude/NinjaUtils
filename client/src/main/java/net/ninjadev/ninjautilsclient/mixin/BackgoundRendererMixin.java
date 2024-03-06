package net.ninjadev.ninjautilsclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.ninjadev.ninjautilsclient.init.ModModules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgoundRendererMixin {

    @Inject(method = "applyFog",
            at = @At("RETURN")
    )
    private static void removeFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {

        float modifiedStart = -8.0f;
        float modifiedEnd = 1000000f;

        if (ModModules.ANTI_FOG.isEnabled()) {
            RenderSystem.setShaderFogStart(modifiedStart);
            RenderSystem.setShaderFogEnd(modifiedEnd);
        }
    }
}
