package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

@Module.Info(name = "Reach", category = Category.Player)
public final class Reach extends Module {

    public final NumberSetting distance = new NumberSetting("Distance", 5, 3, 8, 2);
}
