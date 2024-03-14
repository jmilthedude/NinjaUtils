package net.ninjadev.ninjautils.feature;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.event.impl.BlockUseEvent;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModEvents;
import net.ninjadev.ninjautils.init.ModSetup;
import net.ninjadev.ninjautils.util.TickTimer;

import java.util.*;

public class BeaconVisualizerFeature extends Feature {

    public static final String NAME = "beacon_visualizer";

    private static final HashMap<UUID, TickTimer> timers = new HashMap<>();
    private static final HashMap<UUID, VisualizerData> particles = new HashMap<>();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        ModEvents.BLOCK_USE.register(this, this::onClick);
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


    private void clearAll(List<UUID> playerIds) {
        playerIds.forEach(uuid -> {
            timers.remove(uuid);
            VisualizerData removed = particles.remove(uuid);

            BlockPos pos = BlockPos.ORIGIN;
            if (removed != null) {
                pos = removed.beaconPos;
            }

            ServerPlayerEntity player = ModSetup.SERVER.getPlayerManager().getPlayer(uuid);
            if (player != null) {
                player.sendMessage(Text.literal(String.format("Particles removed for BeaconPos[x=%s,y=%s,z=%s]", pos.getX(), pos.getY(), pos.getZ())),true);
            }
        });
    }

    private void decrementTimers() {
        List<UUID> forRemoval = timers.entrySet().stream().peek(entry -> entry.getValue().update()).filter(entry -> entry.getValue().isComplete()).map(Map.Entry::getKey).toList();
        this.clearAll(forRemoval);
    }

    private void spawnParticles() {
        for (Map.Entry<UUID, VisualizerData> value : particles.entrySet()) {
            ServerPlayerEntity player = ModSetup.SERVER.getPlayerManager().getPlayer(value.getKey());
            if (player == null) continue;
            VisualizerData data = value.getValue();
            BlockPos pos = data.beaconPos;
            if (data.world instanceof ServerWorld world) {
                boolean isChunkLoaded = world.isChunkLoaded(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()));
                if (!isChunkLoaded) continue;
                double beaconX = pos.getX() + 0.5d;
                double beaconY = pos.getY() + 0.5d;
                double beaconZ = pos.getZ() + 0.5d;

                Edge nextEdge = data.getNextEdge();

                for (BlockPos blockPos : nextEdge) {
                    int currentIndex = nextEdge.indexOf(blockPos);
                    for (int y = player.getBlockY() - 1; y <= player.getBlockY() + 2; y++) {
                        boolean isCornerPos = currentIndex == 0 || currentIndex == nextEdge.size() - 1;
                        world.spawnParticles(player, isCornerPos ? ParticleTypes.FLAME : ParticleTypes.SOUL_FIRE_FLAME, true, blockPos.getX() + 0.5d, y + 0.5d, blockPos.getZ() + 0.5d, 1, 0, .25, 0, 0);
                    }
                }
                world.spawnParticles(player, ParticleTypes.HAPPY_VILLAGER, true, beaconX, beaconY, beaconZ, 5, 0, .25, 0, 0);
            }
        }
    }

    private void onClick(BlockUseEvent.Data data) {
        PlayerEntity player = data.getPlayer();
        if (data.getHand() == Hand.OFF_HAND) return;
        if (!player.isSneaking()) return;
        if (!player.getMainHandStack().isOf(Items.BEACON) && !player.getMainHandStack().isOf(Items.NETHER_STAR)) return;

        data.setCancelled(true);

        World world = player.getWorld();
        BlockPos pos = data.getHit().getBlockPos();
        timers.put(player.getUuid(), new TickTimer(300));
        VisualizerData newData = particles.merge(player.getUuid(), new VisualizerData(pos, world, Level.ONE), (old, value) -> {
            if (!pos.equals(old.beaconPos)) return value;
            return new VisualizerData(old.beaconPos, old.world, old.level.getNext());
        });
        String text = "BeaconPos[x=%s,y=%s,z=%s], Level=%s, Range=%s".formatted(newData.beaconPos.getX(), newData.beaconPos.getY(), newData.beaconPos.getZ(), newData.level, newData.level.range);
        player.sendMessage(Text.literal(text), true);
    }


    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }

    enum Level {
        ONE(20),
        TWO(30),
        THREE(40),
        FOUR(50);

        final int range;

        Level(int range) {
            this.range = range;
        }

        public int getRange() {
            return range;
        }

        public Level getNext() {
            int ordinal = this.ordinal();
            if (ordinal + 1 >= Level.values().length) return Level.ONE;
            return Level.values()[ordinal + 1];
        }

    }

    private static class Edge extends ArrayList<BlockPos> {
    }

    private static class VisualizerData {
        private final BlockPos beaconPos;
        private final World world;
        private final Level level;
        private final List<Edge> edges;

        private int currentEdge = 0;

        public VisualizerData(BlockPos beaconPos, World world, Level level) {
            this.beaconPos = beaconPos;
            this.world = world;
            this.level = level;
            this.edges = this.calculateEdges(this.beaconPos, this.level.range);
        }

        public Edge getNextEdge() {
            this.currentEdge++;
            if (this.currentEdge >= this.edges.size()) {
                this.currentEdge = 0;
            }
            return this.edges.get(this.currentEdge);
        }

        private List<Edge> calculateEdges(BlockPos beaconPos, int distance) {
            List<Edge> edges = new ArrayList<>();
            BlockPos northWest = beaconPos.add(-distance, 0, -distance);
            BlockPos northEast = beaconPos.add(distance, 0, -distance);
            BlockPos southEast = beaconPos.add(distance, 0, distance);
            BlockPos southWest = beaconPos.add(-distance, 0, distance);
            edges.add(this.calculateEdge(northWest, northEast));
            edges.add(this.calculateEdge(northEast, southEast));
            edges.add(this.calculateEdge(southEast, southWest));
            edges.add(this.calculateEdge(southWest, northWest));
            return edges;
        }

        private Edge calculateEdge(BlockPos cornerA, BlockPos cornerB) {
            Edge edge = new Edge();
            int dx = cornerB.getX() - cornerA.getX();
            int dz = cornerB.getZ() - cornerA.getZ();
            int steps = Math.max(Math.abs(dx), Math.abs(dz));

            for (int i = 0; i <= steps; i++) {
                int x = cornerA.getX() + (dx * i) / steps;
                int z = cornerA.getZ() + (dz * i) / steps;
                edge.add(new BlockPos(x, cornerA.getY(), z));
            }
            return edge;
        }
    }

}
