package dev.tensor.misc.gui.elements.settings;

import dev.tensor.Tensor;
import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.imp.settings.ColorSetting;
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

public final class ColorElement extends SettingElement {

    private final ColorSetting colorSetting;
    private boolean searchingR = false;
    private boolean searchingG = false;
    private boolean searchingB = false;
    private String value = "";

    private final Color hoveredColor = new Color(30, 30, 30, 150);
    private final Color fillColor = new Color(0, 0, 0, 150);
    private final Color textColor = new Color(255, 255, 255, 255);

    public ColorElement(ColorSetting colorSetting, NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        super(x, y, scrollY, posX, posY);
        this.colorSetting = colorSetting;
    }

    public ColorSetting getColorSetting() {
        return this.colorSetting;
    }

    public void clearSearching() {
        this.searchingR = false;
        this.searchingG = false;
        this.searchingB = false;
        this.value = "";
    }

    @Override
    public int getWidth() {
        return 204;
    }

    @Override
    public int getHeight() {
        return 60;
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
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + 15, this.fillColor.getRGB());
        DrawableHelper.fill(matrixStack, x, y + 15, x + this.getWidth(), y + 30, this.searchingR ? this.hoveredColor.getRGB() : this.fillColor.getRGB());
        DrawableHelper.fill(matrixStack, x, y + 30, x + this.getWidth(), y + 45, this.searchingG ? this.hoveredColor.getRGB() : this.fillColor.getRGB());
        DrawableHelper.fill(matrixStack, x, y + 45, x + this.getWidth(), y + 60, this.searchingB ? this.hoveredColor.getRGB() : this.fillColor.getRGB());

        String name = getColorSetting().getName() + " : ";
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, name, x + 3, y + 3, this.textColor.getRGB());

        String rgb = "(" + getColorSetting().getValue().getRed() + ", " + getColorSetting().getValue().getGreen() + ", " + getColorSetting().getValue().getBlue() + ")";
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, rgb, x + 3 + this.getMinecraft().textRenderer.getWidth(name), y + 3, getColorSetting().getValue().getRGB());

        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, "  > Red (0 -> 255) : " + Formatting.GRAY + (this.searchingR ? (this.value.equalsIgnoreCase("") ? "..." : this.value) : getColorSetting().getValue().getRed()), x + 3, y + 18, this.textColor.getRGB());
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, "  > Green (0 -> 255) : " + Formatting.GRAY + (this.searchingG ? (this.value.equalsIgnoreCase("") ? "..." : this.value) : getColorSetting().getValue().getGreen()), x + 3, y + 33, this.textColor.getRGB());
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, "  > Blue (0 -> 255) : " + Formatting.GRAY + (this.searchingB ? (this.value.equalsIgnoreCase("") ? "..." : this.value) : getColorSetting().getValue().getBlue()), x + 3, y + 48, this.textColor.getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (mouseY > getPosY() + 15 && mouseY <= getPosY() + 30) {
            this.searchingR = !this.searchingR;
            this.searchingG = false;
            this.searchingB = false;
        } else if (mouseY > getPosY() + 30 && mouseY <= getPosY() + 45) {
            this.searchingR = false;
            this.searchingG = !this.searchingG;
            this.searchingB = false;
        } else if (mouseY > getPosY() + 45 && mouseY <= getPosY() + 60) {
            this.searchingR = false;
            this.searchingG = false;
            this.searchingB = !this.searchingB;
        } else {
            clearSearching();
        }
    }

    public void onKeyPressed(int key) {
        if (!this.searchingR && !this.searchingG && !this.searchingB) return;

        if (key == GLFW.GLFW_KEY_DELETE || key == GLFW.GLFW_KEY_ESCAPE) {
            clearSearching();
            return;
        }

        if (key == GLFW.GLFW_KEY_ENTER) {

            if (this.value.equals("")) {
                clearSearching();
                return;
            }

            int value1 = Integer.parseInt(this.value);

            if (value1 > 255) value1 = 255;
            else if (value1 < 0) value1 = 0;

            setValue(value1);

            this.value = "";
            clearSearching();
        }

        if (Arrays.stream(Tensor.INSTANCE.GUI_MANAGER.getGUI().getAcceptedKeys()).noneMatch(value -> value == key)) {
            return;
        }

        if (key == GLFW.GLFW_KEY_PERIOD || key == GLFW.GLFW_KEY_MINUS) return;

        if (key == GLFW.GLFW_KEY_BACKSPACE) {
            if (this.value.length() < 1) return;
            this.value = this.value.substring(0, this.value.length() - 1);
            return;
        }

        this.value += InputUtil.Type.KEYSYM.createFromCode(key).getTranslationKey().replace("key.keyboard.", "");
    }

    private void setValue(int value) {
        Color oldColor = getColorSetting().getValue();

        if (this.searchingR) {
            getColorSetting().setValue(new Color(value, oldColor.getGreen(), oldColor.getBlue(), oldColor.getAlpha()));
        } else if (this.searchingG) {
            getColorSetting().setValue(new Color(oldColor.getRed(), value, oldColor.getBlue(), oldColor.getAlpha()));
        } else if (this.searchingB) {
            getColorSetting().setValue(new Color(oldColor.getRed(), oldColor.getGreen(), value, oldColor.getAlpha()));
        }
    }
}
