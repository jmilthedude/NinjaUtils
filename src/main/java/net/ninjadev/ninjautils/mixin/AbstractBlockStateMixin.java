package net.ninjadev.ninjautils.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {

    @Inject(method = "isIn(Lnet/minecraft/registry/tag/TagKey;)Z", at = @At("RETURN"), cancellable = true)
    public void noTakeBlockEnderman(TagKey<Block> tag, CallbackInfoReturnable<Boolean> cir) {
        if (tag == BlockTags.ENDERMAN_HOLDABLE) cir.setReturnValue(false);
    }
}
