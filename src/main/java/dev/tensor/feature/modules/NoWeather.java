package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

@Module.Info(name = "NoWeather", category = Category.Render)
public final class NoWeather extends Module {

    public void onTick() {
        if (!getWorld().getDimension().isBedWorking()) return;

        if (getWorld().isRaining()) getWorld().setRainGradient(0);
        if (getWorld().isThundering()) getWorld().setThunderGradient(0);
    }
}
