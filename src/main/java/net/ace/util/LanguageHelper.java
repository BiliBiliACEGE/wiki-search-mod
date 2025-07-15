package net.ace.util;

import net.ace.config.ModConfig;
import net.minecraft.client.MinecraftClient;

public class LanguageHelper { // 语言助手类
    public static String getSearchUrlTemplate() {
        ModConfig config = ModConfig.getInstance();

        if (config.isUseAutoLanguage()) {
            // 直接获取语言代码字符串
            String langCode = MinecraftClient.getInstance().getLanguageManager().getLanguage();
            return langCode.startsWith("zh") ?
                    "https://zh.minecraft.wiki/w/%s" :
                    "https://minecraft.wiki/w/%s";
        } else {
            return config.getSearchUrlTemplate();
        }
    }
}
