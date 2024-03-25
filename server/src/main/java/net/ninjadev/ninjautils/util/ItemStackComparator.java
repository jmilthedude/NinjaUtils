package net.ninjadev.ninjautils.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.ninjadev.ninjautils.init.ModSetup;

import java.util.ArrayList;
import java.util.List;

public class ItemStackComparator {

    static final List<ItemGroup> cachedGroups = new ArrayList<>();

    public static void init() {
        cachedGroups.addAll(ItemGroups.getGroups());
        for (ItemGroup cachedGroup : cachedGroups) {
            cachedGroup.updateEntries(new ItemGroup.DisplayContext(ModSetup.SERVER.getCommandSource().getEnabledFeatures(), false, ModSetup.SERVER.getRegistryManager()));
        }
    }


    public static int byItemGroup(ItemStack stack, ItemStack other) {

        if (cachedGroups.isEmpty()) {
            init();
        }

        for (ItemGroup itemGroup : cachedGroups) {
            boolean stackInGroup = itemGroup.getSearchTabStacks().stream().anyMatch(displayStack -> ItemStack.areItemsEqual(displayStack, stack));
            boolean otherInGroup = itemGroup.getSearchTabStacks().stream().anyMatch(displayStack -> ItemStack.areItemsEqual(displayStack, other));
            if (stackInGroup && otherInGroup) {
                List<ItemStack> stacks = new ArrayList<>(itemGroup.getDisplayStacks());
                int indexOfStack = stacks.indexOf(stacks.stream().filter(itemStack -> itemStack.getItem() == stack.getItem()).findFirst().orElse(ItemStack.EMPTY));
                int indexOfOther = stacks.indexOf(stacks.stream().filter(itemStack -> itemStack.getItem() == other.getItem()).findFirst().orElse(ItemStack.EMPTY));
                int compared = Integer.compare(indexOfStack, indexOfOther);
                if (compared != 0) return compared;
                if (stack.hasCustomName() || other.hasCustomName()) {
                    String stackName = stack.getName().getString();
                    String otherName = other.getName().getString();
                    return stackName.compareTo(otherName);
                }
                return Integer.compare(other.getCount(), stack.getCount());
            } else if (stackInGroup) {
                return -1;
            } else if (otherInGroup) {
                return 1;
            }
        }
        return 0;
    }
}
