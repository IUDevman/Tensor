package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.Setting;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author IUDevman
 * @since 04-13-2021
 */

public enum SettingManager implements Manager {

    INSTANCE;

    private final HashMap<Module, Setting<?>> moduleSettingHashMap = new HashMap<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("SettingManager");

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            Arrays.stream(module.getClass().getDeclaredFields()).forEach(field -> {
                if (Setting.class.isAssignableFrom(field.getType())) {

                    try {
                        Setting<?> setting = (Setting<?>) field.get(module);
                        moduleSettingHashMap.put(module, setting);
                        Tensor.LOGGER.info(setting.getName());

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    public Collection<Setting<?>> getSettings() {
        return moduleSettingHashMap.values();
    }
}
