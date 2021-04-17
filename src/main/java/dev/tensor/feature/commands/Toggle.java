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
    public void onCommand(String[] message) {

        if (message == null || message.length < 2) {
            MessageUtil.INSTANCE.sendClientMessage("No module inputted!", true, true);
            return;
        }

        String moduleName = message[1];

        Module module = ModuleManager.INSTANCE.getModule(moduleName);

        if (module == null) {
            MessageUtil.INSTANCE.sendClientMessage("Invalid Module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", true, true);
            return;
        }

        module.toggle();

        String value = module.isEnabled() ? Formatting.GREEN + "true" : Formatting.RED + "false";
        MessageUtil.INSTANCE.sendClientMessage("Toggled " + module.getName() + " (" + value + Formatting.GRAY + ")!", true, true);
    }
}
