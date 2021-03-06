package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.backend.events.PlaySoundEvent;
import dev.tensor.misc.imp.Global;
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

@Mixin(value = SoundManager.class, priority = MixinPriority.VALUE)
public final class SoundManagerMixin implements Global {

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    public void playNoDelay(SoundInstance sound, CallbackInfo ci) {
        PlaySoundEvent playSoundEvent = new PlaySoundEvent(sound);

        Tensor.INSTANCE.EVENT_HANDLER.call(playSoundEvent);
        if (playSoundEvent.isCancelled()) ci.cancel();
    }

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V", at = @At("HEAD"), cancellable = true)
    public void playWithDelay(SoundInstance sound, int delay, CallbackInfo ci) {
        PlaySoundEvent playSoundEvent = new PlaySoundEvent(sound);

        Tensor.INSTANCE.EVENT_HANDLER.call(playSoundEvent);
        if (playSoundEvent.isCancelled()) ci.cancel();
    }
}
