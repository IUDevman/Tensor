package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Module.Info(name = "Coordinates", category = Category.HUD)
public final class Coordinates extends Module {

    public final NumberSetting x = new NumberSetting("X Position", 3, 0, 1000, 0);
    public final NumberSetting y = new NumberSetting("Y Position", 20, 0, 1000, 0);
    public final BooleanSetting nether = new BooleanSetting("Nether", false);

    private final DecimalFormat decimalFormat = new DecimalFormat("###.#");

    @Override
    public void onRender2D() {
        MatrixStack matrixStack = new MatrixStack();
        double playerX = this.getPlayer().getX();
        double playerY = this.getPlayer().getY();
        double playerZ = this.getPlayer().getZ();

        if (this.getWorld().getDimension().isRespawnAnchorWorking()) {
            playerX *= 8;
            playerZ *= 8;
        }

        final String overWorld = "X: " + decimalFormat.format(playerX) + " Y: " + decimalFormat.format(playerY) + " Z: " + decimalFormat.format(playerZ);
        DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, overWorld, x.getValue().intValue(), y.getValue().intValue(), new Color(255, 255, 255, 255).getRGB());

        if (nether.getValue() && !this.getWorld().getDimension().hasEnderDragonFight()) {
            final String nether = "X: " + decimalFormat.format(playerX / 8) + " Y: " + decimalFormat.format(playerY) + " Z: " + decimalFormat.format(playerZ / 8);
            DrawableHelper.drawStringWithShadow(matrixStack, this.getMinecraft().textRenderer, nether, x.getValue().intValue(), y.getValue().intValue() + this.getMinecraft().textRenderer.fontHeight + 1, new Color(255, 0, 0, 255).getRGB());
        }
    }
}
