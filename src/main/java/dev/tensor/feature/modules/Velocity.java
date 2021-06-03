package dev.tensor.feature.modules;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.backend.mixins.accessors.EntityVelocityUpdateS2CPacketAccessor;
import dev.tensor.backend.mixins.accessors.ExplosionS2CPacketAccessor;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Module.Info(name = "Velocity", category = Category.Movement)
public final class Velocity extends Module {

    public final BooleanSetting damage = new BooleanSetting("Damage", true);
    public final BooleanSetting explosion = new BooleanSetting("Explosion", true);

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Receive) return;

        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket && damage.getValue()) {
            EntityVelocityUpdateS2CPacket packet = (EntityVelocityUpdateS2CPacket) event.getPacket();

            if (packet.getId() != getPlayer().getEntityId()) return;

            ((EntityVelocityUpdateS2CPacketAccessor) packet).setVelocityX(0);
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setVelocityY(0);
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setVelocityZ(0);
        }

        if (event.getPacket() instanceof ExplosionS2CPacket && explosion.getValue()) {
            ExplosionS2CPacket packet = (ExplosionS2CPacket) event.getPacket();

            ((ExplosionS2CPacketAccessor) packet).setPlayerVelocityX(0);
            ((ExplosionS2CPacketAccessor) packet).setPlayerVelocityY(0);
            ((ExplosionS2CPacketAccessor) packet).setPlayerVelocityZ(0);
        }
    }
}
