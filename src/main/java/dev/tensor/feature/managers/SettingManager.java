package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author IUDevman
 * @since 04-13-2021
 */

public enum SettingManager implements Manager {

    INSTANCE;

    private final LinkedHashMap<Module, Setting<?>> moduleSettingLinkedHashMap = new LinkedHashMap<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("SettingManager");

        ModuleManager.INSTANCE.getModules().forEach(module -> Arrays.stream(module.getClass().getDeclaredFields()).forEach(field -> {

            if (Setting.class.isAssignableFrom(field.getType())) {
                try {
                    Setting<?> setting = (Setting<?>) field.get(module);
                    moduleSettingLinkedHashMap.put(module, setting);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public Collection<Setting<?>> getSettings() {
        return moduleSettingLinkedHashMap.values();
    }

    public ArrayList<Setting<?>> getSettingsForModule(Module module) {
        final ArrayList<Setting<?>> settings = new ArrayList<>();

        moduleSettingLinkedHashMap.forEach((module1, setting) -> {
            if (module1.equals(module)) settings.add(setting);
        });

        return settings;
    }
}
