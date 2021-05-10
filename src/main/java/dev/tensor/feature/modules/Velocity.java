package dev.tensor.feature.modules;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.backend.mixins.accessors.EntityVelocityUpdateS2CPacketAccessor;
import dev.tensor.backend.mixins.accessors.ExplosionS2CPacketAccessor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Module.Info(name = "Velocity", category = Category.Movement)
public final class Velocity extends Module {

    public final BooleanSetting explosions = new BooleanSetting("Explosions", true);
    public final NumberSetting horizontal = new NumberSetting("Horizontal", 0, 0, 1, 2);
    public final NumberSetting vertical = new NumberSetting("Vertical", 0, 0, 1, 2);

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PacketEvent> packetEventListener = new Listener<>(event -> {
        if (event.getType() != PacketEvent.Type.Receive) return;

        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket packet = (EntityVelocityUpdateS2CPacket) event.getPacket();

            if (packet.getId() != getPlayer().getEntityId()) return;

            ((EntityVelocityUpdateS2CPacketAccessor) packet).setVelocityX(horizontal.getValue().intValue());
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setVelocityY(vertical.getValue().intValue());
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setVelocityZ(horizontal.getValue().intValue());
        }

        if (event.getPacket() instanceof ExplosionS2CPacket && explosions.getValue()) {
            ExplosionS2CPacket packet = (ExplosionS2CPacket) event.getPacket();

            ((ExplosionS2CPacketAccessor) packet).setPlayerVelocityX(horizontal.getValue().floatValue());
            ((ExplosionS2CPacketAccessor) packet).setPlayerVelocityY(vertical.getValue().floatValue());
            ((ExplosionS2CPacketAccessor) packet).setPlayerVelocityZ(horizontal.getValue().floatValue());
        }
    });
}
