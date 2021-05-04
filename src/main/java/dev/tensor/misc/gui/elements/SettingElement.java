package dev.tensor.misc.gui.elements;

import dev.tensor.misc.gui.Element;
import dev.tensor.misc.imp.settings.NumberSetting;

public abstract class SettingElement implements Element {

    private final NumberSetting x;
    private final NumberSetting y;
    private int posX;
    private int posY;

    public SettingElement(NumberSetting x, NumberSetting y, int posX, int posY) {
        this.x = x;
        this.y = y;
        this.posX = posX;
        this.posY = posY;
    }

    public NumberSetting getX() {
        return this.x;
    }

    public NumberSetting getY() {
        return this.y;
    }

    public int getXPos() {
        return this.posX;
    }

    public void setXPos(int posX) {
        this.posX = posX;
    }

    public int getYPos() {
        return this.posY;
    }

    public void setYPos(int posY) {
        this.posY = posY;
    }
}
