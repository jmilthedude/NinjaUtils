package net.ninjadev.ninjautils.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.SimpleBlockFeature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ninjadev.ninjautils.event.impl.FlowerGenerationEvent;
import net.ninjadev.ninjautils.init.ModEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SimpleBlockFeature.class)
public class SimpleBlockFeatureMixin {
    @Inject(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void onFlowerGenerated(FeatureContext<SimpleBlockFeatureConfig> context, CallbackInfoReturnable<Boolean> cir, SimpleBlockFeatureConfig config, StructureWorldAccess world, BlockPos pos, BlockState state) {
        FlowerGenerationEvent.Data data = ModEvents.FLOWER_GENERATION.invoke(new FlowerGenerationEvent.Data(world, pos, state));

        if (data.isStateChanged()) {
            cir.cancel();
            cir.setReturnValue(true);
            world.setBlockState(pos, data.getState(), Block.NOTIFY_LISTENERS);
        }

    }
}
