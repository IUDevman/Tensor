package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author IUDevman
 * @since 07-04-2021
 */

public final class Modules implements Command {

    @Override
    public String getName() {
        return "Modules";
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
        return 684;
    }

    @Override
    public void onCommand(String[] message) {
        this.sendClientMessage(this.getMarker() + "Available modules:", true);

        Arrays.stream(Category.values()).forEach(category -> {

            ArrayList<String> modules = new ArrayList<>();

            Tensor.INSTANCE.MODULE_MANAGER.getModulesInCategory(category).forEach(module -> modules.add((module.isEnabled() ? Formatting.GREEN : "") + module.getName() + Formatting.GRAY));

            int count = modules.size();

            String moduleString = modules.toString().replace("[", "").replace("]", "");

            this.sendClientMessage(Formatting.YELLOW + category.name() + Formatting.GRAY + " (" + Formatting.YELLOW + count + Formatting.GRAY + "): " + moduleString, true);
        });
    }
}
