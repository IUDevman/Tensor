package dev.tensor.misc.gui.elements.settings;

import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.imp.settings.EnumSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public final class EnumElement extends SettingElement {

    public EnumElement(EnumSetting enumSetting, NumberSetting x, NumberSetting y, int posX, int posY) {
        super(enumSetting, x, y, posX, posY);
    }

    @Override
    public int getWidth() {
        return 204;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public int getPosX() {
        return getX().getValue().intValue() + getXPos();
    }

    @Override
    public void setPosX(int posX) {
        setXPos(posX);
    }

    @Override
    public int getPosY() {
        return getY().getValue().intValue() + getYPos();
    }

    @Override
    public void setPosY(int posY) {
        setYPos(posY);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + this.getHeight(), new Color(255, 0, 0, 150).getRGB()); //temp color for testing
    }
}
