package net.ninjadev.ninjautils.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class EntitiesCommand extends Command {
    @Override
    public String getName() {
        return "entities";
    }

    @Override
    public int getPermissionLevel() {
        return 4;
    }

    @Override
    public void build(LiteralArgumentBuilder<ServerCommandSource> builder) {
        builder.then(literal("count")
                .then(argument("entities", EntityArgumentType.entities()).executes(this::countEntities)));
        builder.then(literal("glow")
                .then(argument("entities", EntityArgumentType.entities()).then(argument("duration", IntegerArgumentType.integer(0, 30)).executes(this::addGlowToEntities))));
    }

    private int addGlowToEntities(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "entities");
        int duration = IntegerArgumentType.getInteger(context, "duration");
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living) {
                StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.GLOWING, duration * 20, 0, true, false);
                living.addStatusEffect(statusEffectInstance);
            }
        }
        this.sendFeedback(context, Text.literal("Gave the specified living entities Glow effect for %s seconds".formatted(duration)), false);

        return 1;
    }

    private int countEntities(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "entities");
        HashMap<EntityType<?>, Integer> counts = new HashMap<>();
        for (Entity entity : entities) {
            counts.merge(entity.getType(), 1, Integer::sum);
        }
        List<Map.Entry<EntityType<?>, Integer>> list = new ArrayList<>(counts.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList());
        Collections.reverse(list);
        this.sendFeedback(context, Text.literal("=== Entity Counts ===").withColor(Formatting.DARK_AQUA.getColorValue()), false);
        for (Map.Entry<EntityType<?>, Integer> entry : list) {
            this.sendFeedback(context, entry.getKey().getName().copy().append(String.format(": %s", entry.getValue())), false);

        }
        return 1;
    }

    private static class CountTypeSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
            return null;
        }
    }
}
