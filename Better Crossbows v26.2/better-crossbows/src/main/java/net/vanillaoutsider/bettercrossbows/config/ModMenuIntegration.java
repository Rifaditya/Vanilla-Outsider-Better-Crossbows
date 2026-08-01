package net.vanillaoutsider.bettercrossbows.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.dasik.social.api.config.GuiHelper;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
            "bettercrossbows",
            "net.vanillaoutsider.bettercrossbows.config.ClothConfigScreenHelper",
            "createFactory"
        );
    }
}
