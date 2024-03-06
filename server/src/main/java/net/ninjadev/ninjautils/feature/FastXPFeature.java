package net.ninjadev.ninjautils.feature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.event.impl.PlayerEntityCollisionEvent;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModEvents;
import net.ninjadev.ninjautils.mixin.PlayerEntityAccessor;

import java.util.Optional;

public class FastXPFeature extends Feature {

    public static final String NAME = "fast_xp";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        ModEvents.PLAYER_ENTITY_COLLISION.register(this, this::handleFastXP);

    }

    private void handleFastXP(PlayerEntityCollisionEvent.Data data) {
        PlayerEntity player = data.getPlayer();
        for (Entity entity : data.getEntities()) {
            if(entity.getType() == EntityType.EXPERIENCE_ORB) {
                player.experiencePickUpDelay = 0;
                ((PlayerEntityAccessor)player).doCollideWithEntity(entity);
            }
        }
        data.getEntities().removeIf(entity -> entity.getType() == EntityType.EXPERIENCE_ORB);
    }

    @Override
    public void onDisable() {
        ModEvents.PLAYER_ENTITY_COLLISION.release(this);
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }
}
