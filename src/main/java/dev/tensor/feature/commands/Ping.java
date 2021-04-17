package dev.tensor.feature.commands;

import dev.tensor.misc.imp.Command;
import dev.tensor.misc.util.MessageUtil;

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
    public String getSyntax() {
        return "{alias}";
    }

    @Override
    public String[] getAliases() {
        return new String[] {
                "ping",
                "testping",
                "pingpong"
        };
    }

    @Override
    public void onCommand(String[] message) {
        MessageUtil.INSTANCE.sendClientMessage("Pong", true, true);
    }
}
