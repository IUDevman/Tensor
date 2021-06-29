package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.HUDModule;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * @author IUDevman
 * @since 06-29-2021
 */

@Module.Info(name = "Coordinates", category = Category.HUD)
public final class Coordinates extends Module implements HUDModule {

    public final NumberSetting x = new NumberSetting("X", 1, 0, 1000, 0);
    public final NumberSetting y = new NumberSetting("Y", 17, 0, 1000, 0);
    public final BooleanSetting nether = new BooleanSetting("Nether", false);

    private final DecimalFormat decimalFormat = new DecimalFormat("###.#");

    @Override
    public NumberSetting getStartX() {
        return this.x;
    }

    @Override
    public NumberSetting getStartY() {
        return this.y;
    }

    @Override
    public void onRender2D(MatrixStack matrixStack) {
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
