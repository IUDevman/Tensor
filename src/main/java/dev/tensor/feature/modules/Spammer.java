package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 05-28-2021
 */

@Module.Info(name = "Spammer", category = Category.Misc)
public final class Spammer extends Module {

    public final NumberSetting delayTicks = new NumberSetting("Delay Ticks", 60, 0, 200, 0);

    private final ArrayList<String> spammerFiles = new ArrayList<>();
    private Path path = null;
    private int delayCount = 0;

    @Override
    public void onEnable() {
        loadSpammer();
        delayCount = 0;
    }

    @Override
    public void onTick() {
        if (spammerFiles.size() == 0) {
            loadSpammer();
        }

        if (delayCount >= delayTicks.getValue() && spammerFiles.size() != 0) {
            delayCount = 0;

            String string = spammerFiles.get(0);

            if (string.length() > 256) {
                string = string.substring(0, 256);
            }

            getPlayer().sendChatMessage(string);
            spammerFiles.remove(0);
        }
        delayCount++;
    }

    private void loadSpammer() {
        spammerFiles.clear();

        if (path != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    spammerFiles.add(line);
                }

            } catch (IOException ignored) {
                Tensor.INSTANCE.LOGGER.info("Failed to load spammer file!");
            }
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
