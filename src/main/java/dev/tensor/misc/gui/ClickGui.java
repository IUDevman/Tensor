package dev.tensor.misc.gui;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

/**
 * @author IUDevman
 * @since 04-13-2021
 */

public final class ClickGui extends Screen implements Wrapper {

    public ClickGui() {
        super(new LiteralText(Tensor.MOD_NAME + " ClickGui"));
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int releasedButton) {
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
