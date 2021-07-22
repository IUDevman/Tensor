package dev.tensor.backend.mixins.accessors;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author IUDevman
 * @since 04-17-2021
 */

@Mixin(value = ChatHud.class, priority = Integer.MAX_VALUE)
public interface ChatHudAccessor {

    @Invoker(value = "addMessage")
    void addReplaceable(Text text, int id);
}
