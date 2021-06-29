package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.HUDModule;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;

import java.awt.*;

/**
 * @author IUDevman
 * @since 06-29-2021
 */

@Module.Info(name = "Watermark", category = Category.HUD)
public final class Watermark extends Module implements HUDModule {

    public final NumberSetting x = new NumberSetting("X", 1, 0, 1000, 0);
    public final NumberSetting y = new NumberSetting("Y", 1, 0, 1000, 0);

    private final String text = Tensor.INSTANCE.MOD_NAME + " (" + Formatting.YELLOW + Tensor.INSTANCE.MOD_VERSION + Formatting.RESET + ")";
    private final Color textColor = new Color(255, 255, 255, 255);

    @Override
    public NumberSetting getStartX() {
        return this.x;
    }

    @Override
    public NumberSetting getStartY() {
        return this.y;
    }

    @Override
    public void onRender2D(MatrixStack matrixStack) {
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, text, x.getValue().intValue(), y.getValue().intValue(), textColor.getRGB());
    }
}
