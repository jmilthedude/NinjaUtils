package net.ninjadev.ninjautils.feature;

import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import net.ninjadev.ninjautils.event.impl.InventoryClickEvent;
import net.ninjadev.ninjautils.init.ModEvents;
import net.ninjadev.ninjautils.util.ItemStackComparator;

import java.util.ArrayList;
import java.util.List;

public class InventorySortFeature extends Feature {

    public static final String NAME = "inventory_sort";

    public InventorySortFeature(boolean enabled) {
        super(enabled);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        ModEvents.INVENTORY_CLICK.register(this, this::sortInventory);
    }

    @Override
    public void onDisable() {
        ModEvents.INVENTORY_CLICK.release(this);
    }

    private void sortInventory(InventoryClickEvent.Data data) {
        if (data.getSlotActionType() != SlotActionType.THROW) return;
        if (data.getSlotActionType() == SlotActionType.THROW && data.getSlotIndex() != -999) return;
        Inventory inventory = this.getInventory(data.getPlayer(), data.getSlots());
        if (!isSortableInventory(inventory)) return;

        boolean isPlayerInventory = inventory instanceof PlayerInventory;
        int minSlot = isPlayerInventory ? 9 : 0;
        int maxSlot = isPlayerInventory ? 36 : inventory.size();

        List<ItemStack> stacks = this.collectStacks(inventory, minSlot, maxSlot);

        stacks = combineStacks(stacks);
        stacks.sort(ItemStackComparator::compare);

        for (int j = minSlot; j < maxSlot; j++) {
            int next = j - minSlot;
            inventory.setStack(j, next < stacks.size() ? stacks.get(next) : ItemStack.EMPTY);
        }
    }

    private Inventory getInventory(PlayerEntity player, DefaultedList<Slot> slots) {
        Inventory inventory = slots.get(0).inventory;
        if (inventory instanceof CraftingResultInventory) {
            return player.getInventory();
        }
        return inventory;
    }

    private boolean isSortableInventory(Inventory inventory) {
        return inventory instanceof PlayerInventory ||
                inventory instanceof ChestBlockEntity ||
                inventory instanceof BarrelBlockEntity ||
                inventory instanceof ShulkerBoxBlockEntity ||
                inventory instanceof EnderChestInventory ||
                inventory instanceof DoubleInventory;
    }

    private List<ItemStack> collectStacks(Inventory inventory, int minSlot, int maxSlot) {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = minSlot; i < maxSlot; i++) {
            ItemStack item = inventory.getStack(i);
            if (!item.isEmpty()) {
                stacks.add(item);
            }
        }
        return stacks;
    }

    private List<ItemStack> combineStacks(List<ItemStack> items) {
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack stack : items) {
            int index = getIndexOfSimilar(result, stack);
            if (index == -1) {
                result.add(stack);
                continue;
            }

            ItemStack combinable = result.get(index);
            int combinableCount = combinable.getCount();
            if (combinableCount + stack.getCount() > stack.getMaxCount()) {
                combinable.setCount(stack.getMaxCount());
                stack.setCount(stack.getCount() - stack.getMaxCount() + combinableCount);
                result.add(stack);
            } else {
                combinable.setCount(combinableCount + stack.getCount());
            }
            result.set(index, combinable);

        }
        return result;
    }

    private int getIndexOfSimilar(List<ItemStack> items, ItemStack input) {
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (ItemStack.canCombine(item, input)) {
                if (item.getCount() < item.getMaxCount()) {
                    return i;
                }
            }
        }
        return -1;
    }
}
