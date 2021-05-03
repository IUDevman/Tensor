package dev.tensor.misc.gui;

import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public final class ModuleButton implements Element {

    private final Module module;
    private final NumberSetting x;
    private final NumberSetting y;
    private int posX;
    private int posY;

    public ModuleButton(Module module, NumberSetting x, NumberSetting y, int posX, int posY) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.posX = posX;
        this.posY = posY;
    }

    public Module getModule() {
        return this.module;
    }

    public String getName() {
        return this.getModule().getName();
    }

    public int getPosX() {
        return this.posX + this.x.getValue().intValue();
    }

    public int getPosY() {
        return this.posY + this.y.getValue().intValue();
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        DrawableHelper.fill(matrixStack, x + 1, y + 1, x + this.getWidth() - 1, y + this.getHeight() - 1, new Color(0, 0, 0, 150).getRGB());
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + 1, new Color(130, 130, 130, 150).getRGB()); //top
        DrawableHelper.fill(matrixStack, x, y + this.getHeight() - 1, x + this.getWidth(), y + this.getHeight(), new Color(130, 130, 130, 150).getRGB()); //bottom
        DrawableHelper.fill(matrixStack, x, y + 1, x + 1, y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB()); //left
        DrawableHelper.fill(matrixStack, x + this.getWidth() - 1, y + 1, x + this.getWidth(), y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB()); //right

        DrawableHelper.drawCenteredString(matrixStack, getMinecraft().textRenderer, this.getName(), x + (this.getWidth() / 2), y + ((this.getHeight() - getMinecraft().textRenderer.fontHeight) / 2), new Color(255, 255 ,255, 255).getRGB());
    }
}
