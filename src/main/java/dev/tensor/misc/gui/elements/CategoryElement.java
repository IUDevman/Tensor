package dev.tensor.misc.gui.elements;

import dev.tensor.misc.gui.Element;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

public final class CategoryElement implements Element {

    private final ArrayList<ModuleElement> moduleElements = new ArrayList<>();

    private final Category category;
    private final NumberSetting x;
    private final NumberSetting y;
    private final int posX;
    private final int posY;
    private boolean selected = false;

    public CategoryElement(Category category, NumberSetting x, NumberSetting y, int posX, int posY) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.posX = posX;
        this.posY = posY;
    }

    public ArrayList<ModuleElement> getModuleElements() {
        return this.moduleElements;
    }

    public void addModuleElement(ModuleElement moduleElement) {
        this.moduleElements.add(moduleElement);
    }

    public Category getCategory() {
        return this.category;
    }

    public String getName() {
        return this.getCategory().name();
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
    public int getPosX() {
        return this.x.getValue().intValue() + this.posX;
    }

    @Override
    public int getPosY() {
        return this.y.getValue().intValue() + this.posY;
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        DrawableHelper.fill(matrixStack, x + 1, y + 1, x + this.getWidth() - 1, y + this.getHeight() - 1, this.isSelected() ? new Color(30, 30, 30, 150).getRGB() : new Color(0, 0, 0, 150).getRGB());
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + 1, new Color(130, 130, 130, 150).getRGB());
        DrawableHelper.fill(matrixStack, x, y + this.getHeight() - 1, x + this.getWidth(), y + this.getHeight(), new Color(130, 130, 130, 150).getRGB());
        DrawableHelper.fill(matrixStack, x, y + 1, x + 1, y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB());
        DrawableHelper.fill(matrixStack, x + this.getWidth() - 1, y + 1, x + this.getWidth(), y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB());

        DrawableHelper.drawCenteredTextWithShadow(matrixStack, this.getMinecraft().textRenderer, new LiteralText(this.getName()).asOrderedText(), x + (this.getWidth() / 2), y + ((this.getHeight() - getMinecraft().textRenderer.fontHeight) / 2), this.isSelected() ? new Color(255, 255, 0, 255).getRGB() : new Color(255, 255, 255, 255).getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        if (button == 0) this.setSelected(true);
    }
}
