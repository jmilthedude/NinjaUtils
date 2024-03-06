package net.ninjadev.ninjautils.feature;

import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.EndPortalFeature;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.init.ModConfigs;

import java.util.Optional;

public class SpawnDragonEggFeature extends Feature {

    public static final String NAME = "spawn_dragon_egg";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void onDragonKilled(EnderDragonEntity dragon, World world, BlockPos origin) {
        world.setBlockState(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.offsetOrigin(origin)), Blocks.DRAGON_EGG.getDefaultState());
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }
}
