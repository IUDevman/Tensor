package dev.tensor.misc.gui;

import dev.tensor.misc.imp.Category;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public final class CategoryButton implements Element {

    private final Category category;
    private int posX;
    private int posY;

    public CategoryButton(Category category, int posX, int posY) {
        this.category = category;
        this.posX = posX;
        this.posY = posY;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getName() {
        return getCategory().name();
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getWidth() {
        return 60;
    }

    @Override
    public int getHeight() {
        return 40;
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
