package dev.tensor.misc.gui.elements;

import dev.tensor.misc.gui.Element;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

public final class ModuleElement implements Element {

    private PropertyElement propertyElement;
    private final ArrayList<SettingElement> settingElements = new ArrayList<>();

    private final Module module;
    private final NumberSetting x;
    private final NumberSetting y;
    private int posX;
    private int posY;
    private boolean viewed = false;

    public ModuleElement(Module module, NumberSetting x, NumberSetting y, int posX, int posY) {
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

    public PropertyElement getPropertyElement() {
        return this.propertyElement;
    }

    public void setPropertyElement(PropertyElement propertyElement) {
        this.propertyElement = propertyElement;
    }

    public ArrayList<SettingElement> getSettingElements() {
        return this.settingElements;
    }

    public void addSettingElement(SettingElement settingElement) {
        this.settingElements.add(settingElement);
    }

    public boolean isViewed() {
        return this.viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
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
    public int getPosX() {
        return this.x.getValue().intValue() + this.posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return this.y.getValue().intValue() + this.posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        DrawableHelper.fill(matrixStack, x + 1, y + 1, x + this.getWidth() - 1, y + this.getHeight() - 1, this.isViewed() ? new Color(30, 30, 30, 150).getRGB() : new Color(0, 0, 0, 150).getRGB());
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + 1, new Color(130, 130, 130, 150).getRGB()); //top
        DrawableHelper.fill(matrixStack, x, y + this.getHeight() - 1, x + this.getWidth(), y + this.getHeight(), new Color(130, 130, 130, 150).getRGB()); //bottom
        DrawableHelper.fill(matrixStack, x, y + 1, x + 1, y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB()); //left
        DrawableHelper.fill(matrixStack, x + this.getWidth() - 1, y + 1, x + this.getWidth(), y + this.getHeight() - 1, new Color(130, 130, 130, 150).getRGB()); //right

        DrawableHelper.drawCenteredString(matrixStack, getMinecraft().textRenderer, this.getName(), x + (this.getWidth() / 2), y + ((this.getHeight() - getMinecraft().textRenderer.fontHeight) / 2), module.isEnabled() ? new Color(255, 255, 0, 255).getRGB() : new Color(255, 255 ,255, 255).getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setViewed(true);
    }
}
