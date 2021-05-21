package dev.tensor.feature.commands;

import dev.tensor.feature.managers.CommandManager;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 04-17-2021
 */

public final class Prefix implements Command {

    @Override
    public String getName() {
        return "Prefix";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [char]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "prefix",
                "setprefix",
                "bindprefix",
                "commandprefix"
        };
    }

    @Override
    public int getID() {
        return 668;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No prefix inputted!", this.getID(), true);
            return;
        }

        String prefix = message[1];

        if (prefix.length() > 1) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid prefix (" + Formatting.YELLOW + prefix + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        CommandManager.INSTANCE.setPrefix(prefix);
        this.sendReplaceableClientMessage(this.getMarker() + "Set command prefix to (" + Formatting.GREEN + prefix + Formatting.GRAY + ")!", this.getID(), true);
    }
}
