package dev.tensor.misc.gui.elements;

import dev.tensor.misc.gui.Element;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

public final class PropertyElement implements Element {

    private final Module module;
    private final NumberSetting x;
    private final NumberSetting y;
    private final NumberSetting scrollY;
    private final int posX;
    private final int posY;

    private final Color fillColor = new Color(0, 0, 0, 150);
    private final Color outlineColor = new Color(130, 130, 130, 150);
    private final Color enabledTextColor = new Color(0, 255, 0, 255);
    private final Color disabledTextColor = new Color(255, 0, 0, 255);

    public PropertyElement(Module module, NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.scrollY = scrollY;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public int getWidth() {
        return 204;
    }

    @Override
    public int getHeight() {
        return 45;
    }

    @Override
    public int getPosX() {
        return this.x.getValue().intValue() + this.posX;
    }

    @Override
    public int getPosY() {
        return this.y.getValue().intValue() + this.scrollY.getValue().intValue() + this.posY;
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        DrawableHelper.fill(matrixStack, x + 1, y + 1, x + this.getWidth() - 1, y + this.getHeight() - 1, this.fillColor.getRGB());
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + 1, this.outlineColor.getRGB());
        DrawableHelper.fill(matrixStack, x, y + this.getHeight() - 1, x + this.getWidth(), y + this.getHeight(), this.outlineColor.getRGB());
        DrawableHelper.fill(matrixStack, x, y + 1, x + 1, y + this.getHeight() - 1, this.outlineColor.getRGB());
        DrawableHelper.fill(matrixStack, x + this.getWidth() - 1, y + 1, x + this.getWidth(), y + this.getHeight() - 1, this.outlineColor.getRGB());

        DrawableHelper.drawCenteredTextWithShadow(matrixStack, this.getMinecraft().textRenderer, new LiteralText("Enabled").asOrderedText(), x + (this.getWidth() / 2), y + 3, module.isEnabled() ? this.enabledTextColor.getRGB() : this.disabledTextColor.getRGB());
        DrawableHelper.drawCenteredTextWithShadow(matrixStack, this.getMinecraft().textRenderer, new LiteralText("Drawn").asOrderedText(), x + (this.getWidth() / 2), y + 18, module.isDrawn() ? this.enabledTextColor.getRGB() : this.disabledTextColor.getRGB());
        DrawableHelper.drawCenteredTextWithShadow(matrixStack, this.getMinecraft().textRenderer, new LiteralText("Messages").asOrderedText(), x + (this.getWidth() / 2), y + 33, module.isMessages() ? this.enabledTextColor.getRGB() : this.disabledTextColor.getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (mouseY > this.getPosY() && mouseY < this.getPosY() + 15) {
            module.toggle();
        } else if (mouseY > this.getPosY() + 15 && mouseY < this.getPosY() + 30) {
            module.setDrawn(!module.isDrawn());
        } else if (mouseY > this.getPosY() + 30 && mouseY < this.getPosY() + 45) {
            module.setMessages(!module.isMessages());
        }
    }
}
