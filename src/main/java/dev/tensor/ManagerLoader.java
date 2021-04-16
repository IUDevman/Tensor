package dev.tensor;

import dev.tensor.feature.managers.CommandManager;
import dev.tensor.feature.managers.EventManager;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.managers.SettingManager;
import dev.tensor.misc.imp.Manager;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class ManagerLoader {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final ArrayList<Manager> managers = new ArrayList<>();

    public static void load() {
        loadManager(CommandManager.INSTANCE);
        loadManager(ModuleManager.INSTANCE);
        loadManager(SettingManager.INSTANCE);
        loadManager(EventManager.INSTANCE);
    }

    private static void loadManager(Manager manager) {
        managers.add(manager);
        Tensor.EVENT_BUS.subscribe(manager);
        manager.load();
    }
}
