package dev.tensor;

import dev.tensor.feature.managers.CommandManager;
import dev.tensor.feature.managers.EventManager;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.imp.Manager;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class ManagerLoader {

    private static final ArrayList<Manager> managers = new ArrayList<>();

    public static void load() {
        loadManager(EventManager.INSTANCE);
        loadManager(CommandManager.INSTANCE);
        loadManager(ModuleManager.INSTANCE);
    }

    private static void loadManager(Manager manager) {
        managers.add(manager);
        Tensor.EVENT_BUS.subscribe(manager);
        manager.load();
    }
}
