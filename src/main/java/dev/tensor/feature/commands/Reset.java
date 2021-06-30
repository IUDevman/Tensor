package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.Setting;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

/**
 * @author IUDevman
 * @since 06-29-2021
 */

public final class Reset implements Command {

    @Override
    public String getName() {
        return "Reset";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [module]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "reset",
                "default",
                "clear"
        };
    }

    @Override
    public int getID() {
        return 681;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No module inputted!", this.getID(), true);
            return;
        }

        String moduleName = message[1];

        Module module = Tensor.INSTANCE.MODULE_MANAGER.getModule(moduleName);

        if (module == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        module.setDrawn(true);
        module.setMessages(false);
        module.setEnabled(false);
        module.setBind(GLFW.GLFW_KEY_UNKNOWN);

        Tensor.INSTANCE.SETTING_MANAGER.getSettingsForModule(module).forEach(Setting::reset);

        this.sendReplaceableClientMessage(this.getMarker() + "Reset: " + module.getName() + "!", this.getID(), true);
    }
}
