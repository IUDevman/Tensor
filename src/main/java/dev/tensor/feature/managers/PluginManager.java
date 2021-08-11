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

        FabricLoader.getInstance().getEntrypoints(Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT), Plugin.class).forEach(plugin -> {

            if (plugin.getClass().isAnnotationPresent(Plugin.Info.class)) {
                this.plugins.add(plugin);
            }
        });

        this.plugins.forEach(plugin -> {
            plugin.load();
            Tensor.INSTANCE.LOGGER.info("Loaded plugin: " + plugin.getName() + " " + plugin.getVersion() + "!");
        });
    }

    public void save() {
        this.plugins.forEach(Plugin::save);
    }

    public ArrayList<Plugin> getPlugins() {
        return this.plugins;
    }
}
