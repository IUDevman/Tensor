package dev.tensor.misc.gui.elements.settings;

import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.imp.settings.EnumSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;

import java.awt.*;

/**
 * @author IUDevman
 * @since 05-04-2021
 */

public final class EnumElement extends SettingElement {

    private final EnumSetting enumSetting;

    public EnumElement(EnumSetting enumSetting, NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        super(x, y, scrollY, posX, posY);
        this.enumSetting = enumSetting;
    }

    public EnumSetting getEnumSetting() {
        return this.enumSetting;
    }

    @Override
    public int getWidth() {
        return 204;
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public int getPosX() {
        return getX().getValue().intValue() + getXPos();
    }

    @Override
    public int getPosY() {
        return getY().getValue().intValue() + getScrollY().getValue().intValue() + getYPos();
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + this.getHeight(), new Color(0, 0, 0, 150).getRGB());

        String text = getEnumSetting().getName() + " : " + Formatting.GRAY + getEnumSetting().getValue().name();
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, text, x + 3, y + 3, new Color(255, 255, 255, 255).getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        if(button == 0) getEnumSetting().increment();
    }
}
