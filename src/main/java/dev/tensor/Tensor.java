package dev.tensor;

import dev.tensor.feature.managers.*;
import dev.tensor.misc.event.EventHandler;
import dev.tensor.misc.imp.Manager;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class Tensor implements ClientModInitializer {

    public static Tensor INSTANCE;

    public Tensor() {
        INSTANCE = this;
    }

    public final String MOD_NAME = "Tensor";
    public final String MOD_VERSION = "1.0.0-SNAPSHOT";

    public final Logger LOGGER = LogManager.getLogger(this.MOD_NAME);

    public EventHandler EVENT_HANDLER;

    public CommandManager COMMAND_MANAGER;
    public ModuleManager MODULE_MANAGER;
    public PluginManager PLUGIN_MANAGER;
    public SettingManager SETTING_MANAGER;
    public EventManager EVENT_MANAGER;
    public CapeManager CAPE_MANAGER;
    public FriendManager FRIEND_MANAGER;
    public GUIManager GUI_MANAGER;
    public ConfigManager CONFIG_MANAGER;

    @Override
    public void onInitializeClient() {
        long startTime = System.currentTimeMillis();
        this.LOGGER.info("Initializing " + this.MOD_NAME + " " + this.MOD_VERSION + "!");

        loadClient();

        long finishedTime = System.currentTimeMillis() - startTime;
        this.LOGGER.info("Finished initializing " + this.MOD_NAME + " " + this.MOD_VERSION + " (" + finishedTime + "ms)!");
    }

    private void loadClient() {
        this.EVENT_HANDLER = new EventHandler();

        this.COMMAND_MANAGER = returnLoadedManager(new CommandManager());

        this.MODULE_MANAGER = returnLoadedManager(new ModuleManager());

        this.PLUGIN_MANAGER = returnLoadedManager(new PluginManager());

        this.SETTING_MANAGER = returnLoadedManager(new SettingManager());

        this.EVENT_MANAGER = returnLoadedManager(new EventManager());

        this.CAPE_MANAGER = returnLoadedManager(new CapeManager());

        this.FRIEND_MANAGER = returnLoadedManager(new FriendManager());

        this.GUI_MANAGER = returnLoadedManager(new GUIManager());

        this.CONFIG_MANAGER = returnLoadedManager(new ConfigManager());
    }

    private <T extends Manager> T returnLoadedManager(T manager) {
        this.EVENT_HANDLER.register(manager);
        manager.load();

        return manager;
    }
}
