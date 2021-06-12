package dev.tensor.feature.modules;

import dev.tensor.feature.managers.FriendManager;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 05-26-2021
 */

@Module.Info(name = "VisualRange", category = Category.Misc)
public final class VisualRange extends Module {

    public final BooleanSetting friends = new BooleanSetting("Friends", true);
    public final BooleanSetting replaceMessages = new BooleanSetting("Replace Messages", true);

    private final ArrayList<PlayerEntity> spottedPlayers = new ArrayList<>();

    @Override
    public void onDisable() {
        spottedPlayers.clear();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void onTick() {
        this.getWorld().getPlayers().forEach(player -> {

            if (!spottedPlayers.contains(player) && player != this.getPlayer()) {

                if (!friends.getValue() && FriendManager.INSTANCE.isFriend(player.getEntityName())) return;

                spottedPlayers.add(player);

                String message = "Player " + Formatting.YELLOW + player.getEntityName() + Formatting.GRAY + " has " + Formatting.RED + "entered " + Formatting.GRAY + "your visual range!";

                if (replaceMessages.getValue()) {
                    this.sendReplaceableClientMessage(message, 1001, true);
                } else {
                    this.sendClientMessage(message, true);
                }
            }
        });

        ArrayList<PlayerEntity> removedPlayers = new ArrayList<>();

        spottedPlayers.forEach(player -> {

            if (!this.getWorld().getPlayers().contains(player)) {
                removedPlayers.add(player);

                if (!friends.getValue() && FriendManager.INSTANCE.isFriend(player.getEntityName())) return;

                String message = "Player " + Formatting.YELLOW + player.getEntityName() + Formatting.GRAY + " has " + Formatting.GREEN + "left " + Formatting.GRAY + "your visual range!";

                if (replaceMessages.getValue()) {
                    this.sendReplaceableClientMessage(message, 1001, true);
                } else {
                    this.sendClientMessage(message, true);
                }
            }
        });

        removedPlayers.forEach(spottedPlayers::remove);
    }
}
