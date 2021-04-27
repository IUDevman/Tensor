package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.ColorSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

/**
 * @author IUDevman
 * @since 04-23-2021
 */

@Module.Info(name = "ClickGui", category = Category.Client, bind = GLFW.GLFW_KEY_P)
public final class ClickGui extends Module {

    public final NumberSetting scrollSpeed = new NumberSetting("Scroll Speed", 10, 1, 20, 1, 0);
    public final NumberSetting animationSpeed = new NumberSetting("Animation Speed", 200, 0, 1000, 10, 0);
    public final NumberSetting opacity = new NumberSetting("Opacity", 155, 0, 255, 1, 0);
    public final ColorSetting activeColor = new ColorSetting("Active Color", new Color(0, 255, 0, 255));
    public final ColorSetting inactiveColor = new ColorSetting("Inactive Color", new Color(255, 0, 0, 255));
    public final ColorSetting outlineColor = new ColorSetting("Outline Color", new Color(88, 88, 88, 255));
    public final ColorSetting backgroundColor = new ColorSetting("Background Color", new Color(195, 195, 195, 255));
    public final ColorSetting fontColor = new ColorSetting("Font Color", new Color(255, 255, 255, 255));

    public void onEnable() {
        if (!isNull()) Tensor.INSTANCE.GUI.enterGUI();

        disable();
    }
}
