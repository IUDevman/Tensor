package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.events.PacketSendEvent;
import dev.tensor.misc.imp.Wrapper;
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

@Mixin(ClientConnection.class)
public final class ClientConnectionMixin implements Wrapper {

    @Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
    public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo callbackInfo) {
        PacketSendEvent packetSendEvent = new PacketSendEvent(packet);

        Tensor.EVENT_BUS.post(packetSendEvent);

        if (packetSendEvent.isCancelled()) callbackInfo.cancel();
    }
}
