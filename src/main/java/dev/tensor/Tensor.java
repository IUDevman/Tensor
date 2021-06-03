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
    public final String MOD_VERSION = "0.4.0-SNAPSHOT";

    public final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public final ArrayList<Manager> MANAGERS = new ArrayList<>();

    @Override
    public void onInitialize() {
        double startTime = System.currentTimeMillis();
        this.LOGGER.info("Initializing " + this.MOD_NAME + " " + this.MOD_VERSION + "!");

        loadClient();

        double finishedTime = (System.currentTimeMillis() - startTime) / 1000;
        this.LOGGER.info("Finished initializing " + this.MOD_NAME + " " + this.MOD_VERSION + " (" + finishedTime + "s)!");
    }

    private void loadClient() {
        loadManager(CommandManager.INSTANCE);
        loadManager(ModuleManager.INSTANCE);
        loadManager(SettingManager.INSTANCE);
        loadManager(EventManager.INSTANCE);
        loadManager(CapeManager.INSTANCE);
        loadManager(FriendManager.INSTANCE);
        loadManager(ClickGUIManager.INSTANCE);
        loadManager(ConfigManager.INSTANCE);

        this.LOGGER.info("Finished initializing managers!");
    }

    private void loadManager(Manager manager) {
        this.MANAGERS.add(manager);
        EventHandler.register(manager);
        manager.load();
    }
}
