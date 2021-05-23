package dev.tensor.feature.modules;

import dev.darkmagician6.eventapi.EventTarget;
import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.EnumSetting;
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

    public final EnumSetting mode = new EnumSetting("Mode", Mode.Packet);
    public final BooleanSetting endCrystals = new BooleanSetting("End Crystals", false);

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Send) return;

        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket packet = (PlayerInteractEntityC2SPacket) event.getPacket();

            if (!shouldApplyCriticals(packet.getEntity(getWorld()))) return;

            switch (mode.getValue()) {
                case Packet:
                    getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY() + 0.05, getPlayer().getZ(), false));
                    getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY(), getPlayer().getZ(), false));
                    getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY() + 0.03, getPlayer().getZ(), false));
                    getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY(), getPlayer().getZ(), false));
                    break;
                case Strict:
                    getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY() + 0.062602401692772, getPlayer().getZ(), false));
                    getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY() + 0.0726023996066094, getPlayer().getZ(), false));
                    getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionOnly(getPlayer().getX(), getPlayer().getY(), getPlayer().getZ(), false));
                    break
            }
        }
    }

    private boolean shouldApplyCriticals(Entity entity) {
        if (getPlayer().isSwimming() || getPlayer().isInSwimmingPose()) return false;
        else if (getPlayer().isInLava()) return false;
        else if (!getPlayer().isOnGround()) return false;
        else return !(entity instanceof EndCrystalEntity) || endCrystals.getValue();
    }
    
    private enum Mode {
        Packet,
        Strict
    }
}
