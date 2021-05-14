package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Module.Info(name = "OutlineESP", category = Category.Render)
public final class OutlineESP extends Module {

    public void onEnable() {
        getMinecraft().chunkCullingEnabled = false;
    }

    public void onDisable() {
        getMinecraft().chunkCullingEnabled = true;
    }

    public void onRender3D() {

    }
}
