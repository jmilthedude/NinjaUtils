package net.ninjadev.ninjautils.data.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryEntry extends HashMap<Integer, ItemStack> implements Comparable<InventoryEntry> {

    private final long timestamp;
    private final int experience;

    public record InventorySlotEntry(int slot, ItemStack stack) {
        public static final Codec<InventorySlotEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("slot").forGetter(InventorySlotEntry::slot),
                ItemStack.OPTIONAL_CODEC.fieldOf("stack").forGetter(InventorySlotEntry::stack)
        ).apply(instance, InventorySlotEntry::new));
    }

    public static final Codec<InventoryEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(InventoryEntry::getTimestamp),
            Codec.INT.fieldOf("experience").forGetter(InventoryEntry::getExperience),
            InventorySlotEntry.CODEC.listOf().fieldOf("inventory").forGetter(entry -> {
                List<InventorySlotEntry> list = new ArrayList<>();
                entry.forEach((slot, stack) -> list.add(new InventorySlotEntry(slot, stack)));
                return list;
            })
    ).apply(instance, (timestamp, experience, slotList) -> {
        InventoryEntry entry = new InventoryEntry(timestamp, experience);
        slotList.forEach(slotEntry -> entry.put(slotEntry.slot(), slotEntry.stack()));
        return entry;
    }));

    public InventoryEntry(long timestamp, int experience) {
        this.timestamp = timestamp;
        this.experience = experience;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getExperience() {
        return experience;
    }

    public int getItemCount() {
        return this.size();
    }

    public InventoryEntry applyInventory(ServerPlayerEntity player) {
        this.clear();
        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.getStack(slot);
            this.put(slot, stack);
        }
        return this;
    }

    @Override
    public int compareTo(@NotNull InventoryEntry o) {
        return Long.compare(o.timestamp, this.timestamp);
    }
}
