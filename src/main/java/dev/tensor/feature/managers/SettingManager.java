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

    private final LinkedHashMap<Setting<Object>, Module> moduleSettingLinkedHashMap = new LinkedHashMap<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("SettingManager");

        ModuleManager.INSTANCE.getModules().forEach(module -> Arrays.stream(module.getClass().getDeclaredFields()).forEach(field -> {

            if (Setting.class.isAssignableFrom(field.getType())) {
                if (!field.isAccessible()) field.setAccessible(true);

                try {
                    @SuppressWarnings("unchecked")
                    Setting<Object> setting = (Setting<Object>) field.get(module);
                    this.moduleSettingLinkedHashMap.put(setting, module);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public Collection<Setting<Object>> getSettings() {
        return moduleSettingLinkedHashMap.keySet();
    }

    public ArrayList<Setting<Object>> getSettingsForModule(Module module) {
        final ArrayList<Setting<Object>> settings = new ArrayList<>();

        this.moduleSettingLinkedHashMap.forEach((setting, module1) -> {
            if (module1.equals(module)) settings.add(setting);
        });

        return settings;
    }
}
