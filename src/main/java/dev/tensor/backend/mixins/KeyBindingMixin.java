package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Hoosiers
 * @since 04-13-2021
 */

@Mixin(KeyBinding.class)
public final class KeyBindingMixin implements Wrapper {

    @Inject(method = "onKeyPressed", at = @At("HEAD"))
    private static void onKeyPressed(InputUtil.Key key, CallbackInfo callbackInfo) {
        Tensor.EVENT_BUS.post(new KeyPressedEvent(key.getCode()));
    }
}
