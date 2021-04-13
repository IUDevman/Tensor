package dev.tensor.backend.events;

import dev.tensor.misc.imp.Event;
import net.minecraft.network.Packet;

public class PacketSendEvent extends Event {

    private final Packet<?> packet;

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}
