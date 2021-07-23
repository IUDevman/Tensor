package dev.tensor.feature.modules;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.event.EventTarget;
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

    public final EnumSetting type = new EnumSetting("Type", Type.Packet);
    public final BooleanSetting endCrystals = new BooleanSetting("End Crystals", false);

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Send) return;

        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket packet = (PlayerInteractEntityC2SPacket) event.getPacket();

            //if (!shouldApplyCriticals(packet.getEntity(this.getMinecraft().getServer().getWorld()))) return;

            switch ((Type) type.getValue()) {
                case Packet: {
                    this.getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getPlayer().getX(), this.getPlayer().getY() + 0.05, this.getPlayer().getZ(), false));
                    this.getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getPlayer().getX(), this.getPlayer().getY(), this.getPlayer().getZ(), false));
                    this.getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getPlayer().getX(), this.getPlayer().getY() + 0.03, this.getPlayer().getZ(), false));
                    this.getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getPlayer().getX(), this.getPlayer().getY(), this.getPlayer().getZ(), false));
                    break;
                }
                case Strict: {
                    this.getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getPlayer().getX(), this.getPlayer().getY() + 0.062602401692772, this.getPlayer().getZ(), false));
                    this.getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getPlayer().getX(), this.getPlayer().getY() + 0.0726023996066094, this.getPlayer().getZ(), false));
                    this.getNetwork().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.getPlayer().getX(), this.getPlayer().getY(), this.getPlayer().getZ(), false));
                    break;
                }
                default:
                    break;
            }
        }
    }

    private boolean shouldApplyCriticals(Entity entity) {
        if (this.getPlayer().isSwimming() || this.getPlayer().isInSwimmingPose()) return false;
        else if (this.getPlayer().isInLava()) return false;
        else if (!this.getPlayer().isOnGround()) return false;
        else return !(entity instanceof EndCrystalEntity) || endCrystals.getValue();
    }

    public enum Type {
        Packet,
        Strict
    }
}
