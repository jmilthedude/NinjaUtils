package net.ninjadev.ninjautilsclient;

import net.fabricmc.api.ClientModInitializer;
import net.ninjadev.ninjautilsclient.init.ModKeybinds;
import net.ninjadev.ninjautilsclient.init.ModSetup;

public class NinjaUtilsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModSetup.registerLifecycleEvents();
        ModKeybinds.register();
    }
}
