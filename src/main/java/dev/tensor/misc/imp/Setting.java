package dev.tensor.misc.imp;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public interface Setting<T> {

    String getName();

    T getDefaultValue();

    T getValue();

    void setValue(T value);

    default void reset() {
        setValue(getDefaultValue());
    }
}
