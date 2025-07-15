package net.ace.keybind;

import net.ace.config.ConfigScreenProvider;
import net.ace.screen.SearchScreen;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindManager { // 快捷键管理器
    private static KeyBinding searchKey;
    private static KeyBinding configKey;

    public static void registerKeybinds() {
        // 注册搜索键 (B键)
        searchKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wiki-search.open_search",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KeybindCategory.MAIN.getTranslationKey()
        ));

        // 注册配置键 (C键)
        configKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wiki-search.open_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                KeybindCategory.MAIN.getTranslationKey()
        ));
    }

    // 处理所有快捷键事件
    public static void handleKeyPress(MinecraftClient client) {
        if (client.player == null) return; // 确保在游戏内

        // 处理搜索键
        while (searchKey.wasPressed()) {
            client.setScreen(new SearchScreen());
        }

        // 处理配置键
        while (configKey.wasPressed()) {
            client.setScreen(ConfigScreenProvider.createConfigScreen(client.currentScreen));
        }
    }
}