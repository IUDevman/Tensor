package dev.tensor.feature.commands;

import dev.tensor.feature.managers.FriendManager;
import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;

import java.util.Locale;

/**
 * @author IUDevman
 * @since 05-20-2021
 */

public final class Friend implements Command {

    @Override
    public String getName() {
        return "Friend";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [list/clear/add/remove] [name]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "friend",
                "friends",
                "friendlist"
        };
    }

    @Override
    public int getID() {
        return 675;
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
            case "list" : {
                if (FriendManager.INSTANCE.getFriends().size() == 0) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No friends!", this.getID(), true);
                    return;
                }

                this.sendReplaceableClientMessage(this.getMarker() + "Friends: " + FriendManager.INSTANCE.getFriends() + "!", this.getID(), true);
                break;
            }
            case "clear" : {
                FriendManager.INSTANCE.clearFriends();
                this.sendReplaceableClientMessage(this.getMarker() + "Cleared friends!", this.getID(), true);
                break;
            }
            case "add" : {
                if (isMissingName) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No name inputted!", this.getID(), true);
                    return;
                }

                String name = message[2];

                if (FriendManager.INSTANCE.isFriend(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "Already a friend (" + Formatting.YELLOW + name + Formatting.GRAY + ")!", this.getID(), true);
                    return;
                }

                FriendManager.INSTANCE.addFriend(name);
                this.sendReplaceableClientMessage(this.getMarker() + "Added friend (" + Formatting.GREEN + name + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
            case "remove" : {
                if (isMissingName) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No name inputted!", this.getID(), true);
                    return;
                }

                String name = message[2];

                if (!FriendManager.INSTANCE.isFriend(name)) {
                    this.sendReplaceableClientMessage(this.getMarker() + "No friends matching (" + Formatting.YELLOW + name + Formatting.GRAY + ")!", this.getID(), true);
                    return;
                }

                FriendManager.INSTANCE.removeFriend(name);
                this.sendReplaceableClientMessage(this.getMarker() + "Removed friend (" + Formatting.RED + name + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
            default: {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid argument ("  + Formatting.YELLOW + argument + Formatting.GRAY + ")!", this.getID(), true);
                break;
            }
        }
    }
}
