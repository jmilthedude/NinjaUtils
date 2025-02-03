package net.ninjadev.ninjautils.common.util;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedConstants {

    public static final String CLIENT_MOD_ID = "ninjautilsclient";
    public static final String SERVER_MOD_ID = "ninjautils";

    public static final Logger LOG = LoggerFactory.getLogger("NinjaUtils");

    public static Identifier clientId(String path) {
        return Identifier.of(CLIENT_MOD_ID, path);
    }

    public static Identifier serverId(String path) {
        return Identifier.of(SERVER_MOD_ID, path);
    }
}
