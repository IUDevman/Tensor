package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author IUDevman
 * @since 05-05-2021
 */

public enum CapeManager implements Manager {

    INSTANCE;

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("CapeManager");

        Path path = Paths.get(ConfigManager.INSTANCE.getMainPath() + "Cape.png");

        if (Files.exists(path)) return;

        try {
            URL url = new URL("https://raw.githubusercontent.com/IUDevman/gamesense-assets/main/files/tensor/cape.png");

            BufferedImage bufferedImage = ImageIO.read(url);
            Files.createFile(path);
            ImageIO.write(bufferedImage, "png", path.toFile());

        } catch (IOException ignored) {
            Tensor.INSTANCE.LOGGER.info("Failed to load cape!");
        }
    }
}
