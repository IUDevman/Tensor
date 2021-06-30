package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Module;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 04-17-2021
 */

public final class Messages implements Command {

    @Override
    public String getName() {
        return "Messages";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [module]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "messages",
                "message",
                "msgs",
                "msg"
        };
    }

    @Override
    public int getID() {
        return 670;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No module inputted!", this.getID(), true);
            return;
        }

        String moduleName = message[1];

        Module module = Tensor.INSTANCE.MODULE_MANAGER.getModule(moduleName);

        if (module == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        module.setMessages(!module.isMessages());

        String value = module.isMessages() ? Formatting.GREEN + "true" : Formatting.RED + "false";
        this.sendReplaceableClientMessage(this.getMarker() + "Toggled messages: " + module.getName() + " (" + value + Formatting.GRAY + ")!", this.getID(), true);
    }
}
