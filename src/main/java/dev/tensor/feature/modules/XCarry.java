package dev.tensor.feature.modules;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.backend.mixins.accessors.CloseHandledScreenC2SAccessor;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

/**
 * @author IUDevman
 * @since 05-12-2021
 */

@Module.Info(name = "XCarry", category = Category.Player)
public final class XCarry extends Module {

    private boolean cancelled = false;

    @Override
    public void onDisable() {
        if (cancelled) getNetwork().sendPacket(new CloseHandledScreenC2SPacket(getPlayer().playerScreenHandler.syncId));
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Send) return;

        if (event.getPacket() instanceof CloseHandledScreenC2SPacket) {
            CloseHandledScreenC2SPacket packet = (CloseHandledScreenC2SPacket) event.getPacket();

            if (((CloseHandledScreenC2SAccessor) packet).getSyncId() == getPlayer().playerScreenHandler.syncId) {
                event.setCancelled(true);
                cancelled = true;
            }
        }
    }
}
