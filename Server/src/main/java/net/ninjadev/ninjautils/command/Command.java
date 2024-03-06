package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public abstract class Command {

    public abstract String getName();
    public abstract int getPermissionLevel();
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = literal(this.getName());
        builder.requires((sender) -> sender.hasPermissionLevel(this.getPermissionLevel()));
        this.build(builder);
        dispatcher.register(builder);
    }

    public abstract void build(LiteralArgumentBuilder<ServerCommandSource> builder);

    protected final void sendFeedback(CommandContext<ServerCommandSource> context, Text message, boolean showOps) {
        context.getSource().sendFeedback(() -> message, showOps);
    }
}
