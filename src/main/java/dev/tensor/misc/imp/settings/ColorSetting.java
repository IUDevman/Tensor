package dev.tensor.misc.imp.settings;

import dev.tensor.misc.imp.Setting;

import java.awt.*;

public final class ColorSetting implements Setting<Color> {

    private final String name;
    private Color value;

    public ColorSetting(String name, Color value) {
        this.name = name;
        this.value = value;
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

    public void setOpacity(int opacity) {
        setValue(new Color(getValue().getRed(), getValue().getGreen(), getValue().getBlue(), opacity)); //todo: double check if this works
    }
}
