package net.ninjadev.ninjautils.init.client;


import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.ninjadev.ninjautils.util.Constants;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    private static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(Identifier.of(Constants.CLIENT_MOD_ID, "general"));

    public static KeyBinding toggleFogKey;
    public static KeyBinding toggleFullbrightKey;
    public static KeyBinding sortInventory;

    public static void register() {
        toggleFogKey = createKeyBinding("toggle_fog", GLFW.GLFW_KEY_END);
        toggleFullbrightKey = createKeyBinding("toggle_fullbright", GLFW.GLFW_KEY_MINUS);
        sortInventory = createKeyBinding("sort_inventory", GLFW.GLFW_KEY_R);
    }

    private static KeyBinding createKeyBinding(String name) {
        return createKeyBinding(name, InputUtil.UNKNOWN_KEY.getCode());
    }

    private static KeyBinding createKeyBinding(String name, int key) {
        KeyBinding keyBind = new KeyBinding("key." + Constants.CLIENT_MOD_ID + "." + name, key, CATEGORY);
        KeyBindingHelper.registerKeyBinding(keyBind);
        return keyBind;
    }
}
