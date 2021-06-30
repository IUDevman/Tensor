package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.event.EventHandler;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 06-30-2021
 */

public final class Reload implements Command {

    @Override
    public String getName() {
        return "Reload";
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
                "reload",
                "restart"
        };
    }

    @Override
    public int getID() {
        return 681;
    }

    @Override
    public void onCommand(String[] message) {
        Tensor.INSTANCE.CONFIG_MANAGER.save();

        EventHandler.unregister(Tensor.INSTANCE.COMMAND_MANAGER);
        EventHandler.unregister(Tensor.INSTANCE.MODULE_MANAGER);
        EventHandler.unregister(Tensor.INSTANCE.SETTING_MANAGER);
        EventHandler.unregister(Tensor.INSTANCE.EVENT_MANAGER);
        EventHandler.unregister(Tensor.INSTANCE.CAPE_MANAGER);
        EventHandler.unregister(Tensor.INSTANCE.FRIEND_MANAGER);
        EventHandler.unregister(Tensor.INSTANCE.GUI_MANAGER);
        EventHandler.unregister(Tensor.INSTANCE.CONFIG_MANAGER);

        Tensor.INSTANCE.onInitialize();
        this.sendReplaceableClientMessage(this.getMarker() + "Reloaded client!", this.getID(), true);
    }
}
