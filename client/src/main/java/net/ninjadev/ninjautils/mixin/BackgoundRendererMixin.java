package net.ninjadev.ninjautils.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.ninjadev.ninjautils.feature.AntiFogFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
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
        if (ModConfigs.FEATURES.isEnabled(AntiFogFeature.NAME)) {
            float modifiedStart = -8.0f;
            float modifiedEnd = 1000000f;
            RenderSystem.setShaderFogStart(modifiedStart);
            RenderSystem.setShaderFogEnd(modifiedEnd);
        }
    }
}
