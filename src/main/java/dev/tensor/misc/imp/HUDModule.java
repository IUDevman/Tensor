package dev.tensor.misc.imp;

import java.awt.*;

/**
 * @author IUDevman
 * @since 04-13-2021
 */

public interface HUDModule {

    void render();

    Point getStartingPoint();

    int getWidth();

    int getHeight();
}
