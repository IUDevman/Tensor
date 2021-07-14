package dev.tensor.misc.imp;

import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum Category {

    Combat(Formatting.RED),
    Movement(Formatting.BLUE),
    Player(Formatting.GOLD),
    Render(Formatting.GREEN),
    Misc(Formatting.YELLOW),
    HUD(Formatting.LIGHT_PURPLE),
    Client(Formatting.WHITE);

    private final Formatting formatting;

    Category(Formatting formatting) {
        this.formatting = formatting;
    }

    public Formatting getFormatting() {
        return this.formatting;
    }
}
