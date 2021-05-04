package dev.tensor.misc.gui.elements.settings;

import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

public final class NumberElement extends SettingElement {

    private final NumberSetting numberSetting;
    private boolean isListening = false;

    public NumberElement(NumberSetting numberSetting, NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        super(x, y, scrollY, posX, posY);
        this.numberSetting = numberSetting;
    }

    public NumberSetting getNumberSetting() {
        return this.numberSetting;
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

        String value = this.numberSetting.getName() + " (" + this.numberSetting.getMin() + " -> " + this.numberSetting.getMax() + ") : " + Formatting.GRAY + this.numberSetting.getValue(); //probably gonna wanna round this value too

        DrawableHelper.drawStringWithShadow(matrixStack, getMinecraft().textRenderer, value, x + 3, y + 3, new Color(255, 255 ,255 ,255).getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {

    }

    //todo
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void handleSetValue(double newValue) {
        BigDecimal bigDecimal = new BigDecimal(newValue);
        bigDecimal.setScale(this.numberSetting.getDecimal() + 1, RoundingMode.HALF_UP); //I need to test to see what this value returns

        if (bigDecimal.doubleValue() > this.numberSetting.getMax()) this.numberSetting.setValue(this.numberSetting.getMax());
        else this.numberSetting.setValue(Math.max(bigDecimal.doubleValue(), this.numberSetting.getMin()));
    }
}
