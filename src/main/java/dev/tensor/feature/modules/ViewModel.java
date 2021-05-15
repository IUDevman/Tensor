package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Module.Info(name = "ViewModel", category = Category.Render)
public final class ViewModel extends Module {

    public final BooleanSetting renderEmptyMainHand = new BooleanSetting("Render Empty Mainhand", false);
    public final NumberSetting vertical = new NumberSetting("Vertical", 0.20, -2.00, 2.00, 2);
    public final NumberSetting horizontal = new NumberSetting("Horizontal", -1.20, -2.00, 2.00, 2);
    public final NumberSetting scale = new NumberSetting("Scale", 1.00, 0.10, 1.50, 2);
}
