package net.ace.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenProvider {
    public static Screen createConfigScreen(Screen parent) {
        ModConfig config = ModConfig.getInstance();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setSavingRunnable(ModConfig::save);

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.general"));

        // 自动语言开关
        general.addEntry(builder.entryBuilder()
                .startBooleanToggle(Text.translatable("option.auto_language"), config.isUseAutoLanguage())
                .setSaveConsumer(config::setUseAutoLanguage)
                .build());

        // 自定义模板输入框（仅在关闭自动语言时显示）
        general.addEntry(builder.entryBuilder()
                .startStrField(Text.translatable("option.custom_template"), config.getSearchUrlTemplate())
                .setTooltip(Text.translatable("tooltip.wiki_search.custom_template"))
                .setSaveConsumer(config::setSearchUrlTemplate)
                .setRequirement(() -> !config.isUseAutoLanguage()) // ✅ 动态条件
                .build());

        return builder.build();
    }
}

