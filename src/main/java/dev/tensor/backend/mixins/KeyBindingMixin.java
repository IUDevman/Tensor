package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.misc.imp.Global;
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

@Mixin(value = KeyBinding.class, priority = MixinPriority.VALUE)
public final class KeyBindingMixin implements Global {

    @Inject(method = "onKeyPressed", at = @At("HEAD"))
    private static void onKeyPressed(InputUtil.Key key, CallbackInfo callbackInfo) {
        Tensor.INSTANCE.EVENT_HANDLER.call(new KeyPressedEvent(key.getCode()));
    }
}
