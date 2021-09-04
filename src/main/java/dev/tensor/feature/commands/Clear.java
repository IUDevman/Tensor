package dev.tensor.feature.commands;

import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

/**
 * @author SrgantMooMoo
 * @since 08-09-2021
 */

public final class Clear implements Command {

    @Override
    public String getName() {
        return "Clear";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{name}";
    }

    @Override
    public int getID() {
        return 686;
    }

    @Override
    public void onCommand(String[] message) {
        this.getChatHud().clear(true);
        this.sendReplaceableClientMessage(this.getMarker() + "Cleared the chat!", this.getID(), true);
    }
}