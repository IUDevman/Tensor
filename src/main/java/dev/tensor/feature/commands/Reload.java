package dev.tensor.feature.commands;

import dev.tensor.Tensor;
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
        return 682;
    }

    @Override
    public void onCommand(String[] message) {
        Tensor.INSTANCE.CONFIG_MANAGER.save();

        Tensor.INSTANCE.onReload();

        this.sendReplaceableClientMessage(this.getMarker() + "Reloaded client!", this.getID(), true);
    }
}
