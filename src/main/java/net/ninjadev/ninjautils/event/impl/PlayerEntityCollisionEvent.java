package net.ninjadev.ninjautils.event.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ninjadev.ninjautils.event.Event;

import java.util.List;

public class PlayerEntityCollisionEvent extends Event<PlayerEntityCollisionEvent, PlayerEntityCollisionEvent.Data> {

    public static class Data {
        private final PlayerEntity player;
        private final List<Entity> entities;

        public Data(PlayerEntity player, List<Entity> entities) {
            this.player = player;
            this.entities = entities;
        }

        public PlayerEntity getPlayer() {
            return player;
        }

        public List<Entity> getEntities() {
            return entities;
        }
    }
}
