package net.ninjadev.ninjautils.compat;

import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration;
import de.erdbeerbaerlp.dcintegration.common.storage.Configuration;
import de.erdbeerbaerlp.dcintegration.common.util.DiscordMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.data.NameColorState;

import java.awt.*;

public class DiscordIntegrationCompat {

    public void sendMessage(ServerPlayerEntity player, Text text) {
        Color color = NameColorState.get().getPlayerColor(player);
        final EmbedBuilder b = Configuration.instance().embedMode.playerLeaveMessages.toEmbed()
                .setColor(color)
                .setDescription("*" + text.getString() + "*");
        DiscordIntegration.INSTANCE.sendMessage(new DiscordMessage(b.build()), DiscordIntegration.INSTANCE.getChannel(Configuration.instance().advanced.serverChannelID));
    }

}
