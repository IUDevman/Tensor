package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
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
        return "{alias} [page]";
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
        return 664;
    }

    @Override
    public void onCommand(String[] message) {
        ArrayList<Command> commands = Tensor.INSTANCE.COMMAND_MANAGER.getCommands();

        int commandPages = (int) Math.ceil((double) commands.size() / 4);

        if (commandPages == 0) commandPages = 1;

        int startingPage = 1;

        if (message != null && message.length >= 2) {
            try {
                startingPage = Integer.parseInt(message[1]);

            } catch (NumberFormatException ignored) {
                
            }
        }

        if (startingPage <= 0 || startingPage > commandPages) startingPage = 1;

        this.sendReplaceableClientMessage(this.getMarker() + "Available commands (" + Formatting.GREEN + commands.size() + Formatting.GRAY + " total, page " + Formatting.YELLOW + startingPage + "/" + commandPages + Formatting.GRAY + "):", this.getID(), true);

        int count = 0;

        for (int i = (startingPage * 4) - 4; i < startingPage * 4; i++) {
            count += 1;

            if (i >= commands.size()) {
                this.removeReplaceableClientMessage(this.getID() - count);
                this.removeReplaceableClientMessage(this.getID() - (count * 5));
                continue;
            }

            Command command = commands.get(i);

            String syntax = command.getName() + ": " + command.getSyntax().replace("{alias}", Formatting.YELLOW + "aliases" + Formatting.GRAY);

            this.sendReplaceableClientMessage(syntax, this.getID() - count, true);
            this.sendReplaceableClientMessage(Formatting.YELLOW + "Aliases: " + Formatting.GRAY + Arrays.toString(command.getAliases()), this.getID() - (count * 5), true);
        }
    }
}
