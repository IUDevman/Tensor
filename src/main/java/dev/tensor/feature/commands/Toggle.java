package dev.tensor.feature.commands;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.util.MessageUtil;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 04-17-2021
 */

public final class Toggle implements Command {

    @Override
    public String getName() {
        return "Toggle";
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
        return new String[] {
                "toggle",
                "enable",
                "disable",
                "togglemodule"
        };
    }

    @Override
    public int getID() {
        return 667;
    }

    @Override
    public void onCommand(String[] message) {

        if (message == null || message.length < 2) {
            MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "No module inputted!", this.getID(), true, true);
            return;
        }

        String moduleName = message[1];

        Module module = ModuleManager.INSTANCE.getModule(moduleName);

        if (module == null) {
            MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "Invalid module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", this.getID(), true, true);
            return;
        }

        module.toggle();

        String value = module.isEnabled() ? Formatting.GREEN + "true" : Formatting.RED + "false";
        MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "Toggled: " + module.getName() + " (" + value + Formatting.GRAY + ")!", this.getID(), true, true);
    }
}
