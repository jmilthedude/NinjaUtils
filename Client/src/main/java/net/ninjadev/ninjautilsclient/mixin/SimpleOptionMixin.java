package net.ninjadev.ninjautilsclient.mixin;

import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin<T> {

    @Shadow @Final
    Text text;

    @Shadow public T value;

    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    public void forceSetValue(T value, CallbackInfo ci) {
        if(this.text.getString().equalsIgnoreCase(I18n.translate("options.gamma"))) {
            this.value = value;
            ci.cancel();
        }
    }
}
