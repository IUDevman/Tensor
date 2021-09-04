package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.ClickGUI;
import dev.tensor.misc.imp.Command;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Formatting;

import java.util.Locale;

/**
 * @author IUDevman
 * @since 05-19-2021
 */

public final class Help implements Command {

    @Override
    public String getName() {
        return "Help";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{name}";
    }

    @Override
    public int getID() {
        return 674;
    }

    @Override
    public void onCommand(String[] message) {
        ClickGUI clickGUI = Tensor.INSTANCE.MODULE_MANAGER.getModule(ClickGUI.class);

        if (clickGUI == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Failed to dispatch command... you're probably reloading the client!", this.getID(), true);
            return;
        }

        String bind = InputUtil.Type.KEYSYM.createFromCode(clickGUI.getBind()).getTranslationKey().replace("key.keyboard.", "").replace("unknown", "none").toUpperCase(Locale.ROOT);

        this.sendClientMessage(this.getMarker() + "Welcome to " + Tensor.INSTANCE.MOD_NAME + " (" + Formatting.GREEN + Tensor.INSTANCE.MOD_VERSION + Formatting.GRAY + "):", true);
        this.sendClientMessage("The current ClickGUI bind is " + Formatting.YELLOW + bind, true);
        this.sendClientMessage("The current command prefix is " + Formatting.YELLOW + Tensor.INSTANCE.COMMAND_MANAGER.getPrefix(), true);
        this.sendClientMessage("Use " + Formatting.YELLOW + Tensor.INSTANCE.COMMAND_MANAGER.getPrefix() + "commands" + Formatting.GRAY + " to see a list of commands", true);
    }
}
