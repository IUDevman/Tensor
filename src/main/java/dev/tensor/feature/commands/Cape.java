package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.util.Locale;

/**
 * @author IUDevman
 * @since 05-26-2021
 */

public final class Cape implements Command {

    @Override
    public String getName() {
        return "Cape";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [list/clear/add/remove] [name]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "cape",
                "capes",
                "capelist"
        };
    }

    @Override
    public int getID() {
        return 679;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No argument inputted!", this.getID(), true);
            return;
        }

        String argument = message[1];

        boolean isMissingName = message.length < 3;

        switch (argument.toLowerCase(Locale.ROOT)) {
            case "list": {
                if (Tensor.INSTANCE.CAPE_MANAGER.getCapes().size() == 0) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No caped players!", this.getID(), true);
                    return;
                }

                this.sendReplaceableClientMessage(this.getMarker() + "Caped players: " + Tensor.INSTANCE.CAPE_MANAGER.getCapes() + "!", this.getID(), true);
                break;
            }
            case "clear": {
                Tensor.INSTANCE.CAPE_MANAGER.clearCapes();
                this.sendReplaceableClientMessage(this.getMarker() + "Cleared caped players!", this.getID(), true);
                break;
            }
            case "add": {
                if (isMissingName) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No name inputted!", this.getID(), true);
                    return;
                }

                String name = message[2];

                if (Tensor.INSTANCE.CAPE_MANAGER.hasCape(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "Already a caped player (" + Formatting.YELLOW + name + Formatting.GRAY + ")!", this.getID(), true);
                    return;
                }

                Tensor.INSTANCE.CAPE_MANAGER.addCape(name);
                this.sendReplaceableClientMessage(this.getMarker() + "Added caped players (" + Formatting.GREEN + name + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
            case "remove": {
                if (isMissingName) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No name inputted!", this.getID(), true);
                    return;
                }

                String name = message[2];

                if (!Tensor.INSTANCE.CAPE_MANAGER.hasCape(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No caped players matching (" + Formatting.YELLOW + name + Formatting.GRAY + ")!", this.getID(), true);
                    return;
                }

                Tensor.INSTANCE.CAPE_MANAGER.removeCape(name);
                this.sendReplaceableClientMessage(this.getMarker() + "Removed caped player (" + Formatting.RED + name + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
            default: {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid argument (" + Formatting.YELLOW + argument + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
        }
    }
}
