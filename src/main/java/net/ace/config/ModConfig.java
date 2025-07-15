package net.ace.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.ace.util.LanguageHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig { // 配置类
    // 配置字段（使用@SerializedName定义JSON键名）
    @SerializedName("search_url_template")
    private String searchUrlTemplate;

    @SerializedName("use_auto_language")
    private boolean useAutoLanguage = true;

    // 单例模式
    private static ModConfig INSTANCE;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("in_game_wiki.json");

    private ModConfig() {
        // 私有构造函数防止外部实例化
        if (INSTANCE != null) {
            throw new IllegalStateException("ModConfig is a singleton!");
        }
        INSTANCE = this;
        setDefaultValues();
        validateValues();
    }

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    // 加载配置
    public static void load() {
        try {
            if (Files.notExists(CONFIG_PATH)) {
                INSTANCE = new ModConfig();
                INSTANCE.setDefaultValues();
                save(); // 创建默认配置文件
                return;
            }

            String json = Files.readString(CONFIG_PATH);
            INSTANCE = GSON.fromJson(json, ModConfig.class);
            INSTANCE.validateValues(); // 校验加载的值
        } catch (IOException | IllegalArgumentException e) {
            INSTANCE = new ModConfig();
            INSTANCE.setDefaultValues();
        }
    }

    // 保存配置
    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(INSTANCE);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
        }
    }

    // 设置默认值
    private void setDefaultValues() {
        this.searchUrlTemplate = "https://zh.minecraft.wiki/w/%s";
        this.useAutoLanguage = true;
    }

    // 校验配置值有效性
    private void validateValues() {
        if (searchUrlTemplate == null || !searchUrlTemplate.contains("%s")) {
            searchUrlTemplate = "https://zh.minecraft.wiki/w/%s";
        }
    }

    // Getter/Setter
    public String getSearchUrlTemplate() {
        return useAutoLanguage ?
                LanguageHelper.getSearchUrlTemplate() : // 自动语言检测
                searchUrlTemplate;
    }

    public void setSearchUrlTemplate(String template) {
        this.searchUrlTemplate = template;
        save();
    }

    public boolean isUseAutoLanguage() {
        return useAutoLanguage;
    }

    public void setUseAutoLanguage(boolean useAutoLanguage) {
        this.useAutoLanguage = useAutoLanguage;
        save();
    }
}