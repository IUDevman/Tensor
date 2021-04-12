package dev.tensor.misc.imp;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public interface Setting<T> {

    String getName();

    T getValue();

    void setValue(T value);
}
