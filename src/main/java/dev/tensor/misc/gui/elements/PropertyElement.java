package dev.tensor.misc.gui.elements;

import dev.tensor.misc.gui.Element;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

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
        DrawableHelper.fill(matrixStack, x + 1, y + 1, x + this.getWidth() - 1, y + this.getHeight() - 1, new Color(0, 0, 0, 150).getRGB());
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + 1, new Color(130, 130, 130, 150).getRGB()); //top
        DrawableHelper.fill(matrixStack, x, y + this.getHeight() - 1, x + this.getWidth(), y + this.getHeight(), new Color(130, 130, 130, 150).getRGB()); //bottom
        DrawableHelper.fill(matrixStack, x, y + 1, x + 1, y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB()); //left
        DrawableHelper.fill(matrixStack, x + this.getWidth() - 1, y + 1, x + this.getWidth(), y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB()); //right

        DrawableHelper.drawCenteredString(matrixStack, getMinecraft().textRenderer, "Enabled", x + (this.getWidth() / 2), y + 3, module.isEnabled() ? new Color(0, 255, 0, 255).getRGB() : new Color(255, 0, 0, 255).getRGB());
        DrawableHelper.drawCenteredString(matrixStack, getMinecraft().textRenderer, "Drawn", x + (this.getWidth() / 2), y + 18, module.isDrawn() ? new Color(0, 255, 0, 255).getRGB() : new Color(255, 0, 0, 255).getRGB());
        DrawableHelper.drawCenteredString(matrixStack, getMinecraft().textRenderer, "Messages", x + (this.getWidth() / 2), y + 33, module.isMessages() ? new Color(0, 255, 0, 255).getRGB() : new Color(255, 0, 0, 255).getRGB());
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
