package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.feature.commands.Ping;
import dev.tensor.imp.Command;
import dev.tensor.imp.Manager;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum CommandManager implements Manager {

    INSTANCE;

    private final String commandPath = "dev.tensor.feature.commands";

    private final ArrayList<Command> commandArrayList = new ArrayList<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("CommandManager");

        addCommand(new Ping());
    }

    private void addCommand(Command command) {
        this.commandArrayList.add(command);
    }

    public ArrayList<Command> getCommands() {
        return this.commandArrayList;
    }
}
