package net.ninjadev.ninjautilsclient;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import net.ninjadev.ninjautilsclient.init.ModKeybinds;
import net.ninjadev.ninjautilsclient.init.ModSetup;

public class NinjaUtilsClient implements ClientModInitializer {
    public static final String MOD_ID = "ninjautilsclient";

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitializeClient() {
        ModSetup.registerLifecycleEvents();
        ModKeybinds.register();
    }
}
