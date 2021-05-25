package dev.tensor.feature.commands;

import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 05-25-2021
 */

public final class VClip implements Command {

    @Override
    public String getName() {
        return "VClip";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [value]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "vclip",
                "up",
                "yclip"
        };
    }

    @Override
    public int getID() {
        return 678;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No value inputted!", this.getID(), true);
            return;
        }

        String value = message[1];

        try {
            int clipAmount = Integer.parseInt(value);

            getPlayer().updatePosition(getPlayer().getX(), getPlayer().getY() + clipAmount, getPlayer().getZ());
            this.sendReplaceableClientMessage(this.getMarker() + "Attempted to y-teleport (" + Formatting.GREEN + clipAmount + Formatting.GRAY + ") blocks!", this.getID(), true);

        } catch (NullPointerException | NumberFormatException ignored) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid value (" + Formatting.RED + value + Formatting.GRAY + ")!", this.getID(), true);
        }
    }
}
