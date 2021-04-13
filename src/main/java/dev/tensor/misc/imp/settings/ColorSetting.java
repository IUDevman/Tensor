package dev.tensor.misc.imp.settings;

import dev.tensor.misc.imp.Setting;

import java.awt.*;

public class ColorSetting implements Setting<Color> {

    private final String name;
    private Color value;
    private int opacity;

    public ColorSetting(String name, Color value, int opacity) {
        this.name = name;
        this.value = value;
        this.opacity = opacity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getValue() {
        return value;
    }

    @Override
    public void setValue(Color value) {
        this.value = value;
    }

    public int getOpacity() {
        return this.opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }
}
