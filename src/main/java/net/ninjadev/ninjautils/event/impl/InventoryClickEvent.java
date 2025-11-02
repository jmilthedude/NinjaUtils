package net.ninjadev.ninjautils.event.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import net.ninjadev.ninjautils.event.CancellableEvent;

public class InventoryClickEvent extends CancellableEvent<InventoryClickEvent.Data> {

    public static class Data extends CancellableEvent.Data {
        private final DefaultedList<Slot> slots;
        private final int slotIndex;
        private final int button;
        private final SlotActionType slotActionType;
        private final PlayerEntity player;

        public Data(DefaultedList<Slot> slots, int slotIndex, int button, SlotActionType slotActionType, PlayerEntity player) {
            this.slots = slots;
            this.slotIndex = slotIndex;
            this.button = button;
            this.slotActionType = slotActionType;
            this.player = player;
        }

        public DefaultedList<Slot> getSlots() {
            return slots;
        }

        public int getSlotIndex() {
            return slotIndex;
        }

        public int getButton() {
            return button;
        }

        public SlotActionType getSlotActionType() {
            return slotActionType;
        }

        public PlayerEntity getPlayer() {
            return player;
        }
    }
}
