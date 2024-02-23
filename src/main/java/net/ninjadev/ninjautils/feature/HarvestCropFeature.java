package net.ninjadev.ninjautils.feature;

import com.google.gson.annotations.Expose;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.init.ModEvents;

import java.util.List;

public class HarvestCropFeature extends Feature {

    @Expose List<String> blockIds = List.of(
            Registries.BLOCK.getId(Blocks.WHEAT).toString(),
            Registries.BLOCK.getId(Blocks.CARROTS).toString(),
            Registries.BLOCK.getId(Blocks.POTATOES).toString(),
            Registries.BLOCK.getId(Blocks.BEETROOTS).toString()
    );

    @Override
    public String getName() {
        return "harvest_crop";
    }

    @Override
    public void onEnable() {
        ModEvents.BLOCK_USE.register(this, data -> {
            Block block = data.getState().getBlock();
            if (!blockIds.contains(Registries.BLOCK.getId(block).toString())) return;
            if (!(block instanceof CropBlock cropBlock)) return;
            if (cropBlock.getAge(data.getState()) < cropBlock.getMaxAge()) return;

            this.harvestBlock(data.getPlayer(), data.getPos(), cropBlock);
        });

    }

    private void harvestBlock(PlayerEntity player, BlockPos pos, CropBlock block) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (!serverPlayer.getMainHandStack().isIn(ItemTags.HOES)) return;
            if (serverPlayer.interactionManager.tryBreakBlock(pos)) {
                serverPlayer.swingHand(Hand.MAIN_HAND, true);
                World world = player.getWorld();
                world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundGroup(world.getBlockState(pos)).getBreakSound(), SoundCategory.BLOCKS, 0.75f, 1.0f);
                world.setBlockState(pos, block.withAge(0));
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
