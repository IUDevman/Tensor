package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public final class CapeManager implements Manager {

    private final ArrayList<String> capes = new ArrayList<>();
    private Identifier cape = null;

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("CapeManager");

        Path path = Paths.get(Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/Cape.png");

        if (Files.exists(path)) {
            Tensor.INSTANCE.LOGGER.info("Already downloaded cape!");
            return;
        }

        try {
            Path configPath = Paths.get(Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/");

            if (!Files.exists(configPath)) {
                Files.createDirectories(configPath);
            }

            URL url = new URL("https://raw.githubusercontent.com/IUDevman/gamesense-assets/main/files/tensor/cape.png");

            BufferedImage bufferedImage = ImageIO.read(url);
            Files.createFile(path);
            ImageIO.write(bufferedImage, "png", path.toFile());
            Tensor.INSTANCE.LOGGER.info("Downloaded cape!");

        } catch (IOException ignored) {
            Tensor.INSTANCE.LOGGER.warn("Failed to download cape!");
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

    public Identifier getCape(String name) {
        if (this.isNull()) return null;

        if (this.capes.contains(name) || this.getPlayer().getEntityName().equalsIgnoreCase(name)) {
            if (this.cape == null) {
                try {
                    Path path = Paths.get(Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/Cape.png");

                    if (!Files.exists(path)) return null;

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(ImageIO.read(path.toFile()), "png", byteArrayOutputStream);
                    InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

                    this.cape = this.getMinecraft().getTextureManager().registerDynamicTexture("666cape666", new NativeImageBackedTexture(NativeImage.read(inputStream)));

                } catch (IOException ignored) {
                    Tensor.INSTANCE.LOGGER.warn("Failed to load cape!");
                }
            }

            return this.cape;
        }

        return null;
    }
}
