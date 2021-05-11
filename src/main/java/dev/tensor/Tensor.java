package dev.tensor;

import dev.tensor.misc.gui.TensorGUI;
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

    public static Tensor INSTANCE;

    public Tensor() {
        INSTANCE = this;
    }

    public final String MOD_NAME = "Tensor";
    public final String MOD_VERSION = "0.1.0-RELEASE";

    public final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public final EventBus EVENT_BUS = new EventManager();

    @Override
    public void onInitialize() {
        double startTime = System.currentTimeMillis();
        LOGGER.info("Initializing " + MOD_NAME + " " + MOD_VERSION + "!");

        setupClient();

        double finishedTime = (System.currentTimeMillis() - startTime) / 1000;
        LOGGER.info("Finished initializing " + MOD_NAME + " " + MOD_VERSION +  " (" + finishedTime + "s)!");
    }

    public TensorGUI TENSOR_GUI;

    private void setupClient() {

        ManagerLoader.load();
        LOGGER.info("Finished initializing managers!");

        TENSOR_GUI = new TensorGUI();
        LOGGER.info("Finished initializing gui!");
    }
}
