package dev.tensor.misc.imp;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public interface Command extends Wrapper {

    String getName();

    String getSyntax();

    String[] getAliases();

    void onCommand(String[] message);
}
