package dev.tensor.misc.gui;

import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

public interface Element extends Wrapper {

    int getWidth();

    int getHeight();

    int getPosX();

    void setPosX(int posX);

    int getPosY();

    void setPosY(int posY);

    void render(MatrixStack matrixStack, int x, int y);

    void onClick(double mouseX, double mouseY);
}
