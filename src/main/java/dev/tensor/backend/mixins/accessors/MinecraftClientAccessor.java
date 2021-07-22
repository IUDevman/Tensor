package dev.tensor.backend.mixins.accessors;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-21-2021
 */

@Mixin(value = MinecraftClient.class, priority = Integer.MAX_VALUE)
public interface MinecraftClientAccessor {

    @Accessor(value = "itemUseCooldown")
    void setItemUseCooldown(int itemUseCooldown);
}
