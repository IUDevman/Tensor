package dev.tensor.feature.managers;

import com.google.gson.*;
import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.ColorSetting;
import dev.tensor.misc.imp.settings.EnumSetting;
import dev.tensor.misc.imp.settings.NumberSetting;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 04-28-2021
 */

public enum ConfigManager implements Manager {

    INSTANCE;

    private final String mainPath = Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/";
    private final String modulePath = mainPath + "modules/";

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("ConfigManager");

        try {
            if (!Files.exists(Paths.get(mainPath))) {
                Files.createDirectories(Paths.get(mainPath));
            }

            if (!Files.exists(Paths.get(modulePath))) {
                Files.createDirectories(Paths.get(modulePath));
            }

            loadPrefix();
            loadClickGUI();

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

                    if (jsonElement == null) return;

                    if (objectSetting instanceof BooleanSetting) {
                        ((BooleanSetting) objectSetting).setValue(jsonElement.getAsBoolean());
                    } else if (objectSetting instanceof NumberSetting) {
                        ((NumberSetting) objectSetting).setValue(jsonElement.getAsDouble());
                    } else if (objectSetting instanceof EnumSetting) {
                        Enum<?>[] array = ((EnumSetting) objectSetting).getValue().getDeclaringClass().getEnumConstants();

                        Arrays.stream(array).forEach(anEnum -> {
                            if (anEnum.name().equalsIgnoreCase(jsonElement.getAsString())) {
                                ((EnumSetting) objectSetting).setValue(anEnum);
                            }
                        });

                    } else if (objectSetting instanceof ColorSetting) {
                        ((ColorSetting) objectSetting).setValue(new Color(jsonElement.getAsInt()));
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadPrefix() throws IOException {
        Path path = Paths.get(mainPath + "Prefix.json");

        if (!Files.exists(path)) return;

        InputStream inputStream = Files.newInputStream(path);
        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        CommandManager.INSTANCE.setPrefix(mainObject.get("Prefix").getAsString());
    }

    private void loadClickGUI() throws IOException {
        Path path = Paths.get(mainPath + "ClickGUI.json");

        if (!Files.exists(path)) return;

        InputStream inputStream = Files.newInputStream(path);
        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        ClickGUIManager.INSTANCE.getGUI().getX().setValue(mainObject.get("X Position").getAsDouble());
        ClickGUIManager.INSTANCE.getGUI().getY().setValue(mainObject.get("Y Position").getAsDouble());
    }

    public void save() {

        try {
            savePrefix();
            saveClickGUI();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ModuleManager.INSTANCE.getModules().forEach(module -> {

            try {
                Path path = Paths.get(modulePath + module.getName() + ".json");
                registerFile(path);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

                JsonObject mainObject = new JsonObject();

                mainObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
                mainObject.add("Messages", new JsonPrimitive(module.isMessages()));
                mainObject.add("Drawn", new JsonPrimitive(module.isDrawn()));
                mainObject.add("Bind", new JsonPrimitive(module.getBind()));

                JsonObject settingObject = new JsonObject();

                SettingManager.INSTANCE.getSettingsForModule(module).forEach(objectSetting -> {
                    if (objectSetting.getValue() instanceof Color) {
                        settingObject.add(objectSetting.getName(), new JsonPrimitive(((Color) objectSetting.getValue()).getRGB()));
                    } else {
                        settingObject.add(objectSetting.getName(), new JsonPrimitive(objectSetting.getValue().toString()));
                    }
                });

                mainObject.add("Settings", settingObject);

                String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
                outputStreamWriter.write(jsonString);
                outputStreamWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void savePrefix() throws IOException {
        Path path = Paths.get(mainPath + "Prefix.json");
        registerFile(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

        JsonObject mainObject = new JsonObject();
        mainObject.add("Prefix", new JsonPrimitive(CommandManager.INSTANCE.getPrefix()));

        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }

    private void saveClickGUI() throws IOException {
        Path path = Paths.get(mainPath + "ClickGUI.json");
        registerFile(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

        JsonObject mainObject = new JsonObject();
        mainObject.add("X Position", new JsonPrimitive(ClickGUIManager.INSTANCE.getGUI().getX().getValue()));
        mainObject.add("Y Position", new JsonPrimitive(ClickGUIManager.INSTANCE.getGUI().getY().getValue()));

        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }

    private void registerFile(Path path) throws IOException {
        if (Files.exists(path)) Files.delete(path);

        Files.createFile(path);
    }
}
