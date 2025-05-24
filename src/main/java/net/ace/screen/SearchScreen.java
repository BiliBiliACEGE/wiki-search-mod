package net.ace.screen;

import net.ace.util.LanguageHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.awt.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class SearchScreen extends Screen {
    // 1. 声明为类成员变量
    private TextFieldWidget searchField;

    public SearchScreen() {
        super(Text.translatable("gui.wiki-search.title"));
    }

    @Override
    protected void init() {
        super.init();

        // 2. 初始化并添加组件
        this.searchField = new TextFieldWidget(
                this.textRenderer,
                this.width / 2 - 100,
                this.height / 2 - 20,
                200,
                20,
                Text.translatable("gui.wiki-search.placeholder")
        );
        this.addDrawableChild(this.searchField); // 必须添加到屏幕

        // 3. 搜索按钮
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("gui.wiki-search.button.search"),
                button -> performSearch()
        ).position(this.width / 2 - 50, this.height / 2 + 10).size(100, 20).build());
    }

    private void performSearch() {
        String query = this.searchField.getText().trim();

        if (query.isEmpty()) {
            this.searchField.setPlaceholder(Text.translatable("gui.wiki-search.error.empty"));
            return;
        }

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = String.format(
                    LanguageHelper.getSearchUrlTemplate(),
                    encodedQuery
            );

            // 尝试通过 Desktop 打开
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                // 备用方案
                openUrlViaCommandLine(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.close();
    }

    private void openUrlViaCommandLine(String url) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Process process;
            if (os.contains("linux")) {
                process = new ProcessBuilder("xdg-open", url).start();
            } else if (os.contains("win")) {
                process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                process = Runtime.getRuntime().exec("open " + url);
            } else {
                return;
            }
            process.waitFor(2, TimeUnit.SECONDS); // 等待命令执行
        } catch (Exception e) {
        }
    }
}

