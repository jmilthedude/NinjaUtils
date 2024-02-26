package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.feature.PlayerSleepFeature;
import net.ninjadev.ninjautils.init.ModConfigs;

import static net.minecraft.server.command.CommandManager.argument;

public class SleepMessageCommand extends Command {
    @Override
    public String getName() {
        return "sleep_message";
    }

    @Override
    public int getPermissionLevel() {
        return 4;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(argument("message", StringArgumentType.greedyString()).executes(this::addSleepMessage));
    }

    private int addSleepMessage(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String message = StringArgumentType.getString(context, "message");

        PlayerSleepFeature sleepFeature = ModConfigs.FEATURES.getFeature(PlayerSleepFeature.NAME);
        sleepFeature.addMessage(message);

        this.sendFeedback(context, Text.literal("Sleep Message Added"), false);
        return 1;
    }
}
