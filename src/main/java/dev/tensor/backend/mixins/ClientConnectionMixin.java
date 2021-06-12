package dev.tensor.backend.mixins;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.event.EventHandler;
import dev.tensor.misc.imp.Global;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 04-13-2021
 */

@Mixin(value = ClientConnection.class, priority = Integer.MAX_VALUE)
public final class ClientConnectionMixin implements Global {

    @Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
    public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo callbackInfo) {
        if (this.isNull()) return;

        PacketEvent packetEvent = new PacketEvent(PacketEvent.Type.Send, packet);

        EventHandler.call(packetEvent);
        if (packetEvent.isCancelled()) callbackInfo.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void receive(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
        if (this.isNull()) return;

        PacketEvent packetEvent = new PacketEvent(PacketEvent.Type.Receive, packet);

        EventHandler.call(packetEvent);
        if (packetEvent.isCancelled()) callbackInfo.cancel();
    }
}
