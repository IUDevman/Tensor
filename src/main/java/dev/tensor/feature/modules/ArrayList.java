package dev.tensor.feature.modules;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author IUDevman
 * @since 05-15-2021
 */

//this is a mega WIP module
@Module.Info(name = "ArrayList", category = Category.HUD)
public final class ArrayList extends Module {

    public final NumberSetting x = new NumberSetting("X Position", 0, 0, 1000, 0);
    public final NumberSetting y = new NumberSetting("Y Position", 100, 0, 1000, 0);

    @Override
    public void onRender2D() {
        MatrixStack matrixStack = new MatrixStack();
        AtomicInteger incrementY = new AtomicInteger();

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (!module.isEnabled() || !module.isDrawn()) return;

            DrawableHelper.drawStringWithShadow(matrixStack, getMinecraft().textRenderer, module.getName(), this.x.getValue().intValue(), this.y.getValue().intValue() + incrementY.get(), new Color(255, 255, 255, 255).getRGB());
            incrementY.getAndAdd(getMinecraft().textRenderer.fontHeight);
        });
    }
}
