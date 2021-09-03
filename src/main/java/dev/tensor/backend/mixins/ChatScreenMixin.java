package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.Commands;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 09-03-2021
 */

@Mixin(value = ChatScreen.class, priority = MixinPriority.VALUE)
public final class ChatScreenMixin extends Screen implements Global {

    @Shadow
    protected TextFieldWidget chatField;

    public ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        Commands commands = Tensor.INSTANCE.MODULE_MANAGER.getModule(Commands.class);

        if (commands != null && commands.isEnabled() && commands.preview.getValue()) {
            Tensor.INSTANCE.GUI_MANAGER.getTensorChatGUI().render(matrices, this.chatField.getText(), 2, this.height - 14, this.width - 2, this.height - 2);
        }
    }
}
