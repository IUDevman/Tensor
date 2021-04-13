package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.util.ClassUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum CommandManager implements Manager {

    INSTANCE;

    private String prefix = "-";

    private final ArrayList<Command> commandArrayList = new ArrayList<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("CommandManager");

        ClassUtil.INSTANCE.findClassesForPath("dev.tensor.feature.commands").forEach(aClass -> {

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

    public String getPrefix() {
        return this.prefix;
    }

    public void dispatchCommands(String message) {
        String[] splitMessage = message.split("\\s");

        getCommands().forEach(command -> Arrays.stream(command.getAliases()).forEach(alias -> {
            if (splitMessage[0].equalsIgnoreCase(alias)) {

                command.onCommand(message.replace(alias, "").split("\\s"));
            }
        }));
    }
}
