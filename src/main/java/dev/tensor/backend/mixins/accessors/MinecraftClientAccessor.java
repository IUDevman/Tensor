package dev.tensor.backend.mixins.accessors;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-21-2021
 */

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Accessor
    void setItemUseCooldown(int itemUseCooldown);
}
