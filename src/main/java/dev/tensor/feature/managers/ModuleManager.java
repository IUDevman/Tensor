package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.imp.Manager;
import dev.tensor.imp.Module;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum ModuleManager implements Manager {

    INSTANCE;

    private static final LinkedHashMap<Class<? extends Module>, Module> moduleClassLinkedHashMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, Module> moduleNameLinkedHashMap = new LinkedHashMap<>();

    @Override
    public void load() {
        Tensor.LOGGER.info("ModuleManager");
    }

    public Collection<Module> getModules() {
        return moduleClassLinkedHashMap.values();
    }
}
