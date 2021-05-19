package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.feature.managers.CommandManager;
import dev.tensor.feature.managers.ModuleManager;
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
        return "{alias}";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "help",
                "info"
        };
    }

    @Override
    public int getID() {
        return 674;
    }

    @Override
    public void onCommand(String[] message) {
        ClickGUI clickGUI = ModuleManager.INSTANCE.getModule(ClickGUI.class);
        String bind = InputUtil.Type.KEYSYM.createFromCode(clickGUI.getBind()).getTranslationKey().replace("key.keyboard.", "").replace("unknown", "none").toUpperCase(Locale.ROOT);

        this.sendClientMessage(this.getMarker() + "Welcome to " + Tensor.INSTANCE.MOD_NAME + " (" + Formatting.GREEN + Tensor.INSTANCE.MOD_VERSION + Formatting.GRAY + "):", true);
        this.sendClientMessage("The current ClickGUI bind is " + Formatting.YELLOW + bind, true);
        this.sendClientMessage("The current Command bind is " + Formatting.YELLOW + CommandManager.INSTANCE.getPrefix(), true);
        this.sendClientMessage("Use " + Formatting.YELLOW + CommandManager.INSTANCE.getPrefix() + "commands" + Formatting.GRAY + " to see a list of commands", true);
    }
}
