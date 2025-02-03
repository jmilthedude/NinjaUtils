package net.ninjadev.ninjautils.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BackgroundRenderer.class)
public class BackgoundRendererMixin {

//    @Inject(method = "applyFog",
//            at = @At("RETURN")
//    )
//    private static void removeFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
//        if (ModConfigs.FEATURES.isEnabled(AntiFogFeature.NAME)) {
//            float modifiedStart = -8.0f;
//            float modifiedEnd = 1000000f;
//            RenderSystem.setShaderFogStart(modifiedStart);
//            RenderSystem.setShaderFogEnd(modifiedEnd);
//        }
//    }
}
