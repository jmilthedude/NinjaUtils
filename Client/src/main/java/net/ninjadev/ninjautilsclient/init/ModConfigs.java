package net.ninjadev.ninjautilsclient.init;


import net.ninjadev.ninjautilsclient.config.AntiFogModuleConfig;
import net.ninjadev.ninjautilsclient.config.FullBrightnessModuleConfig;

public class ModConfigs {

    public static AntiFogModuleConfig ANTI_FOG;
    public static FullBrightnessModuleConfig FULL_BRIGHT;


    public static void register() {
        ANTI_FOG = new AntiFogModuleConfig().readConfig();
        FULL_BRIGHT = new FullBrightnessModuleConfig().readConfig();
    }
}
