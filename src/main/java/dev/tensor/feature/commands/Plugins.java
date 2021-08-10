package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 08-06-2021
 */

public final class Plugins implements Command {

    @Override
    public String getName() {
        return "Plugins";
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
                "plugins",
                "plugin"
        };
    }

    @Override
    public int getID() {
        return 685;
    }

    @Override
    public void onCommand(String[] message) {
        this.sendClientMessage(this.getMarker() + "Available modules:", true);

        Tensor.INSTANCE.PLUGIN_MANAGER.getPlugins().forEach(plugin -> this.sendClientMessage(this.getMarker() + plugin.getName() + ", version " + Formatting.YELLOW + plugin.getVersion(), true));
    }
}
