package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.util.ClassUtil;

import java.util.ArrayList;
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

        ClassUtil.INSTANCE.findClassesForPath("dev.tensor.feature.modules").forEach(aClass -> {

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

    public ArrayList<Module> getModulesInCategory(Category category) {
        final ArrayList<Module> modules = new ArrayList<>();

        moduleClassLinkedHashMap.forEach((aClass, module) -> {
            if (module.getCategory().equals(category)) modules.add(module);
        });

        return modules;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> tClass) {
        return (T) moduleClassLinkedHashMap.get(tClass);
    }

    public Module getModule(String name) {
        if (name == null) return null;
        return moduleNameLinkedHashMap.get(name.toLowerCase(Locale.ROOT));
    }
}
