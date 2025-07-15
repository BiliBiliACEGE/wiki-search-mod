package net.ace.keybind;

public enum KeybindCategory { // 定义一个枚举类，用于表示快捷键的分类
    MAIN("category.wiki_search.main"); // 使用翻译键

    private final String translationKey;

    KeybindCategory(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}