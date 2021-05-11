package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.text.DecimalFormat;

@Module.Info(name = "Coordinates", category = Category.HUD)
public final class Coordinates extends Module {

    public final NumberSetting posX = new NumberSetting("X Position", 3, 0, 1000, 0);
    public final NumberSetting posY = new NumberSetting("Y Position", 20, 0, 1000, 0);
    public final BooleanSetting nether = new BooleanSetting("Nether", false);

    private final DecimalFormat decimalFormat = new DecimalFormat("###.##");

    public void onRender2D(MatrixStack matrixStack) {
        double playerX = getPlayer().getX();
        double playerY = getPlayer().getY();
        double playerZ = getPlayer().getZ();

        if (getWorld().getDimension().isRespawnAnchorWorking()) {
            playerX *= 8;
            playerZ *= 8;
        }

        final String overWorld = "X: " + decimalFormat.format(playerX) + " Y: " + decimalFormat.format(playerY) + " Z: " + decimalFormat.format(playerZ);
        DrawableHelper.drawStringWithShadow(matrixStack, getMinecraft().textRenderer, overWorld, posX.getValue().intValue(), posY.getValue().intValue(), new Color(255, 255, 255, 255).getRGB());

        if (nether.getValue() && !getWorld().getDimension().hasEnderDragonFight()) {
            final String nether = "X: " + decimalFormat.format(playerX / 8) + " Y: " + decimalFormat.format(playerY) + " Z: " + decimalFormat.format(playerZ / 8);
            DrawableHelper.drawStringWithShadow(matrixStack, getMinecraft().textRenderer, nether, posX.getValue().intValue(), posY.getValue().intValue() + getMinecraft().textRenderer.fontHeight + 1, new Color(255, 0, 0, 255).getRGB());
        }
    }
}
