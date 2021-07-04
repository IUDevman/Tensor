package dev.tensor;

import dev.tensor.feature.managers.*;
import dev.tensor.misc.event.EventHandler;
import dev.tensor.misc.imp.Manager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class Tensor implements ModInitializer {

    public static Tensor INSTANCE;

    public Tensor() {
        INSTANCE = this;
    }

    public final String MOD_NAME = "Tensor";
    public final String MOD_VERSION = "0.5.0-SNAPSHOT";

    public final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public final EventHandler EVENT_HANDLER = new EventHandler();
    public final ArrayList<Manager> MANAGERS = new ArrayList<>();

    public CommandManager COMMAND_MANAGER;
    public ModuleManager MODULE_MANAGER;
    public SettingManager SETTING_MANAGER;
    public EventManager EVENT_MANAGER;
    public CapeManager CAPE_MANAGER;
    public FriendManager FRIEND_MANAGER;
    public ClickGUIManager GUI_MANAGER;
    public ConfigManager CONFIG_MANAGER;

    @Override
    public void onInitialize() {
        double startTime = System.currentTimeMillis();
        this.LOGGER.info("Initializing " + this.MOD_NAME + " " + this.MOD_VERSION + "!");

        loadClient();

        double finishedTime = (System.currentTimeMillis() - startTime) / 1000;
        this.LOGGER.info("Finished initializing " + this.MOD_NAME + " " + this.MOD_VERSION + " (" + finishedTime + "s)!");
    }

    public void onReload() {
       this.MANAGERS.forEach(this.EVENT_HANDLER::unregister);

       onInitialize();
    }

    private void loadClient() {
        this.MANAGERS.clear();

        this.COMMAND_MANAGER = new CommandManager();
        loadManager(this.COMMAND_MANAGER);

        this.MODULE_MANAGER = new ModuleManager();
        loadManager(this.MODULE_MANAGER);

        this.SETTING_MANAGER = new SettingManager();
        loadManager(this.SETTING_MANAGER);

        this.EVENT_MANAGER = new EventManager();
        loadManager(this.EVENT_MANAGER);

        this.CAPE_MANAGER = new CapeManager();
        loadManager(this.CAPE_MANAGER);

        this.FRIEND_MANAGER = new FriendManager();
        loadManager(this.FRIEND_MANAGER);

        this.GUI_MANAGER = new ClickGUIManager();
        loadManager(this.GUI_MANAGER);

        this.CONFIG_MANAGER = new ConfigManager();
        loadManager(this.CONFIG_MANAGER);

        this.LOGGER.info("Finished initializing managers!");
    }

    private void loadManager(Manager manager) {
        this.MANAGERS.add(manager);
        this.EVENT_HANDLER.register(manager);
        manager.load();
    }
}
