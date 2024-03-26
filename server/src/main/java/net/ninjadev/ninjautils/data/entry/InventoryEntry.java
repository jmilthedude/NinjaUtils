package net.ninjadev.ninjautils.data.entry;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

public class InventoryEntry extends HashMap<Integer, ItemStack> implements Comparable<InventoryEntry> {

    private final long timestamp;
    private final int experience;

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

    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList inventoryList = new NbtList();
        for (Entry<Integer, ItemStack> entry : this.entrySet()) {
            if (entry.getValue().isEmpty()) continue;
            NbtCompound compound = new NbtCompound();
            compound.putInt("slot", entry.getKey());
            NbtCompound stackNbt = new NbtCompound();
            entry.getValue().writeNbt(stackNbt);
            compound.put("stack", stackNbt);
            inventoryList.add(compound);
        }
        nbt.putLong("timestamp", this.timestamp);
        nbt.putLong("experience", this.experience);
        nbt.put("inventory", inventoryList);
        return nbt;
    }

    public static Optional<InventoryEntry> fromNbt(NbtCompound nbt) {
        if (!nbt.contains("inventory")) return Optional.empty();
        long timestamp = nbt.getLong("timestamp");
        int experience = nbt.getInt("experience");
        InventoryEntry entry = new InventoryEntry(timestamp, experience);
        NbtList list = nbt.getList("inventory", NbtElement.COMPOUND_TYPE);
        list.stream().map(element -> (NbtCompound) element).forEach(compound -> {
            int slot = compound.getInt("slot");
            ItemStack stack = ItemStack.fromNbt(compound.getCompound("stack"));
            entry.put(slot, stack);
        });
        return Optional.of(entry);
    }

    @Override
    public int compareTo(@NotNull InventoryEntry o) {
        return Long.compare(o.timestamp, this.timestamp);
    }
}
