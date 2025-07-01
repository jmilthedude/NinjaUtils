package net.ninjadev.ninjautils.data;

import com.mojang.serialization.Codec;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.data.entry.InventoryEntry;
import net.ninjadev.ninjautils.init.ModSetup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventorySaveState extends PersistentState {

    protected static final String DATA_NAME = SharedConstants.SERVER_MOD_ID + "_inventorySave";

    private final HashMap<UUID, List<InventoryEntry>> entries = new HashMap<>();

    public static final Codec<InventorySaveState> CODEC = Codec.unboundedMap(
            Codec.STRING,
            InventoryEntry.CODEC.listOf()
    ).xmap(
            entryMap -> {
                InventorySaveState state = new InventorySaveState();
                entryMap.forEach((key, entryList) -> {
                    UUID uuid = UUID.fromString(key);
                    // Convert immutable list into mutable ArrayList at deserialization time
                    state.entries.put(uuid, new ArrayList<>(entryList));
                });
                return state;
            },
            state -> {
                HashMap<String, List<InventoryEntry>> entryMap = new HashMap<>();
                state.entries.forEach((uuid, entryList) -> entryMap.put(uuid.toString(), entryList));
                return entryMap;
            }
    );

    public static final PersistentStateType<InventorySaveState> TYPE = new PersistentStateType<>(DATA_NAME, InventorySaveState::new, CODEC, null);

    public void addInventory(ServerPlayerEntity player) {
        InventoryEntry entry = new InventoryEntry(System.currentTimeMillis(), player.totalExperience).applyInventory(player);
        this.addInventory(player.getUuid(), entry);
    }

    public void addInventory(UUID uuid, InventoryEntry value) {
        this.entries.computeIfAbsent(uuid, id -> new ArrayList<>()).add(value);

        List<InventoryEntry> inventoryEntries = this.entries.get(uuid);
        inventoryEntries.sort(InventoryEntry::compareTo);
        if (inventoryEntries.size() > 5) {
            inventoryEntries.removeLast();
        }
        this.markDirty();
    }

    public List<InventoryEntry> getSavedInventories(ServerPlayerEntity player) {
        List<InventoryEntry> inventories = this.entries.getOrDefault(player.getUuid(), new ArrayList<>());
        inventories.sort(InventoryEntry::compareTo);
        return inventories;
    }

    public void restore(ServerPlayerEntity player, int index) {
        List<InventoryEntry> savedInventories = this.getSavedInventories(player);
        if (index >= savedInventories.size()) return;
        SharedConstants.LOG.info("Restoring inventory for player: {}", player.getName());

        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            player.dropItem(stack, false, true);
            inventory.setStack(i, ItemStack.EMPTY);
        }
        InventoryEntry inventoryEntry = savedInventories.get(index);
        player.setExperiencePoints(0);
        player.setExperienceLevel(0);
        player.addExperience(inventoryEntry.getExperience());
        inventoryEntry.forEach(inventory::setStack);
        SharedConstants.LOG.info("Inventory restored.");
    }

    public static InventorySaveState get() {
        return ModSetup.SERVER
                .getOverworld()
                .getPersistentStateManager()
                .getOrCreate(TYPE);
    }



}
