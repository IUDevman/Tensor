package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.util.ClassUtil;
import dev.tensor.misc.util.MessageUtil;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

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
        Tensor.INSTANCE.LOGGER.info("CommandManager");

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

    public void dispatchCommands(String message) {
        String[] splitMessage = message.split("\\s");

        AtomicBoolean foundMessage = new AtomicBoolean(false);

        getCommands().forEach(command -> Arrays.stream(command.getAliases()).forEach(alias -> {
            if (splitMessage[0].equalsIgnoreCase(alias)) {

                foundMessage.set(true);
                command.onCommand(splitMessage);
            }
        }));

        if (!foundMessage.get()) {
            MessageUtil.INSTANCE.sendReplaceableClientMessage("Invalid command! Type " + Formatting.YELLOW + CommandManager.INSTANCE.prefix + "commands" + Formatting.GRAY + " to see a full list of commands!", 665, true, true);
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        if (prefix.length() > 1) return;

        this.prefix = prefix;
    }
}
