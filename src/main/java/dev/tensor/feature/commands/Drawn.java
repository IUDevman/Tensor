package dev.tensor.feature.commands;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Module;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 05-21-2021
 */

public final class Drawn implements Command {

    @Override
    public String getName() {
        return "Drawn";
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
                "drawn",
                "setdrawn"
        };
    }

    @Override
    public int getID() {
        return 677;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No module inputted!", this.getID(), true);
            return;
        }

        String moduleName = message[1];

        Module module = ModuleManager.INSTANCE.getModule(moduleName);

        if (module == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        module.setDrawn(!module.isDrawn());

        String value = module.isMessages() ? Formatting.GREEN + "true" : Formatting.RED + "false";
        this.sendReplaceableClientMessage(this.getMarker() + "Set drawn: " + module.getName() + " (" + value + Formatting.GRAY + ")!", this.getID(), true);
    }
}
