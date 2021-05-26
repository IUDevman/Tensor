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
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 05-05-2021
 */

public enum CapeManager implements Manager {

    INSTANCE;

    private final ArrayList<String> capes = new ArrayList<>();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("CapeManager");

        Path path = Paths.get(Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/Cape.png");

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

    public ArrayList<String> getCapes() {
        return this.capes;
    }

    public void addCape(String name) {
        this.capes.add(name);
    }

    public void removeCape(String name) {
        this.capes.remove(name);
    }

    public void clearCapes() {
        this.capes.clear();
    }

    public boolean hasCape(String name) {
        return this.capes.contains(name);
    }
}
