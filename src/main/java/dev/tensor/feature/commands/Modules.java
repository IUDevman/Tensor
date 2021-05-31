package dev.tensor.feature.commands;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

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
        return "{alias}";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "modules",
                "modulelist",
                "listmodules"
        };
    }

    @Override
    public int getID() {
        return 681;
    }

    @Override
    public void onCommand(String[] message) {
        this.sendClientMessage(this.getMarker() + "Modules:", true);

        ModuleManager.INSTANCE.getModules().forEach(module -> {
        	String syntax;
        	if(module.isEnabled()) {
        		syntax = module.getName() + ": " + Formatting.GREEN + "(enabled)";
        	}else {
        		syntax = module.getName() + ": " + Formatting.RED + "(disabled)";
        	}

            this.sendClientMessage(syntax, true);
        });
    }
}
