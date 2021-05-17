package dev.tensor.feature.modules;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/**
 * @author IUDevman
 * @since 05-17-2021
 */

@Module.Info(name = "Criticals", category = Category.Combat)
public final class Criticals extends Module {

    public final BooleanSetting endCrystals = new BooleanSetting("End Crystals", false);

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PacketEvent> packetEventListener = new Listener<>(event -> {
        if (event.getType() != PacketEvent.Type.Send) return;

        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket packet = (PlayerInteractEntityC2SPacket) event.getPacket();

            if (!shouldApplyCriticals(packet.getEntity(getWorld()))) return;

            getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY() + 0.1, getPlayer().getZ(), false));
            getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY(), getPlayer().getZ(), false));
        }
    });

    private boolean shouldApplyCriticals(Entity entity) {
        if (getPlayer().isSwimming() || getPlayer().isInSwimmingPose()) return false;
        else if (getPlayer().isInLava()) return false;
        else if (!getPlayer().isOnGround()) return false;
        else return !(entity instanceof EndCrystalEntity) || endCrystals.getValue();
    }
}
