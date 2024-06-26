package net.ninjadev.ninjautils.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Accessor("portalCooldown")
    int getNetherPortalTime();

    @Accessor("portalCooldown")
    void setNetherPortalTime(int time);
}
