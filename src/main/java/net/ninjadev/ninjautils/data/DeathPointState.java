package net.ninjadev.ninjautils.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.NinjaUtils;
import net.ninjadev.ninjautils.feature.DeathPointFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModSetup;
import net.ninjadev.ninjautils.util.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DeathPointState extends PersistentState {

    protected static final String DATA_NAME = NinjaUtils.MOD_ID + "_deathpoints";

    private final HashMap<UUID, List<Entry>> entries = new HashMap<>();

    public void addEntry(UUID playerId, Entry entry) {
        List<Entry> entryList = this.entries.computeIfAbsent(playerId, id -> new ArrayList<>());
        entryList.sort(Entry::compareTo);
        DeathPointFeature deathpointFeature = ModConfigs.FEATURES.getFeature(DeathPointFeature.NAME);
        if (entryList.size() >= deathpointFeature.getMaxDeathpoints()) {
            entryList.remove(0);
        }
        entryList.add(entry);
        this.markDirty();
    }

    public void sendEntries(PlayerEntity player) {
        UUID playerId = player.getUuid();
        if (!entries.containsKey(playerId)) {
            player.sendMessage(Text.literal("You have no recent DeathPoints to display."));
        }
        List<Entry> entryList = this.entries.get(playerId);
        entryList.sort(Entry::compareTo);
        player.sendMessage(Text.literal("=== Your DeathPoints by Latest ==="));
        entryList.forEach(entry -> player.sendMessage(entry.getMessage(true)));
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        entries.forEach(((uuid, entryList) -> {
            NbtList list = new NbtList();
            entryList.stream().map(Entry::writeNbt).forEach(list::add);
            nbt.put(uuid.toString(), list);
        }));
        return nbt;
    }

    private static DeathPointState load(NbtCompound nbt) {
        DeathPointState state = new DeathPointState();
        for (String key : nbt.getKeys()) {
            NbtList list = nbt.getList(key, NbtElement.COMPOUND_TYPE);
            list.stream().map(element -> (NbtCompound) element).forEach(nbtCompound -> state.addEntry(UUID.fromString(key), Entry.fromNbt(nbtCompound)));
        }
        return state;
    }

    public static DeathPointState get() {
        return ModSetup.SERVER
                .getOverworld()
                .getPersistentStateManager()
                .getOrCreate(new PersistentState.Type<>(DeathPointState::new, DeathPointState::load, null), DATA_NAME);
    }

    public static class Entry implements Comparable<Entry> {
        private final BlockPos pos;
        private final Identifier worldId;
        private final long timeStamp;

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

        public NbtCompound writeNbt() {
            NbtCompound nbt = new NbtCompound();
            nbt.put("pos", NbtHelper.fromBlockPos(this.pos));
            nbt.putString("worldId", this.worldId.toString());
            nbt.putLong("timeStamp", this.timeStamp);
            return nbt;
        }

        public static Entry fromNbt(NbtCompound nbt) {
            BlockPos pos = NbtHelper.toBlockPos(nbt.getCompound("pos"));
            Identifier worldId = Identifier.tryParse(nbt.getString("worldId"));
            long timeStamp = nbt.getLong("timeStamp");
            return new Entry(pos, worldId, timeStamp);
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
