package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.client.gui.screen.DeathScreen;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Module.Info(name = "AutoRespawn", category = Category.Misc)
public final class AutoRespawn extends Module {

    @Override
    public void onTick() {
        if (this.getMinecraft().currentScreen instanceof DeathScreen) {
            this.getPlayer().requestRespawn();
            this.getMinecraft().setScreen(null);
        }
    }
}
