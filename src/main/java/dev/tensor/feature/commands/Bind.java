package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Module;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.Locale;

/**
 * @author IUDevman
 * @since 04-27-2021
 */

public final class Bind implements Command {

    @Override
    public String getName() {
        return "Bind";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [module] [key/none]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "bind",
                "binds",
                "setbind"
        };
    }

    @Override
    public int getID() {
        return 672;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No module inputted!", this.getID(), true);
            return;
        } else if (message.length < 3) {
            this.sendReplaceableClientMessage(this.getMarker() + "No bind inputted!", this.getID(), true);
            return;
        }

        String moduleName = message[1];

        Module module = Tensor.INSTANCE.MODULE_MANAGER.getModule(moduleName);

        if (module == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        String bindName = message[2].toLowerCase(Locale.ROOT);

        try {
            int keyCode = GLFW.GLFW_KEY_UNKNOWN;

            if (!bindName.equalsIgnoreCase("none")) {
                keyCode = InputUtil.fromTranslationKey("key.keyboard." + bindName).getCode();
            }

            module.setBind(keyCode);
            this.sendReplaceableClientMessage(this.getMarker() + "Set bind for: " + module.getName() + " (" + Formatting.GREEN + bindName + Formatting.GRAY + ")!", this.getID(), true);

        } catch (IllegalArgumentException ignored) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid bind (" + Formatting.YELLOW + bindName + Formatting.GRAY + ")!", this.getID(), true);
        }
    }
}
