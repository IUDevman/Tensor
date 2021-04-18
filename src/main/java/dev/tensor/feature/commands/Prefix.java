package dev.tensor.feature.commands;

import dev.tensor.feature.managers.CommandManager;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.util.MessageUtil;
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
    public String getSyntax() {
        return "{alias} [char]";
    }

    @Override
    public String[] getAliases() {
        return new String[] {
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
            MessageUtil.INSTANCE.sendReplaceableClientMessage("(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") No prefix inputted!", this.getID(), true, true);
            return;
        }

        String prefix = message[1];

        if (prefix.length() > 1) {
            MessageUtil.INSTANCE.sendReplaceableClientMessage("(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") Invalid prefix (" + Formatting.YELLOW + prefix + Formatting.GRAY + ")!", this.getID(), true, true);
            return;
        }

        CommandManager.INSTANCE.setPrefix(prefix);
        MessageUtil.INSTANCE.sendReplaceableClientMessage("(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") Set command prefix to (" + Formatting.GREEN + prefix + Formatting.GRAY +")!", this.getID(), true, true);
    }
}
