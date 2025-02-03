package net.ninjadev.ninjautils.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.ninjadev.ninjautils.feature.PeacefulPlayerFeature;
import net.ninjadev.ninjautils.feature.ShulkerDropsTwoFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract long getLootTableSeed();

    @Inject(method = "dropLoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootWorldContext;JLjava/util/function/Consumer;)V",
                    shift = At.Shift.BEFORE),
            cancellable = true
    )
    public void shulkerDrops(ServerWorld world, DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci, @Local LootTable lootTable, @Local LootWorldContext context) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof ShulkerEntity)) return;

        if (!ModConfigs.FEATURES.isEnabled(ShulkerDropsTwoFeature.NAME) || !(damageSource.getSource() instanceof ServerPlayerEntity))
            return;

        ci.cancel();

        ObjectArrayList<ItemStack> loot = lootTable.generateLoot(context, this.getLootTableSeed());
        ItemStack shells = new ItemStack(Items.SHULKER_SHELL, 2);
        entity.dropStack(world, shells);
        for (ItemStack itemStack : loot) {
            if (itemStack.getItem() == Items.SHULKER_SHELL) {
                continue;
            }
            entity.dropStack(world, itemStack);
        }
    }

    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    public void canTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        PeacefulPlayerFeature feature = ModConfigs.FEATURES.getFeature(PeacefulPlayerFeature.NAME);
        if (!feature.isEnabled()) return;
        if (target instanceof ServerPlayerEntity player) {
            String name = player.getNameForScoreboard();
            if (feature.getPlayers().contains(name)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    public void onDropInventory(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof PlayerEntity player)) return;
        PeacefulPlayerFeature feature = ModConfigs.FEATURES.getFeature(PeacefulPlayerFeature.NAME);
        if (feature.isEnabled() && feature.getPlayers().contains(player.getNameForScoreboard())) {
            ci.cancel();
        }
    }
}
