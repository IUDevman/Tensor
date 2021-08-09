package dev.tensor.misc.imp;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@FunctionalInterface
public interface Manager extends Global, ClassLoader, Methods {

    void load();
}
