package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import net.ninjadev.ninjautils.event.impl.InventoryClickEvent;
import net.ninjadev.ninjautils.init.ModEvents;
import net.ninjadev.ninjautils.init.ModPlayerManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {

    @Shadow
    @Final
    public DefaultedList<Slot> slots;

    @Shadow
    @Final
    private @Nullable ScreenHandlerType<?> type;

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (this.slots.isEmpty()) return;
        if (ModPlayerManager.isClientInstalled(player.getUuid())) return;
        InventoryClickEvent.Data data = ModEvents.INVENTORY_CLICK.invoke(new InventoryClickEvent.Data(this.slots, slotIndex, button, actionType, player));
        if (data.isCancelled()) {
            ci.cancel();
        }
    }
}
