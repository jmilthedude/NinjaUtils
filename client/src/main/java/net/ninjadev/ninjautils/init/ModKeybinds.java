package net.ninjadev.ninjautils.init;


import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    public static KeyBinding toggleFogKey;
    public static KeyBinding toggleFullbrightKey;

    public static void register() {
        toggleFogKey = createKeyBinding("toggle_fog", GLFW.GLFW_KEY_END);
        toggleFullbrightKey = createKeyBinding("toggle_fullbright", GLFW.GLFW_KEY_MINUS);
    }

    private static KeyBinding createKeyBinding(String name) {
        return createKeyBinding(name, InputUtil.UNKNOWN_KEY.getCode());
    }

    private static KeyBinding createKeyBinding(String name, int key) {
        KeyBinding keyBind = new KeyBinding("key." + SharedConstants.CLIENT_MOD_ID + "." + name, key, "key.category." + SharedConstants.CLIENT_MOD_ID);
        KeyBindingHelper.registerKeyBinding(keyBind);
        return keyBind;
    }
}
