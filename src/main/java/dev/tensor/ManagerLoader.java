package dev.tensor;

import dev.tensor.feature.managers.*;
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
        loadManager(ConfigManager.INSTANCE);
    }

    private static void loadManager(Manager manager) {
        managers.add(manager);
        Tensor.EVENT_BUS.subscribe(manager);
        manager.load();
    }
}
