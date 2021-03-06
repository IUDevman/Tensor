package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 05-28-2021
 */

public final class Spammer implements Command {

    @Override
    public String getName() {
        return "Spammer";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{name} [file.txt]";
    }

    @Override
    public int getID() {
        return 680;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No file inputted!", this.getID(), true);
            return;
        }

        String file = message[1];

        Path path = Paths.get(Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/spammer/" + file);

        if (!Files.exists(path)) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid file (" + Formatting.RED + file + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        this.sendReplaceableClientMessage(this.getMarker() + "Set spammer file (" + Formatting.GREEN + file + Formatting.GRAY + ")!", this.getID(), true);

        dev.tensor.feature.modules.Spammer spammer = Tensor.INSTANCE.MODULE_MANAGER.getModule(dev.tensor.feature.modules.Spammer.class);

        if (spammer == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Failed to set spammer file path!", this.getID(), true);
            return;
        }

        spammer.setPath(path);
    }
}
