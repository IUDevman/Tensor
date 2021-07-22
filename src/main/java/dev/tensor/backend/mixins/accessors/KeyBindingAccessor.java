package dev.tensor.backend.mixins.accessors;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 07-21-2021
 */

@Mixin(value = KeyBinding.class, priority = Integer.MAX_VALUE)
public interface KeyBindingAccessor {

    @Accessor(value = "boundKey")
    InputUtil.Key getBoundKey();
}
