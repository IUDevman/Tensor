package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Module.Info(name = "NoOverlay", category = Category.Render)
public final class NoOverlay extends Module {

    public final BooleanSetting vignette = new BooleanSetting("Vignette", true);
    public final BooleanSetting pumpkin = new BooleanSetting("Pumpkin", true);
    public final BooleanSetting bossBar = new BooleanSetting("Boss Bar", true);
    public final BooleanSetting status = new BooleanSetting("Status", false);
}
