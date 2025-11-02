package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.ninjadev.ninjautils.data.DeathPointState;

import static net.minecraft.server.command.CommandManager.literal;

public class DeathPointCommand extends Command {
    @Override
    public String getName() {
        return "deathpoint";
    }

    @Override
    public int getPermissionLevel() {
        return 0;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(literal("list").executes(this::sendDeathpoints));
    }

    private int sendDeathpoints(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        DeathPointState.get().sendEntries(player);
        return 1;
    }
}
