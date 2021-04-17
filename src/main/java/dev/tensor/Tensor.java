package dev.tensor;

import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class Tensor implements ModInitializer {

    public static final String MOD_NAME = "Tensor";
    public static final String MOD_VERSION = "0.1.0-SNAPSHOT";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final EventBus EVENT_BUS = new EventManager();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + MOD_NAME + " " + MOD_VERSION + "!");

        setupClient();

        LOGGER.info("Finished initializing " + MOD_NAME + " " + MOD_VERSION + "!");
    }

    private void setupClient() {

        ManagerLoader.load();
        LOGGER.info("Finished initializing managers!");
    }
}
