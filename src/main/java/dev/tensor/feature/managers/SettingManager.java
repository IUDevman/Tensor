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

public final class SettingManager implements Manager {

    private final LinkedHashMap<Setting<?>, Module> settingModuleLinkedHashMap = new LinkedHashMap<>();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("SettingManager");

        Tensor.INSTANCE.MODULE_MANAGER.getModules().forEach(module -> Arrays.stream(module.getClass().getDeclaredFields()).forEach(field -> {

            if (Setting.class.isAssignableFrom(field.getType())) {
                if (!field.isAccessible()) field.setAccessible(true);

                try {
                    Setting<?> setting = (Setting<?>) field.get(module);
                    this.settingModuleLinkedHashMap.put(setting, module);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public Collection<Setting<?>> getSettings() {
        return this.settingModuleLinkedHashMap.keySet();
    }

    public ArrayList<Setting<?>> getSettingsForModule(Module module) {
        ArrayList<Setting<?>> settings = new ArrayList<>();

        this.settingModuleLinkedHashMap.forEach((setting, module1) -> {
            if (module1.equals(module)) settings.add(setting);
        });

        return settings;
    }
}
