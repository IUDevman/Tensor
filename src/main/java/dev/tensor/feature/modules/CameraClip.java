package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;

/**
 * @author IUDevman
 * @since 05-11-2021
 */

@Module.Info(name = "CameraClip", category = Category.Render)
public final class CameraClip extends Module {

    public final NumberSetting distance = new NumberSetting("Distance", 5.0, 1.0, 50.0, 1);
}
