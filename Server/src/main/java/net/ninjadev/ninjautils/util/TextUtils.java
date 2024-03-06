package net.ninjadev.ninjautils.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.ninjadev.ninjautils.data.NameColorState;
import net.ninjadev.ninjautils.feature.DimensionSymbolFeature;
import net.ninjadev.ninjautils.feature.NameColorFeature;
import net.ninjadev.ninjautils.init.ModConfigs;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.awt.*;

public class TextUtils {

    public static String getWorldName(RegistryKey<World> key) {
        if (key == World.OVERWORLD) return Formatting.DARK_GREEN + "Overworld " + Formatting.WHITE;
        if (key == World.NETHER) return Formatting.DARK_RED + "The Nether" + Formatting.WHITE;
        if (key == World.END) return Formatting.DARK_PURPLE + "The End   " + Formatting.WHITE;
        return "";
    }

    public static Color getWorldColor(RegistryKey<World> key) {
        if (key == World.OVERWORLD) return new Color(43520);
        if (key == World.NETHER) return new Color(0xAA0000);
        if (key == World.END) return new Color(0xAA00AA);
        return Color.WHITE;
    }

    public static String getDuration(long timeInMillis) {
        return DurationFormatUtils.formatDuration(timeInMillis, "HH:mm:ss");
    }

    public static Text getPlayerNameStyled(ServerPlayerEntity player, boolean withDimension) {
        MutableText name = Text.literal("");
        if (withDimension && ModConfigs.FEATURES.isEnabled(DimensionSymbolFeature.NAME)) {
            Color dimensionColor = TextUtils.getWorldColor(player.getWorld().getRegistryKey());
            name.append(Text.literal("■ ").withColor(dimensionColor.getRGB()));
        }
        Color color = Color.WHITE;
        if (ModConfigs.FEATURES.isEnabled(NameColorFeature.NAME)) {
            color = NameColorState.get().getPlayerColor(player.getUuid());
        }
        name.append(Text.literal(player.getNameForScoreboard()).withColor(color.getRGB()));

        return name;
    }
}
