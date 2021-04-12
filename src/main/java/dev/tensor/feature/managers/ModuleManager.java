package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.FullBright;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum ModuleManager implements Manager {

    INSTANCE;

    private final String modulePath = "dev.tensor.feature.modules";

    private final LinkedHashMap<Class<? extends Module>, Module> moduleClassLinkedHashMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, Module> moduleNameLinkedHashMap = new LinkedHashMap<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("ModuleManager");

        addModule(new FullBright());
    }

    public void addModule(Module module) {
        this.moduleClassLinkedHashMap.put(module.getClass(), module);
        this.moduleNameLinkedHashMap.put(module.getName().toLowerCase(Locale.ROOT), module);
    }

    public Collection<Module> getModules() {
        return this.moduleClassLinkedHashMap.values();
    }
}
