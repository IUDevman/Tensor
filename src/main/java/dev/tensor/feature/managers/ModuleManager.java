package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.util.ClassUtil;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum ModuleManager implements Manager {

    INSTANCE;

    private final LinkedHashMap<Class<? extends Module>, Module> moduleClassLinkedHashMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, Module> moduleNameLinkedHashMap = new LinkedHashMap<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("ModuleManager");

        ClassUtil.findClassesForPath("dev.tensor.feature.modules").forEach(aClass -> {

            if (Module.class.isAssignableFrom(aClass)) {
                try {
                    Module module = (Module) aClass.newInstance();
                    addModule(module);

                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addModule(Module module) {
        this.moduleClassLinkedHashMap.put(module.getClass(), module);
        this.moduleNameLinkedHashMap.put(module.getName().toLowerCase(Locale.ROOT), module);
    }

    public Collection<Module> getModules() {
        return this.moduleClassLinkedHashMap.values();
    }
}
