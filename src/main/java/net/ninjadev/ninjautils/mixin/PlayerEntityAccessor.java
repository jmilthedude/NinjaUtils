package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {

    @Invoker("collideWithEntity")
    void doCollideWithEntity(Entity entity);
}
