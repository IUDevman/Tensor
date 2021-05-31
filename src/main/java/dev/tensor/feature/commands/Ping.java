package dev.tensor.feature.commands;

import dev.tensor.misc.imp.Command;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class Ping implements Command {

    @Override
    public String getName() {
        return "Ping";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [player] (case sensitive)";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "ping",
                "latency",
        };
    }

    @Override
    public int getID() {
        return 669;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No player inputted!", this.getID(), true);
            return;
        }

        String playerName = message[1];

        if (getMinecraft().getNetworkHandler() == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "No network connection!", this.getID(), true);
            return;
        }

        PlayerListEntry playerListEntry = getMinecraft().getNetworkHandler().getPlayerListEntry(playerName);

        if (playerListEntry == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid player (" + Formatting.YELLOW + playerName + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        this.sendReplaceableClientMessage(this.getMarker() + "Ping for player " + Formatting.YELLOW + playerName + Formatting.GRAY + " is " + Formatting.GREEN + playerListEntry.getLatency() + "ms" + Formatting.GRAY + "!", this.getID(), true);
    }
}
