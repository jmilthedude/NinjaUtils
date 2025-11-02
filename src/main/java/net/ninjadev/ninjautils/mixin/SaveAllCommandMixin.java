package net.ninjadev.ninjautils.mixin;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.SaveAllCommand;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(SaveAllCommand.class)
public class SaveAllCommandMixin {

    @Redirect(method = "saveAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendFeedback(Ljava/util/function/Supplier;Z)V", ordinal = 1))
    private static void stopAnnoyingFeedback(ServerCommandSource source, Supplier<Text> feedback, boolean broadcastToOps) {
        source.sendFeedback(feedback, false);
    }
}
