package dev.tensor.misc.gui.elements;

import dev.tensor.misc.gui.Element;
import dev.tensor.misc.imp.settings.NumberSetting;

/**
 * @author IUDevman
 * @since 05-04-2021
 */

public abstract class SettingElement implements Element {

    private final NumberSetting x;
    private final NumberSetting y;
    private final NumberSetting scrollY;
    private final int posX;
    private final int posY;

    public SettingElement(NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        this.x = x;
        this.y = y;
        this.scrollY = scrollY;
        this.posX = posX;
        this.posY = posY;
    }

    public NumberSetting getX() {
        return this.x;
    }

    public NumberSetting getY() {
        return this.y;
    }

    public NumberSetting getScrollY() {
        return this.scrollY;
    }

    public int getXPos() {
        return this.posX;
    }

    public int getYPos() {
        return this.posY;
    }
}
