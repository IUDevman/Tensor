package dev.tensor;

import dev.tensor.feature.managers.*;
import dev.tensor.misc.event.EventHandler;
import dev.tensor.misc.imp.Manager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public final String MOD_VERSION = "0.6.0-SNAPSHOT";

    public final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public EventHandler EVENT_HANDLER;

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
        long startTime = System.currentTimeMillis();
        this.LOGGER.info("Initializing " + this.MOD_NAME + " " + this.MOD_VERSION + "!");

        loadClient();

        long finishedTime = System.currentTimeMillis() - startTime;
        this.LOGGER.info("Finished initializing " + this.MOD_NAME + " " + this.MOD_VERSION + " (" + finishedTime + "ms)!");
    }

    private void loadClient() {
        this.EVENT_HANDLER = new EventHandler();

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
        this.EVENT_HANDLER.register(manager);
        manager.load();
    }
}
