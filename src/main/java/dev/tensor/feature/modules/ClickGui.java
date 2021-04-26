package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;

/**
 * @author IUDevman
 * @since 04-23-2021
 */

@Module.Info(name = "ClickGui", category = Category.Client)
public final class ClickGui extends Module {

    public final NumberSetting scrollSpeed = new NumberSetting("Scroll Speed", 10, 1, 20, 1, 0);
    public final NumberSetting animationSpeed = new NumberSetting("Animation Speed", 200, 0, 1000, 10, 0);

    public void onEnable() {
        if (isNull()) {
            disable();
            return;
        }

        Tensor.INSTANCE.GUI.enterGUI();
    }
}
