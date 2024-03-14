package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.ninjadev.ninjautils.common.data.PlayerSettingsData;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.common.network.C2SNotifyPacket;
import net.ninjadev.ninjautils.common.network.C2SSyncSettingsPacket;
import net.ninjadev.ninjautils.event.InputEvents;
import net.ninjadev.ninjautils.feature.InventorySortFeature;

public class ModSetup {

    public static void registerLifecycleEvents() {

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ModConfigs.register();
            ModFeatures.init();
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> InputEvents.handleInput());

        ClientTickEvents.END_CLIENT_TICK.register(client -> ModConfigs.FEATURES.features.stream().filter(Feature::isEnabled).forEach(Feature::onTick));

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.player != null) {
                ClientPlayNetworking.send(new C2SNotifyPacket(client.player.getUuid()));

                InventorySortFeature feature = ModConfigs.FEATURES.getFeature(InventorySortFeature.NAME);
                PlayerSettingsData data = new PlayerSettingsData()
                        .setSortInventoryEnabled(feature.isEnabled())
                        .setSortInventoryKeybind(feature.useKeybind());
                ClientPlayNetworking.send(new C2SSyncSettingsPacket(data));
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            ModConfigs.FEATURES.save();
        });

    }
}
