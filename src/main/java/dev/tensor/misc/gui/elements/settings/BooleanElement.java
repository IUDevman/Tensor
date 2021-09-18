package dev.tensor.misc.gui.elements.settings;

import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;

import java.awt.*;

/**
 * @author IUDevman
 * @since 05-04-2021
 */

public final class BooleanElement extends SettingElement {

    private final BooleanSetting booleanSetting;

    private final Color fillColor = new Color(0, 0, 0, 150);
    private final Color textColor = new Color(255, 255, 255, 255);

    public BooleanElement(BooleanSetting booleanSetting, NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        super(x, y, scrollY, posX, posY);
        this.booleanSetting = booleanSetting;
    }

    public BooleanSetting getBooleanSetting() {
        return this.booleanSetting;
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
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + this.getHeight(), this.fillColor.getRGB());

        String text = getBooleanSetting().getName() + " (" + (getBooleanSetting().getValue() ? Formatting.GREEN + "true" : Formatting.RED + "false") + Formatting.RESET + ")";
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, text, x + 3, y + 3, this.textColor.getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.booleanSetting.setValue(!this.booleanSetting.getValue());
    }
}
