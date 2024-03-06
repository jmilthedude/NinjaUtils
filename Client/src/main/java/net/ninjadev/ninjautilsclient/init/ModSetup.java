package net.ninjadev.ninjautilsclient.init;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.ninjadev.ninjautilsclient.network.NotifyServerPacket;
import net.ninjadev.ninjautilsclient.util.Tickable;

public class ModSetup {

    public static void registerLifecycleEvents() {

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ModConfigs.register();
            ModModules.register();
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            handleInput();
            ModModules.getModules().stream().filter(module -> module instanceof Tickable).map(module -> (Tickable) module).forEach(Tickable::tick);
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ClientPlayNetworking.send(new NotifyServerPacket(1));
        });

    }

    private static void handleInput() {
        if (ModKeybinds.toggleFogKey.wasPressed()) {
            ModModules.ANTI_FOG.toggle();
        }
        if (ModKeybinds.toggleFullbrightKey.wasPressed()) {
            ModModules.FULL_BRIGHT.toggle();
        }
    }
}
