package dev.tensor;

import dev.darkmagician6.eventapi.EventHandler;
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
        loadManager(CapeManager.INSTANCE);
        loadManager(FriendManager.INSTANCE);
        loadManager(ClickGUIManager.INSTANCE);
        loadManager(ConfigManager.INSTANCE);
    }

    private static void loadManager(Manager manager) {
        managers.add(manager);
        EventHandler.register(manager);
        manager.load();
    }
}
