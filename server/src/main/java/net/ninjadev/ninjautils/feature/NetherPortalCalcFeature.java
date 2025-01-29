package net.ninjadev.ninjautils.feature;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.event.impl.BlockUseEvent;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModEvents;
import net.ninjadev.ninjautils.init.ModSetup;
import org.apache.commons.lang3.text.WordUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class NetherPortalCalcFeature extends Feature {

    public static final String NAME = "nether_portal_calc";

    private static final HashMap<UUID, AtomicLong> timers = new HashMap<>();
    private static final HashMap<UUID, Pair<World, BlockPos>> particles = new HashMap<>();

    @Override
    public String getName() {
        return "nether_portal_calc";
    }

    @Override
    public void onEnable() {
        ModEvents.BLOCK_USE.register(this, this::onClickedPortal);
    }

    @Override
    public void onDisable() {
        ModEvents.BLOCK_USE.release(this);
    }

    @Override
    public void onTick() {
        this.decrementTimers();
        this.spawnParticles();
    }

    private void decrementTimers() {
        List<UUID> forRemoval = new ArrayList<>();
        for (Map.Entry<UUID, AtomicLong> entry : timers.entrySet()) {
            if (entry.getValue().decrementAndGet() <= 0) {
                forRemoval.add(entry.getKey());
            }
        }
        clearAll(forRemoval);
    }

    private void clearAll(List<UUID> playerIds) {
        playerIds.forEach(uuid -> {
            timers.remove(uuid);
            Pair<World, BlockPos> removed = particles.remove(uuid);

            BlockPos pos = BlockPos.ORIGIN;
            if (removed != null) {
                pos = removed.getRight();
            }

            ServerPlayerEntity player = ModSetup.SERVER.getPlayerManager().getPlayer(uuid);
            if (player != null) {
                player.sendMessage(Text.literal(String.format("Particles despawned at: x%s, y%s, z%s", pos.getX(), pos.getY(), pos.getZ())));
            }
        });
    }

    private void spawnParticles() {
        for (Pair<World, BlockPos> value : particles.values()) {
            if (value.getLeft() instanceof ServerWorld world) {
                if (world.getTime() % 2 == 0) {
                    BlockPos pos = value.getRight();
                    boolean isChunkLoaded = world.isChunkLoaded(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()));
                    if (!isChunkLoaded) continue;
                    double x = pos.getX() + 0.5d;
                    double z = pos.getZ() + 0.5d;
                    for (int y = world.getBottomY(); y < world.getTopYInclusive(); y++) {
                        world.spawnParticles(ParticleTypes.PORTAL, x, y, z, 1, 0, Math.random(), 0, 0.005d);
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onClickedPortal(BlockUseEvent.Data data) {
        PlayerEntity player = data.getPlayer();
        if (data.getHand() == Hand.OFF_HAND) return;
        if (!player.isSneaking()) return;
        if (!player.getMainHandStack().isOf(Items.FLINT_AND_STEEL)) return;
        if (!data.getState().isOf(Blocks.OBSIDIAN)) return;

        data.setCancelled(true);

        World world = player.getWorld();
        BlockPos opposite = this.getOppositePosition(world, data.getHit().getBlockPos());
        RegistryKey<World> oppositeWorldKey = this.getOppositeWorldKey(world);
        World oppositeWorld = Objects.requireNonNull(player.getServer()).getWorld(oppositeWorldKey);
        if (oppositeWorld == null) return;

        timers.put(player.getUuid(), new AtomicLong(3600));
        particles.put(player.getUuid(), new Pair<>(oppositeWorld, opposite));
        String worldName = WordUtils.capitalizeFully(Objects.requireNonNull(oppositeWorldKey).getValue().getPath().replace("_", " "));
        player.sendMessage(Text.literal(String.format("%s Portal Position: x%s, y%s, z%s", worldName, opposite.getX(), opposite.getY(), opposite.getZ())), false);
    }

    private RegistryKey<World> getOppositeWorldKey(World world) {
        if (world.getRegistryKey() == World.OVERWORLD) {
            return World.NETHER;
        } else if (world.getRegistryKey() == World.NETHER) {
            return World.OVERWORLD;
        }
        return null;
    }

    private BlockPos getOppositePosition(World world, BlockPos current) {
        if (world == null) return BlockPos.ORIGIN;
        if (world.getRegistryKey() == World.OVERWORLD) {
            int x = Math.floorDiv(current.getX(), 8);
            int y = current.getY();
            int z = Math.floorDiv(current.getZ(), 8);
            return new BlockPos(x, y, z);
        } else if (world.getRegistryKey() == World.NETHER) {
            int x = current.getX() * 8;
            int y = current.getY();
            int z = current.getZ() * 8;
            return new BlockPos(x, y, z);
        }
        return BlockPos.ORIGIN;
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }
}
