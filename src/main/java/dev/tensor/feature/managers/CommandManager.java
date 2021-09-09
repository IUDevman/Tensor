package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.plugin.PluginEntryPoint;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class CommandManager implements Manager {

    private String prefix = "-";

    private final ArrayList<Command> commands = new ArrayList<>();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("CommandManager");

        this.findClassesForPath("dev.tensor.feature.commands").forEach(aClass -> {

            if (Command.class.isAssignableFrom(aClass)) {
                try {
                    Command command = (Command) aClass.getDeclaredConstructor().newInstance();
                    addCommand(command);

                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        });

        this.postSortCommands();
    }

    public void postSortCommands() {
        this.commands.sort(Comparator.comparing(Command::getName));
    }

    @PluginEntryPoint
    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }

    public Command getCommand(String name) {
        return this.commands.stream().filter(command -> command.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        if (prefix.length() > 1) return;

        this.prefix = prefix;
    }

    public void dispatchCommands(String message) {
        String[] splitMessage = message.split("\\s");

        AtomicBoolean foundMessage = new AtomicBoolean(false);

        getCommands().forEach(command -> {
            if (command.getName().equalsIgnoreCase(splitMessage[0])) {
                foundMessage.set(true);
                command.onCommand(splitMessage);
            }
        });

        if (!foundMessage.get()) {
            this.sendReplaceableClientMessage("Invalid command! Type " + Formatting.YELLOW + getPrefix() + "commands" + Formatting.GRAY + " to see a full list of commands!", 666, true);
        }
    }
}
