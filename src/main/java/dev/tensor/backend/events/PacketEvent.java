package dev.tensor.backend.events;

import dev.tensor.misc.event.imp.EventCancellable;
import net.minecraft.network.Packet;

/**
 * @author IUDevman
 * @since 04-13-2021
 */

public final class PacketEvent extends EventCancellable {

    private final Type type;
    private final Packet<?> packet;

    public PacketEvent(Type type, Packet<?> packet) {
        this.type = type;
        this.packet = packet;
    }

    public Type getType() {
        return this.type;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public enum Type {
        Send,
        Receive
    }
}
