package dev.tensor.misc.imp.settings;

import dev.tensor.misc.imp.Setting;

import java.awt.*;

/**
 * @author IUDevman
 * @since 04-15-2021
 */

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
}
