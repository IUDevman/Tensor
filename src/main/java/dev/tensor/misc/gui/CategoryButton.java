package dev.tensor.misc.gui;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public final class CategoryButton implements Element {

    private final Category category;
    private final NumberSetting x;
    private final NumberSetting y;
    private int posX;
    private int posY;
    private boolean selected = false;

    public CategoryButton(Category category, NumberSetting x, NumberSetting y, int posX, int posY) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.posX = posX;
        this.posY = posY;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getName() {
        return this.getCategory().name();
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

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
