package dev.tensor.misc.imp;

import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author IUDevman
 * @since 06-29-2021
 */

public interface HUDComponent {

    NumberSetting getStartX();

    NumberSetting getStartY();

    void onRender2D(MatrixStack matrixStack);
}
