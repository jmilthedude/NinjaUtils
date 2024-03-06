package net.ninjadev.ninjautils.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.ninjadev.ninjautils.init.ModEvents;

import java.util.List;
import java.util.Optional;

public class RandomFlowersFeature extends Feature {

    public static final String NAME = "random_flowers";
    private static final List<BlockState> flowers = List.of(Blocks.DANDELION.getDefaultState(),
            Blocks.POPPY.getDefaultState(),
            Blocks.ALLIUM.getDefaultState(),
            Blocks.AZURE_BLUET.getDefaultState(),
            Blocks.RED_TULIP.getDefaultState(),
            Blocks.ORANGE_TULIP.getDefaultState(),
            Blocks.WHITE_TULIP.getDefaultState(),
            Blocks.PINK_TULIP.getDefaultState(),
            Blocks.OXEYE_DAISY.getDefaultState(),
            Blocks.CORNFLOWER.getDefaultState(),
            Blocks.LILY_OF_THE_VALLEY.getDefaultState()
    );

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        ModEvents.FLOWER_GENERATION.register(this, data -> {
            StructureWorldAccess world = data.getWorld();
            BlockPos pos = data.getPos();
            Optional<RegistryKey<Biome>> key = world.getBiome(pos).getKey();
            if (key.isEmpty() || !BiomeKeys.FLOWER_FOREST.getValue().equals(key.get().getValue())) return;

            if(!data.getState().isIn(BlockTags.SMALL_FLOWERS)) return;
            data.setState(flowers.get(world.getRandom().nextInt(flowers.size())));
        });

    }

    @Override
    public void onDisable() {
        ModEvents.FLOWER_GENERATION.release(this);
    }
}
