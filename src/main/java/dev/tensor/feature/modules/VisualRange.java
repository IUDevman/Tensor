package dev.tensor.feature.modules;

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

    public final BooleanSetting replaceMessages = new BooleanSetting("Replace Messages", true);

    private final ArrayList<PlayerEntity> spottedPlayers = new ArrayList<>();

    @Override
    public void onDisable() {
        spottedPlayers.clear();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void onTick() {
        getWorld().getPlayers().forEach(player -> {

            if (!spottedPlayers.contains(player) && player != getPlayer()) {
                spottedPlayers.add(player);

                String message = "Player " + Formatting.YELLOW + player.getEntityName() + Formatting.GRAY + " has " + Formatting.RED + "entered " + Formatting.GRAY + "your visual range!";

                if (replaceMessages.getValue()) {
                    this.sendReplaceableClientMessage(message, 1001, true);
                } else {
                    this.sendClientMessage(message, true);
                }
            }
        });

        spottedPlayers.forEach(player -> {
            if (!getWorld().getPlayers().contains(player)) {
                String message = "Player " + Formatting.YELLOW + player.getEntityName() + Formatting.GRAY + " has " + Formatting.GREEN + "left " + Formatting.GRAY + "your visual range!";

                if (replaceMessages.getValue()) {
                    this.sendReplaceableClientMessage(message, 1001, true);
                } else {
                    this.sendClientMessage(message, true);
                }

                spottedPlayers.remove(player);
            }
        });
    }
}
