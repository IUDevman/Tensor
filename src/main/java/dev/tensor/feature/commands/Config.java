package dev.tensor.feature.commands;

import dev.tensor.feature.managers.ConfigManager;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.util.Locale;

/**
 * @author IUDevman
 * @since 05-19-2021
 */

public final class Config implements Command {

    @Override
    public String getName() {
        return "Config";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [save/reload]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "config",
                "configuration",
                "file"
        };
    }

    @Override
    public int getID() {
        return 673;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No argument inputted!", this.getID(), true);
            return;
        }

        String argument = message[1];

        switch (argument.toLowerCase(Locale.ROOT)) {
            case "save": {
                ConfigManager.INSTANCE.save();
                this.sendReplaceableClientMessage(this.getMarker() + "Saved config!", this.getID(), true);
                break;
            }
            case "reload": {
                ConfigManager.INSTANCE.load();
                this.sendReplaceableClientMessage(this.getMarker() + "Reloaded config!", this.getID(), true);
                break;
            }
            default: {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid argument (" + Formatting.YELLOW + argument + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
        }
    }
}
