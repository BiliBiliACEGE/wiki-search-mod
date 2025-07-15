package net.ace.util;


import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.ace.config.ConfigScreenProvider;

public class ModMenuIntegration implements ModMenuApi { // 实现ModMenuApi接口
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreenProvider::createConfigScreen;
    }
}
