package dev.tensor.misc.gui.elements.settings;

import dev.tensor.Tensor;
import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.Setting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 05-11-2021
 */

public final class ModulePropertyElement extends SettingElement {

    private final Module module;
    private boolean searching = false;

    public ModulePropertyElement(Module module, NumberSetting x, NumberSetting y, NumberSetting scrollY, int posX, int posY) {
        super(x, y, scrollY, posX, posY);

        this.module = module;
    }

    public Module getModule() {
        return this.module;
    }

    public boolean isSearching() {
        return this.searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    @Override
    public int getWidth() {
        return 204;
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public int getPosX() {
        return getX().getValue().intValue() + getXPos();
    }

    @Override
    public int getPosY() {
        return getY().getValue().intValue() + getScrollY().getValue().intValue() + getYPos();
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        DrawableHelper.fill(matrixStack, x, y, x + this.getWidth(), y + 15, this.isSearching() ? new Color(30, 30, 30, 150).getRGB() : new Color(0, 0, 0, 150).getRGB());
        DrawableHelper.fill(matrixStack, x, y + 15, x + this.getWidth(), y + this.getHeight(), new Color(0, 0, 0, 150).getRGB());

        String text = "Keybind : " + Formatting.GRAY + (this.isSearching() ? "..." : InputUtil.Type.KEYSYM.createFromCode(getModule().getBind()).getTranslationKey().replace("key.keyboard.", "").replace("unknown", "none").toUpperCase(Locale.ROOT));
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, text, x + 3, y + 3, new Color(255, 255, 255, 255).getRGB());

        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, Formatting.GRAY + "Reset", x + 3, y + 18, new Color(255, 255, 255, 255).getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (mouseY > this.getPosY() + 15) {
            if (isSearching()) setSearching(false);

            module.setDrawn(true);
            module.setMessages(false);
            module.setEnabled(false);
            module.setBind(GLFW.GLFW_KEY_UNKNOWN);

            Tensor.INSTANCE.SETTING_MANAGER.getSettingsForModule(module).forEach(Setting::reset);
            return;
        }

        setSearching(!isSearching());
    }

    public void onKeyPressed(int key) {
        if (!isSearching()) return;

        if (key == GLFW.GLFW_KEY_DELETE) {
            getModule().setBind(GLFW.GLFW_KEY_UNKNOWN);
        }

        if (key != GLFW.GLFW_KEY_ESCAPE && key != GLFW.GLFW_KEY_DELETE && key != GLFW.GLFW_KEY_ENTER) {
            getModule().setBind(key);
        }

        setSearching(false);
    }
}
