package dev.tensor.feature.modules;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.backend.mixins.accessors.PlayerMoveC2SPacketAccessor;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Module.Info(name = "OnGround", category = Category.Player)
public final class OnGround extends Module {

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Send) return;

        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
            PlayerMoveC2SPacket packet = (PlayerMoveC2SPacket) event.getPacket();

            ((PlayerMoveC2SPacketAccessor) packet).setOnGround(false);
        }
    }
}
