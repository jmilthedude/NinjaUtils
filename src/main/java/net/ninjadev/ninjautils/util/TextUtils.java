package net.ninjadev.ninjautils.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class TextUtils {

    public static String getWorldName(RegistryKey<World> key) {
        if (key == World.OVERWORLD) return Formatting.DARK_GREEN + "Overworld " + Formatting.WHITE;
        if (key == World.NETHER) return Formatting.DARK_RED + "The Nether" + Formatting.WHITE;
        if (key == World.END) return Formatting.DARK_PURPLE + "The End   " + Formatting.WHITE;
        return "";
    }

    public static String getDuration(long timeInMillis) {
        return DurationFormatUtils.formatDuration(timeInMillis, "HH:mm:ss");
    }
}
