package dev.tensor.misc.imp.settings;

import com.lukflug.panelstudio.settings.Toggleable;
import dev.tensor.misc.imp.Setting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class BooleanSetting implements Setting<Boolean>, Toggleable {

    private final String name;
    private boolean value;

    public BooleanSetting(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public void toggle() {
        setValue(!getValue());
    }

    @Override
    public boolean isOn() {
        return getValue();
    }
}
