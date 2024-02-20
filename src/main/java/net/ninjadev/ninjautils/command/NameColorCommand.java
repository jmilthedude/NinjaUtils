package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class NameColorCommand extends Command {
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
                .then(argument("color", ColorArgumentType.color())
                        .executes(this::setNameColor)));
    }

    private int setNameColor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        ServerWorld serverWorld = player.getServerWorld();
        ServerScoreboard scoreboard = serverWorld.getScoreboard();
        Formatting color = ColorArgumentType.getColor(context, "color");
        String teamName = color.getName().toLowerCase();
        Team team;
        if (!scoreboard.getTeamNames().contains(teamName)) {
            team = scoreboard.addTeam(teamName);
            team.setColor(Formatting.byName(teamName));
        } else {
            team = scoreboard.getTeam(teamName);
        }
        scoreboard.addScoreHolderToTeam(player.getName().getString(), team);
        this.sendFeedback(context, Text.literal("You have changed your name color to ").append(Text.literal(teamName.replace("_", " ")).formatted(color)).append("!"), false);
        return 1;
    }
}
