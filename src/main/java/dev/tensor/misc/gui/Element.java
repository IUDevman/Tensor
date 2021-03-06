package dev.tensor.misc.gui;

import dev.tensor.misc.imp.Global;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

public interface Element extends Global {

    int getWidth();

    int getHeight();

    int getPosX();

    int getPosY();

    void render(MatrixStack matrixStack, int x, int y);

    void onClick(double mouseX, double mouseY);
}
