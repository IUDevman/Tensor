package dev.tensor.backend.mixins;

import dev.darkmagician6.eventapi.EventHandler;
import dev.tensor.backend.events.PlaySoundEvent;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-25-2021
 */

@Mixin(value = SoundManager.class, priority = Integer.MAX_VALUE)
public final class SoundManagerMixin implements Wrapper {

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    public void playNoDelay(SoundInstance sound, CallbackInfo callbackInfo) {
        PlaySoundEvent playSoundEvent = new PlaySoundEvent(sound);

        EventHandler.call(playSoundEvent);
        if (playSoundEvent.isCancelled()) callbackInfo.cancel();
    }

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V", at = @At("HEAD"), cancellable = true)
    public void playWithDelay(SoundInstance sound, int delay, CallbackInfo callbackInfo) {
        PlaySoundEvent playSoundEvent = new PlaySoundEvent(sound);

        EventHandler.call(playSoundEvent);
        if (playSoundEvent.isCancelled()) callbackInfo.cancel();
    }
}
