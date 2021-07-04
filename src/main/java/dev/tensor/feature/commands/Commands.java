package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
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
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias}";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "commands",
                "command",
                "listcommands",
                "syntax"
        };
    }

    @Override
    public int getID() {
        return 671;
    }

    @Override
    public void onCommand(String[] message) {
        this.sendClientMessage(this.getMarker() + "Available commands:", true);

        Tensor.INSTANCE.COMMAND_MANAGER.getCommands().forEach(command -> {
            String syntax = command.getName() + ": " + command.getSyntax().replace("{alias}", Formatting.YELLOW + "aliases" + Formatting.GRAY);

            this.sendClientMessage(syntax, true);
            this.sendClientMessage(Formatting.YELLOW + "Aliases: " + Formatting.GRAY + Arrays.toString(command.getAliases()), true);
        });
    }
}
