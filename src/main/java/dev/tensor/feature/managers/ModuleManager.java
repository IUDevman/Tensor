package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.PluginEntryPoint;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class ModuleManager implements Manager {

    private final ArrayList<Module> modules = new ArrayList<>();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("ModuleManager");

        this.findClassesForPath("dev.tensor.feature.modules").forEach(aClass -> {

            if (Module.class.isAssignableFrom(aClass) && aClass.isAnnotationPresent(Module.Info.class)) {
                try {
                    Module module = (Module) aClass.getDeclaredConstructor().newInstance();
                    addModule(module);

                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @PluginEntryPoint
    public void addModule(Module module) {
        this.modules.add(module);
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public ArrayList<Module> getModulesInCategory(Category category) {
        return this.modules.stream().filter(module -> module.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> aClass) {
        return (T) this.modules.stream().filter(module -> module.getClass().equals(aClass)).findFirst().orElse(null);
    }

    public Module getModule(String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
