package dev.tensor.misc.imp.settings;

import dev.tensor.misc.imp.Setting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public class EnumSetting implements Setting<Enum<?>> {

    private final String name;
    private Enum<?> value;

    public EnumSetting(String name, Enum<?> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Enum<?> getValue() {
        return this.value;
    }

    @Override
    public void setValue(Enum<?> value) {
        this.value = value;
    }
}
