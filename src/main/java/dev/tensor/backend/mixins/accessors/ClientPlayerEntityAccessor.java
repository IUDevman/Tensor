package dev.tensor.backend.mixins.accessors;

import dev.tensor.backend.MixinPriority;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-17-2021
 */

@Mixin(value = ClientPlayerEntity.class, priority = MixinPriority.VALUE)
public interface ClientPlayerEntityAccessor {

    @Accessor(value = "input")
    void setInput(Input input);
}
