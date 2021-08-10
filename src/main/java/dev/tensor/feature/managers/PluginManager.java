package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.plugin.Plugin;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 08-10-2021
 */

public final class PluginManager implements Manager {

    private final ArrayList<Plugin> plugins = new ArrayList<>();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("PluginManager");

        FabricLoader.getInstance().getEntrypointContainers(Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT), Plugin.class).forEach(pluginEntrypointContainer -> {

            Plugin plugin = pluginEntrypointContainer.getEntrypoint();

            if (plugin.getClass().isAnnotationPresent(Plugin.Info.class)) {
                this.plugins.add(pluginEntrypointContainer.getEntrypoint());
            }
        });

        this.plugins.forEach(Plugin::load);
    }
}
