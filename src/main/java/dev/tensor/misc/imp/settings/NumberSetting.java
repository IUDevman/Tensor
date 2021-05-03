package dev.tensor.misc.imp.settings;

import dev.tensor.misc.imp.Setting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class NumberSetting implements Setting<Double> {

    private final String name;
    private double value;
    private final double min;
    private final double max;
    private final int decimal;

    public NumberSetting(String name, double value, double min, double max, int decimal) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
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

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public int getDecimal() {
        return this.decimal;
    }
}
