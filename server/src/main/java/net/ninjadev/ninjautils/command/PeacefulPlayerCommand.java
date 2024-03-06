package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.feature.PeacefulPlayerFeature;
import net.ninjadev.ninjautils.init.ModConfigs;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PeacefulPlayerCommand extends Command {
    @Override
    public String getName() {
        return "peaceful_player";
    }

    @Override
    public int getPermissionLevel() {
        return 4;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(literal("add").then(
                argument("player", EntityArgumentType.player())
                        .executes(this::addPlayer)));

        builder.then(literal("remove")
                .then(argument("player", StringArgumentType.word())
                        .suggests((context, suggestionsBuilder) -> {
                            PeacefulPlayerFeature feature = ModConfigs.FEATURES.getFeature(PeacefulPlayerFeature.NAME);
                            for (String player : feature.getPlayers()) {
                                suggestionsBuilder.suggest(player);
                            }
                            return suggestionsBuilder.buildFuture();
                        })
                        .executes(this::removePlayer)));
    }

    private int addPlayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        String playerName = player.getNameForScoreboard();

        PeacefulPlayerFeature feature = ModConfigs.FEATURES.getFeature(PeacefulPlayerFeature.NAME);
        if (feature.addPlayer(playerName)) {
            this.sendFeedback(context, Text.literal(String.format("%s has been set to peaceful.", playerName)), false);
            ModConfigs.FEATURES.markDirty();
        } else {
            this.sendFeedback(context, Text.literal(String.format("%s is already set to peaceful.", playerName)), false);
        }

        return 1;
    }

    private int removePlayer(CommandContext<ServerCommandSource> context) {
        String playerName = StringArgumentType.getString(context, "player");
        PeacefulPlayerFeature feature = ModConfigs.FEATURES.getFeature(PeacefulPlayerFeature.NAME);
        if (feature.removePlayer(playerName)) {
            this.sendFeedback(context, Text.literal(String.format("%s has been set to normal.", playerName)), false);
            ModConfigs.FEATURES.markDirty();
        } else {
            this.sendFeedback(context, Text.literal(String.format("%s is already set to normal.", playerName)), false);
        }

        return 1;
    }
}
