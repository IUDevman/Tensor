package dev.tensor.feature.commands;

import dev.tensor.misc.imp.Command;
import dev.tensor.misc.util.MessageUtil;
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
        return "{alias}";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "ping",
                "testping",
                "pingpong"
        };
    }

    @Override
    public int getID() {
        return 669;
    }

    @Override
    public void onCommand(String[] message) {
        MessageUtil.INSTANCE.sendClientMessage(this.getMarker() + "Pong", true, true);
    }
}
