package dev.tensor.feature.commands;

import dev.tensor.feature.managers.CommandManager;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.util.MessageUtil;
import net.minecraft.util.Formatting;

import java.util.Arrays;

/**
 * @author IUDevman
 * @since 04-17-2021
 */

public final class Commands implements Command {

    @Override
    public String getName() {
        return "Commands";
    }

    @Override
    public String getSyntax() {
        return "{alias}";
    }

    @Override
    public String[] getAliases() {
        return new String[] {
                "commands",
                "command",
                "listcommands",
                "syntax"
        };
    }

    @Override
    public void onCommand(String[] message) {
        CommandManager.INSTANCE.getCommands().forEach(command -> {
            String syntax = command.getName() + ": " + command.getSyntax().replace("{alias}", Formatting.YELLOW + "aliases" + Formatting.GRAY);

            MessageUtil.INSTANCE.sendClientMessage(syntax, true, true);
            MessageUtil.INSTANCE.sendClientMessage(Formatting.YELLOW + "aliases: " + Formatting.GRAY + Arrays.toString(command.getAliases()), true, true);
        });
    }
}
