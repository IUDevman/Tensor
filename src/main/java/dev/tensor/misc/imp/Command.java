package dev.tensor.misc.imp;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public interface Command extends Global, Utilities {

    String getName();

    String getMarker();

    String getSyntax();

    String[] getAliases();

    int getID();

    void onCommand(String[] message);
}
