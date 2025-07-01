package net.ninjadev.ninjautils.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.feature.DeathPointFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModSetup;
import net.ninjadev.ninjautils.util.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DeathPointState extends PersistentState {

    protected static final String DATA_NAME = SharedConstants.SERVER_MOD_ID + "_deathpoints";

    private final HashMap<UUID, List<Entry>> entries = new HashMap<>();

    private static final Codec<DeathPointState> CODEC = Codec.unboundedMap(
            Codec.STRING,
            Entry.CODEC.listOf()
    ).xmap(
            entryMap -> {
                DeathPointState state = new DeathPointState();
                entryMap.forEach((key, entryList) -> {
                    UUID uuid = UUID.fromString(key);
                    state.entries.put(uuid, new ArrayList<>(entryList));
                });
                return state;
            },
            state -> {
                HashMap<String, List<Entry>> entryMap = new HashMap<>();
                state.entries.forEach((uuid, entryList) -> entryMap.put(uuid.toString(), new ArrayList<>(entryList)));
                return entryMap;
            }
    );

    public static final PersistentStateType<DeathPointState> TYPE = new PersistentStateType<>(DATA_NAME, DeathPointState::new, CODEC, null);

    public void addEntry(UUID playerId, Entry entry) {
        List<Entry> entryList = this.entries.computeIfAbsent(playerId, id -> new ArrayList<>());
        entryList.sort(Entry::compareTo);
        DeathPointFeature deathpointFeature = ModConfigs.FEATURES.getFeature(DeathPointFeature.NAME);
        if (entryList.size() >= deathpointFeature.getMaxDeathpoints()) {
            entryList.removeFirst();
        }
        entryList.add(entry);
        this.markDirty();
    }

    public void sendEntries(PlayerEntity player) {
        UUID playerId = player.getUuid();
        if (!entries.containsKey(playerId)) {
            player.sendMessage(Text.literal("You have no recent DeathPoints to display."), false);
        }
        List<Entry> entryList = this.entries.get(playerId);
        entryList.sort(Entry::compareTo);
        player.sendMessage(Text.literal("=== Your DeathPoints by Latest ==="), false);
        entryList.forEach(entry -> player.sendMessage(entry.getMessage(true), false));
    }

    public static DeathPointState get() {
        return ModSetup.SERVER
                .getOverworld()
                .getPersistentStateManager()
                .getOrCreate(TYPE);
    }

    public static class Entry implements Comparable<Entry> {
        private final BlockPos pos;
        private final Identifier worldId;
        private final long timeStamp;

        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Entry::getPos),
                Identifier.CODEC.fieldOf("worldId").forGetter(Entry::getWorldId),
                Codec.LONG.fieldOf("timeStamp").forGetter(Entry::getTimeStamp)
        ).apply(instance, Entry::new));

        public Entry(BlockPos pos, Identifier worldId, long timeStamp) {
            this.pos = pos;
            this.worldId = worldId;
            this.timeStamp = timeStamp;
        }

        public BlockPos getPos() {
            return pos;
        }

        public Identifier getWorldId() {
            return worldId;
        }

        public RegistryKey<World> getWorldKey() {
            if (this.worldId.equals(World.OVERWORLD.getValue())) return World.OVERWORLD;
            if (this.worldId.equals(World.NETHER.getValue())) return World.NETHER;
            if (this.worldId.equals(World.END.getValue())) return World.END;
            return null;
        }

        public Optional<World> getWorld() {
            if (this.getWorldKey() == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(ModSetup.SERVER.getWorld(this.getWorldKey()));
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        @Override
        public int compareTo(@NotNull DeathPointState.Entry o) {
            return Long.compare(o.timeStamp, this.timeStamp);
        }

        public Text getMessage(boolean withTime) {

            MutableText message = Text.literal("");
            if (withTime) {
                message.append(Text.literal(this.getTimeSince() + " - ").formatted(Formatting.GRAY));
            }
            String s = String.format("%s -> %sx%s%s%s, %sy%s%s%s, %sz%s%s%s ",
                    TextUtils.getWorldName(this.getWorldKey()),
                    Formatting.YELLOW, Formatting.GRAY, this.pos.getX(), Formatting.WHITE,
                    Formatting.YELLOW, Formatting.GRAY, this.pos.getY(), Formatting.WHITE,
                    Formatting.YELLOW, Formatting.GRAY, this.pos.getZ(), Formatting.WHITE);
            message.append(s);
            return message;

        }

        private String getTimeSince() {
            long timeSince = System.currentTimeMillis() - this.timeStamp;
            return TextUtils.getDuration(timeSince);
        }
    }
}
