package dev.tensor.misc.imp;

import java.awt.*;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum Category {

    Combat(Color.RED),
    Movement(Color.CYAN),
    Player(Color.ORANGE),
    Render(Color.GREEN),
    Misc(Color.YELLOW),
    HUD(Color.MAGENTA),
    Client(Color.WHITE);

    private final Color color;

    Category(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
