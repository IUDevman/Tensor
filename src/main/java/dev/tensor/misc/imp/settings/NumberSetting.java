package dev.tensor.misc.imp.settings;

import dev.tensor.misc.imp.Setting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class NumberSetting implements Setting<Double>, com.lukflug.panelstudio.settings.NumberSetting {

    private final String name;
    private double value;
    private final double min;
    private final double max;
    private final double step;
    private final int decimal;

    public NumberSetting(String name, double value, double min, double max, double step, int decimal) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
        this.decimal = decimal;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public double getNumber() {
        return getValue();
    }

    @Override
    public void setNumber(double value) {
        setValue(value);
    }

    @Override
    public double getMaximumValue() {
        return this.max;
    }

    @Override
    public double getMinimumValue() {
        return this.min;
    }

    @Override
    public int getPrecision() {
        return this.decimal;
    }

    public double getStep() {
        return this.step;
    }
}
