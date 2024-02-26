package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.ninjadev.ninjautils.data.InventorySaveState;
import net.ninjadev.ninjautils.data.entry.InventoryEntry;
import net.ninjadev.ninjautils.util.TextUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RestoreInventoryCommand extends Command {

    @Override
    public String getName() {
        return "restore";
    }

    @Override
    public int getPermissionLevel() {
        return 4;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(literal("query")
                        .then(argument("player", EntityArgumentType.player()).executes(this::queryPlayer)))
                .then(argument("player", EntityArgumentType.player())
                        .then(argument("index", IntegerArgumentType.integer(0)).suggests(new IndexSuggestionProvider())
                                .executes(this::restoreInventory)));
    }

    private int queryPlayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        List<InventoryEntry> inventories = InventorySaveState.get().getSavedInventories(player);
        inventories.sort(InventoryEntry::compareTo);
        int index = 0;
        for (InventoryEntry inventory : inventories) {
            context.getSource().sendMessage(Text.literal(String.format("%s: Time = %s, Items = %s", index++, TextUtils.getDuration(System.currentTimeMillis() - inventory.getTimestamp()), inventory.getItemCount())));
        }
        return 1;
    }

    private int restoreInventory(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        int index = IntegerArgumentType.getInteger(context, "index");

        InventorySaveState.get().restore(player, index);

        this.sendFeedback(context, Text.literal("Player's inventory has been restored!"), false);
        return 1;
    }

    private static class IndexSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
            List<InventoryEntry> savedInventories = InventorySaveState.get().getSavedInventories(player);
            if (savedInventories.size() == 1) {
                builder.suggest("0");
            } else {
                builder.suggest(String.format("<0-%s>", savedInventories.size() - 1));
            }
            return builder.buildFuture();
        }
    }
}
