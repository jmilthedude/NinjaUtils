package net.ninjadev.ninjautils.common.util;

import net.minecraft.util.Identifier;

public class SharedConstants {

    public static final String CLIENT_MOD_ID = "ninjautilsclient";
    public static final String SERVER_MOD_ID = "ninjautils";

    public static Identifier clientId(String path) {
        return new Identifier(CLIENT_MOD_ID, path);
    }

    public static Identifier serverId(String path) {
        return new Identifier(SERVER_MOD_ID, path);
    }
}
