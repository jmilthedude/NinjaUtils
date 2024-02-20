package net.ninjadev.ninjautils.util;

import net.minecraft.item.ItemStack;

public class ItemStackComparator {

    public static int compare(ItemStack stack, ItemStack other) {
        String thisName = stack.getItem().getName().getString();
        String otherName = other.getItem().getName().getString();
        int byName = thisName.compareTo(otherName);

        if (byName != 0) return byName;

        return Integer.compare(other.getCount(), stack.getCount());
    }

}
