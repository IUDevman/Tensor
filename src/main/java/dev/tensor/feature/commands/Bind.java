package dev.tensor.feature.commands;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.util.MessageUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Formatting;

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
        return "{alias} [module] [key]";
    }

    @Override
    public String[] getAliases() {
        return new String[] {
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
            MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "No module inputted!", this.getID(), true, true);
            return;
        } else if (message.length < 3) {
            MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "No bind inputted!", this.getID(), true, true);
            return;
        }

        String moduleName = message[1];

        Module module = ModuleManager.INSTANCE.getModule(moduleName);

        if (module == null) {
            MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "Invalid module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", this.getID(), true, true);
            return;
        }

        String bindName = message[2].toLowerCase(Locale.ROOT);

        try {
            int keyCode = InputUtil.fromTranslationKey("key.keyboard." + bindName).getCode();

            module.setBind(keyCode);
            MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "Set bind for: " + module.getName() + " (" + Formatting.GREEN + bindName + Formatting.GRAY + ")!", this.getID(), true, true);

        } catch (IllegalArgumentException ignored) {
            MessageUtil.INSTANCE.sendReplaceableClientMessage(this.getMarker() + "Invalid bind (" + Formatting.YELLOW + bindName + Formatting.GRAY + ")!", this.getID(), true, true);
        }
    }
}
