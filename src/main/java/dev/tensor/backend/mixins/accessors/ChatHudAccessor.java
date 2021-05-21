package dev.tensor.backend.mixins.accessors;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author IUDevman
 * @since 04-17-2021
 */

@Mixin(value = ChatHud.class, priority = 9999)
public interface ChatHudAccessor {

    @Invoker("addMessage")
    void addReplaceable(Text text, int id);
}
