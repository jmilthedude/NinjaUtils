package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ninjadev.ninjautils.data.NameColorState;
import net.ninjadev.ninjautils.init.ModSetup;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.util.Formatting.*;

public class NameColorCommand extends Command {
    public static final MutableText MESSAGE = Text.literal("Invalid color given. Must be one suggested or in hex #000000 format.");
    public static final CommandExceptionType INVALID_COLOR = new SimpleCommandExceptionType(MESSAGE);

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public int getPermissionLevel() {
        return 0;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(literal("set")
                .then(argument("color", StringArgumentType.word()).suggests(new ColorSuggestionProvider())
                        .executes(this::setNameColor)));
    }

    private int setNameColor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        Color color;
        String colorString = StringArgumentType.getString(context, "color");
        try {
            Formatting formatting = Formatting.byName(colorString);
            color = new Color(formatting.getColorValue());
        } catch (IllegalArgumentException | NullPointerException exception) {
            try {
                color = Color.decode("#" + colorString);
            } catch (Exception ex) {
                throw new CommandSyntaxException(INVALID_COLOR, MESSAGE);
            }
        }

        NameColorState.get().setPlayerColor(player.getUuid(), color);
        ModSetup.SERVER.getPlayerManager().sendToAll(PlayerListS2CPacket.entryFromPlayer(List.of(player)));
        this.sendFeedback(context, Text.literal("You have changed your name color!"), false);
        return 1;
    }

    private static class ColorSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
            try {
                Formatting formatting = ColorArgumentType.getColor(context, "color");
                if (formatting == null) {
                    return ColorArgumentType.color().listSuggestions(context, builder);
                }
            } catch (IllegalArgumentException | NullPointerException exception) {
                builder.suggest("<hex>");
                for (Formatting value : Formatting.values()) {
                    if (value != OBFUSCATED
                            && value != BOLD
                            && value != STRIKETHROUGH
                            && value != UNDERLINE
                            && value != ITALIC
                            && value != RESET) {
                        builder.suggest(value.getName().toLowerCase());
                    }
                }
            }
            return builder.buildFuture();
        }
    }
}
