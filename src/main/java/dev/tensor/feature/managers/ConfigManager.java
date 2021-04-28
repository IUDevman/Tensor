package dev.tensor.feature.managers;

import com.google.gson.*;
import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author IUDevman
 * @since 04-28-2021
 */

public enum ConfigManager implements Manager {

    INSTANCE;

    private final String mainPath = "tensor/";
    private final String modulePath = mainPath + "modules/";

    @Override
    public void load() {
        Tensor.LOGGER.info("ConfigManager");

        try {
            if (!Files.exists(Paths.get(mainPath))) {
                Files.createDirectories(Paths.get(mainPath));
            }

            if (!Files.exists(Paths.get(modulePath))) {
                Files.createDirectories(Paths.get(modulePath));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ModuleManager.INSTANCE.getModules().forEach(module -> {

            try {
                Path path = Paths.get(modulePath + module.getName() + ".json");

                if (!Files.exists(path)) return;

                InputStream inputStream = Files.newInputStream(path);
                JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

                module.setEnabled(mainObject.get("Enabled").getAsBoolean());
                module.setMessages(mainObject.get("Messages").getAsBoolean());
                module.setDrawn(mainObject.get("Drawn").getAsBoolean());
                module.setBind(mainObject.get("Bind").getAsInt());

                JsonObject settingObject = mainObject.getAsJsonObject("Settings");

                SettingManager.INSTANCE.getSettingsForModule(module).forEach(objectSetting -> {
                    JsonElement jsonElement = settingObject.get(objectSetting.getName());

                    if (objectSetting.getValue() instanceof Boolean) {
                        objectSetting.setValue(jsonElement.getAsBoolean());
                    } else if (objectSetting.getValue() instanceof Integer || objectSetting.getValue() instanceof Double) {
                        objectSetting.setValue(jsonElement.getAsDouble());
                    } //todo: enum and color values
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {
        ModuleManager.INSTANCE.getModules().forEach(module -> {

            try {
                Path path = Paths.get(modulePath + module.getName() + ".json");

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

                JsonObject mainObject = new JsonObject();

                mainObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
                mainObject.add("Messages", new JsonPrimitive(module.isMessages()));
                mainObject.add("Drawn", new JsonPrimitive(module.isDrawn()));
                mainObject.add("Bind", new JsonPrimitive(module.getBind()));

                JsonObject settingObject = new JsonObject();

                SettingManager.INSTANCE.getSettingsForModule(module).forEach(objectSetting -> settingObject.add(objectSetting.getName(), new JsonPrimitive(objectSetting.getValue().toString())));

                mainObject.add("Settings", settingObject);

                String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
                outputStreamWriter.write(jsonString);
                outputStreamWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
