package net.ninjadev.ninjautils.feature;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.event.impl.BlockUseEvent;
import net.ninjadev.ninjautils.init.ModEvents;

import java.util.List;

public class HarvestCropFeature extends Feature {

    private final List<String> blockIds = List.of(
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
            if (!this.canHarvest(data)) return;
            this.harvestBlock((ServerPlayerEntity) data.getPlayer(), data.getPos(), (CropBlock) data.getState().getBlock());
        });

    }

    private boolean canHarvest(BlockUseEvent.Data data) {
        if (!(data.getPlayer() instanceof ServerPlayerEntity player)) return false;
        if (!player.getMainHandStack().isIn(ItemTags.HOES)) return false;

        Block block = data.getState().getBlock();
        if (!blockIds.contains(Registries.BLOCK.getId(block).toString())) return false;

        if (!(block instanceof CropBlock cropBlock)) return false;
        return cropBlock.getAge(data.getState()) >= cropBlock.getMaxAge();
    }

    private void harvestBlock(ServerPlayerEntity player, BlockPos pos, CropBlock block) {
        if (player.interactionManager.tryBreakBlock(pos)) {
            this.postBreak(player, pos, block);
        }
    }

    private void postBreak(ServerPlayerEntity player, BlockPos pos, CropBlock block) {
        player.swingHand(Hand.MAIN_HAND, true);
        World world = player.getWorld();
        world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundGroup(world.getBlockState(pos)).getBreakSound(), SoundCategory.BLOCKS, 0.75f, 1.0f);
        world.setBlockState(pos, block.withAge(0));
        player.getMainHandStack().damage(1, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        this.decrementSeedItem(pos, world);
    }

    private void decrementSeedItem(BlockPos pos, World world) {
        List<Item> deductible = List.of(Items.WHEAT_SEEDS, Items.CARROT, Items.POTATO, Items.BEETROOT_SEEDS);
        List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class, new Box(pos).expand(2.0), entity -> {
            ItemStack stack = entity.getStack();
            return deductible.contains(stack.getItem());
        });
        for (ItemEntity item : items) {
            ItemStack stack = item.getStack();
            int count = stack.getCount();
            if (count > 0) {
                stack.setCount(count - 1);
                break;
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
