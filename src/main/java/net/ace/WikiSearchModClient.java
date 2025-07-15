package net.ace;

import net.ace.keybind.KeybindManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class WikiSearchModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {  // 客户端初始化
        // 注册快捷键
        KeybindManager.registerKeybinds();

        // 绑定按键事件监听
        // 每一帧检测按键
        ClientTickEvents.END_CLIENT_TICK.register(KeybindManager::handleKeyPress);
    }
}
