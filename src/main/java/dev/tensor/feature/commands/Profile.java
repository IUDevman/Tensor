package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 07-13-2021
 */

public final class Profile implements Command {

    @Override
    public String getName() {
        return "Profile";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [current/list/clear/add/remove/set] [name]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "profile",
                "profiles",
                "setprofile"
        };
    }

    @Override
    public int getID() {
        return 683;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No argument inputted!", this.getID(), true);
            return;
        }

        String argument = message[1];

        boolean isMissingName = message.length < 3;

        switch (argument.toLowerCase(Locale.ROOT)) {
            case "current": {
                this.sendReplaceableClientMessage(this.getMarker() + "Current profile: " + Tensor.INSTANCE.CONFIG_MANAGER.getCurrentProfile().getName() + "!", this.getID(), true);
                break;
            }
            case "list": {
                ArrayList<String> profileNames = new ArrayList<>();

                Tensor.INSTANCE.CONFIG_MANAGER.getProfiles().forEach(profile -> profileNames.add(profile.getName()));

                this.sendReplaceableClientMessage(this.getMarker() + "Profiles: " + profileNames + "!", this.getID(), true);
                break;
            }
            case "clear": {
                Tensor.INSTANCE.CONFIG_MANAGER.getProfiles().forEach(profile -> Tensor.INSTANCE.CONFIG_MANAGER.removeProfile(profile.getName()));
                this.sendReplaceableClientMessage(this.getMarker() + "Cleared profiles!", this.getID(), true);
                break;
            }
            case "add": {
                if (isMissingName) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No name inputted!", this.getID(), true);
                    return;
                }

                String name = message[2];

                if (Tensor.INSTANCE.CONFIG_MANAGER.containsProfile(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "Already a profile (" + Formatting.YELLOW + name + Formatting.GRAY + ")!", this.getID(), true);
                    return;
                }

                Tensor.INSTANCE.CONFIG_MANAGER.addProfile(name);
                this.sendReplaceableClientMessage(this.getMarker() + "Added profile (" + Formatting.GREEN + name + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
            case "remove": {
                if (isMissingName) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No name inputted!", this.getID(), true);
                    return;
                }

                String name = message[2];

                if (!Tensor.INSTANCE.CONFIG_MANAGER.containsProfile(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No profiles matching (" + Formatting.YELLOW + name + Formatting.GRAY + ")!", this.getID(), true);
                    return;
                }

                if (Tensor.INSTANCE.CONFIG_MANAGER.getCurrentProfile().getName().equalsIgnoreCase(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "Cannot delete the current profile!", this.getID(), true);
                    return;
                }

                Tensor.INSTANCE.CONFIG_MANAGER.removeProfile(name);
                this.sendReplaceableClientMessage(this.getMarker() + "Removed profile (" + Formatting.RED + name + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
            case "set": {
                if (isMissingName) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No name inputted!", this.getID(), true);
                    return;
                }

                String name = message[2];

                if (!Tensor.INSTANCE.CONFIG_MANAGER.containsProfile(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No profiles matching (" + Formatting.YELLOW + name + Formatting.GRAY + ")!", this.getID(), true);
                    return;
                }

                Tensor.INSTANCE.CONFIG_MANAGER.setCurrentProfile(name);
                this.sendReplaceableClientMessage(this.getMarker() + "Set current profile (" + Formatting.GREEN + name + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
            default: {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid argument (" + Formatting.YELLOW + argument + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
        }
    }
}
