package net.ace.keybind;

public enum KeybindCategory {
    MAIN("category.wiki_search.main"); // 使用翻译键

    private final String translationKey;

    KeybindCategory(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}