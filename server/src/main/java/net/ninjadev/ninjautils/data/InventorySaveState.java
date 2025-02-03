package net.ninjadev.ninjautils.data;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.data.entry.InventoryEntry;
import net.ninjadev.ninjautils.init.ModSetup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventorySaveState extends PersistentState {

    private final HashMap<UUID, List<InventoryEntry>> entries = new HashMap<>();

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

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        entries.forEach((uuid, inventory) -> {
            SharedConstants.LOG.info("Writing inventories for player: {}", uuid);
            NbtList inventories = new NbtList();
            for (InventoryEntry inventoryEntry : inventory) {
                NbtCompound inventoryNbt = inventoryEntry.writeNbt(new NbtCompound(), ModSetup.SERVER.getRegistryManager());
                if (inventoryNbt != null) {
                    inventories.add(inventoryNbt);
                }
            }
            nbt.put(uuid.toString(), inventories);
            SharedConstants.LOG.info("Wrote inventories successfully.");
        });
        return nbt;
    }

    private static InventorySaveState load(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        InventorySaveState state = new InventorySaveState();
        for (String key : nbt.getKeys()) {
            SharedConstants.LOG.info("Loading inventories for player: {}", UUID.fromString(key));
            NbtList inventories = nbt.getList(key, NbtElement.COMPOUND_TYPE);
            inventories.stream().map(element -> (NbtCompound) element).forEach(inventoryNbt -> {
                InventoryEntry.fromNbt(registryLookup, inventoryNbt).ifPresent(value -> state.addInventory(UUID.fromString(key), value));
            });
            SharedConstants.LOG.info("Loaded inventory successfully");
        }
        return state;
    }

    public static InventorySaveState get() {
        return ModSetup.SERVER
                .getOverworld()
                .getPersistentStateManager()
                .getOrCreate(new PersistentState.Type<>(InventorySaveState::new, InventorySaveState::load, null), DATA_NAME);
    }

    protected static final String DATA_NAME = SharedConstants.SERVER_MOD_ID + "_inventorySave";


}
