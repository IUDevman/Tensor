package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.ColorSetting;
import dev.tensor.misc.imp.settings.NumberSetting;

import java.awt.*;

/**
 * @author IUDevman
 * @since 04-23-2021
 */

@Module.Info(name = "ClickGui", category = Category.Client)
public final class ClickGui extends Module {

    public final NumberSetting scrollSpeed = new NumberSetting("Scroll Speed", 10, 1, 20, 0);
    public final NumberSetting animationSpeed = new NumberSetting("Animation Speed", 200, 0, 1000, 0);
    public final NumberSetting opacity = new NumberSetting("Opacity", 155, 0, 255, 0);
    public final ColorSetting enabledColor = new ColorSetting("Enabled Color", new Color(0, 255, 0, 255));
    public final ColorSetting disabledColor = new ColorSetting("Disabled Color", new Color(255, 0, 0, 255));
    public final ColorSetting categoryColor = new ColorSetting("Category Color", new Color(88, 88, 88, 255));
    public final ColorSetting buttonColor = new ColorSetting("Button Color", new Color(195, 195, 195, 255));
    public final ColorSetting fontColor = new ColorSetting("Font Color", new Color(255, 255, 255, 255));

    public void onEnable() {
        if (!isNull()) Tensor.INSTANCE.GUI.enterGUI();

        disable();
    }
}
