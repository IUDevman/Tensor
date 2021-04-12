package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.util.ClassUtil;

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

        ClassUtil.findClassesForPath(commandPath).forEach(aClass -> {

            if (Command.class.isAssignableFrom(aClass)) {
                try {
                    Command command = (Command) aClass.newInstance();
                    addCommand(command);

                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addCommand(Command command) {
        this.commandArrayList.add(command);
    }

    public ArrayList<Command> getCommands() {
        return this.commandArrayList;
    }
}
