package dev.tensor.misc.imp;

import me.zero.alpine.listener.Listenable;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public interface Manager extends Wrapper, Listenable, ClassLoader, Utilities {

    void load();
}
