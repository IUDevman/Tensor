package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.ColorSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

/**
 * @author IUDevman
 * @since 05-05-2021
 */

@Module.Info(name = "Watermark", category = Category.HUD)
public final class Watermark extends Module {

    public final NumberSetting posX = new NumberSetting("X Position", 3, 0, 1000, 0);
    public final NumberSetting posY = new NumberSetting("Y Position", 3, 0, 1000, 0);
    public final ColorSetting color = new ColorSetting("Color", new Color(255, 255, 255 ,255));

    public void onRender2D(MatrixStack matrixStack) {
        final String watermark = Tensor.INSTANCE.MOD_NAME + " (" + Tensor.INSTANCE.MOD_VERSION + ")";

        DrawableHelper.drawStringWithShadow(matrixStack, getMinecraft().textRenderer, watermark, posX.getValue().intValue(), posY.getValue().intValue(), color.getValue().getRGB());
    }
}
