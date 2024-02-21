package net.ninjadev.ninjautils.init;

import net.ninjadev.ninjautils.event.Event;
import net.ninjadev.ninjautils.event.impl.BlockUseEvent;
import net.ninjadev.ninjautils.event.impl.PlayerEntityCollisionEvent;
import net.ninjadev.ninjautils.event.impl.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class ModEvents {

    public static final List<Event<?, ?>> REGISTRY = new ArrayList<>();

    public static final BlockUseEvent BLOCK_USE = register(new BlockUseEvent());
    public static final InventoryClickEvent INVENTORY_CLICK = register(new InventoryClickEvent());
    public static final PlayerEntityCollisionEvent PLAYER_ENTITY_COLLISION = register(new PlayerEntityCollisionEvent());

    public static void release(Object reference) {
        REGISTRY.forEach(event -> event.release(reference));
    }

    private static <T extends Event<?, ?>> T register(T event) {
        REGISTRY.add(event);
        return event;
    }

    public static void releaseAll() {
        REGISTRY.forEach(Event::releaseAll);
    }
}
