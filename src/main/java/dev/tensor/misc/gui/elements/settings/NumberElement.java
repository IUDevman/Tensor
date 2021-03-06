package dev.tensor.misc.gui.elements.settings;

import dev.tensor.Tensor;
import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Arrays;

/**
 * @author IUDevman
 * @since 05-04-2021
 */

public final class NumberElement extends SettingElement {

    private final NumberSetting numberSetting;
    private boolean searching = false;
    private String value = "";

    private final Color hoveredColor = new Color(30, 30, 30, 150);
    private final Color fillColor = new Color(0, 0, 0, 150);
    private final Color textColor = new Color(255, 255, 255, 255);

    public NumberElement(NumberSetting numberSetting, NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        super(x, y, scrollY, posX, posY);
        this.numberSetting = numberSetting;
    }

    public NumberSetting getNumberSetting() {
        return this.numberSetting;
    }

    public boolean isSearching() {
        return this.searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
        this.value = "";
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
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + this.getHeight(), this.isSearching() ? this.hoveredColor.getRGB() : this.fillColor.getRGB());

        String value = this.numberSetting.getName() + " (" + adjustForDecimals(this.numberSetting.getMin()) + " -> " + adjustForDecimals(this.numberSetting.getMax()) + ") : " + Formatting.GRAY + (this.isSearching() ? (this.value.equalsIgnoreCase("") ? "..." : this.value) : adjustForDecimals(this.numberSetting.getValue()));

        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, value, x + 3, y + 3, this.textColor.getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        setSearching(!isSearching());
    }

    public void onKeyPressed(int key) {
        if (!isSearching()) return;

        if (key == GLFW.GLFW_KEY_DELETE || key == GLFW.GLFW_KEY_ESCAPE) {
            setSearching(false);
            return;
        }

        if (key == GLFW.GLFW_KEY_ENTER) {

            if (this.value.equals("")) {
                setSearching(false);
                return;
            }

            try {
                double value1 = Double.parseDouble(this.value);

                if (value1 > getNumberSetting().getMax()) value1 = getNumberSetting().getMax();
                else if (value1 < getNumberSetting().getMin()) value1 = getNumberSetting().getMin();

                getNumberSetting().setValue(Double.parseDouble(this.adjustForDecimals(value1)));
                this.value = "";
                setSearching(false);

            } catch (NullPointerException | NumberFormatException ignored) {
                this.value = "";
                setSearching(false);
                return;
            }
        }

        if (Arrays.stream(Tensor.INSTANCE.GUI_MANAGER.getGUI().getAcceptedKeys()).noneMatch(value -> value == key)) {
            return;
        }

        if (key == GLFW.GLFW_KEY_BACKSPACE) {
            if (this.value.length() < 1) return;
            this.value = this.value.substring(0, this.value.length() - 1);
            return;
        }

        this.value += InputUtil.Type.KEYSYM.createFromCode(key).getTranslationKey().replace("key.keyboard.", "").replace("period", ".").replace("minus", "-");
    }

    private String adjustForDecimals(double value) {
        return String.format("%." + this.numberSetting.getDecimal() + "f", value);
    }
}
