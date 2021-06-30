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

public final class ConfigManager implements Manager {

    private final String mainPath = Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/";
    private final String modulePath = mainPath + "modules/";
    private final String spammerPath = mainPath + "spammer/";

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("ConfigManager");

        try {
            if (!Files.exists(Paths.get(this.mainPath))) {
                Files.createDirectories(Paths.get(this.mainPath));
            }

            if (!Files.exists(Paths.get(this.modulePath))) {
                Files.createDirectories(Paths.get(this.modulePath));
            }

            if (!Files.exists(Paths.get(this.spammerPath))) {
                Files.createDirectories(Paths.get(this.spammerPath));
            }

            loadPrefix();
            loadFriends();
            loadCapes();
            loadClickGUI();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Tensor.INSTANCE.MODULE_MANAGER.getModules().forEach(module -> {

            try {
                Path path = Paths.get(this.modulePath + module.getName() + ".json");

                if (!Files.exists(path)) return;

                InputStream inputStream = Files.newInputStream(path);
                JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

                module.setEnabled(jsonObject.get("Enabled").getAsBoolean());
                module.setMessages(jsonObject.get("Messages").getAsBoolean());
                module.setDrawn(jsonObject.get("Drawn").getAsBoolean());
                module.setBind(jsonObject.get("Bind").getAsInt());

                JsonObject settingObject = jsonObject.getAsJsonObject("Settings");

                Tensor.INSTANCE.SETTING_MANAGER.getSettingsForModule(module).forEach(objectSetting -> {
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
        Path path = Paths.get(this.mainPath + "Prefix.json");

        if (!Files.exists(path)) return;

        InputStream inputStream = Files.newInputStream(path);
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        Tensor.INSTANCE.COMMAND_MANAGER.setPrefix(jsonObject.get("Prefix").getAsString());
    }

    private void loadFriends() throws IOException {
        Path path = Paths.get(this.mainPath + "Friends.json");

        if (!Files.exists(path)) return;

        InputStream inputStream = Files.newInputStream(path);
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("Friends").getAsJsonArray();

        Tensor.INSTANCE.FRIEND_MANAGER.clearFriends();
        jsonArray.forEach(jsonElement -> Tensor.INSTANCE.FRIEND_MANAGER.addFriend(jsonElement.getAsString()));
    }

    private void loadCapes() throws IOException {
        Path path = Paths.get(this.mainPath + "Capes.json");

        if (!Files.exists(path)) return;

        InputStream inputStream = Files.newInputStream(path);
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("Capes").getAsJsonArray();

        Tensor.INSTANCE.CAPE_MANAGER.clearCapes();
        jsonArray.forEach(jsonElement -> Tensor.INSTANCE.CAPE_MANAGER.addCape(jsonElement.getAsString()));
    }

    private void loadClickGUI() throws IOException {
        Path path = Paths.get(this.mainPath + "ClickGUI.json");

        if (!Files.exists(path)) return;

        InputStream inputStream = Files.newInputStream(path);
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        Tensor.INSTANCE.GUI_MANAGER.getGUI().getX().setValue(jsonObject.get("X Position").getAsDouble());
        Tensor.INSTANCE.GUI_MANAGER.getGUI().getY().setValue(jsonObject.get("Y Position").getAsDouble());
    }

    public void save() {

        try {
            savePrefix();
            saveFriends();
            saveCapes();
            saveClickGUI();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Tensor.INSTANCE.MODULE_MANAGER.getModules().forEach(module -> {

            try {
                Path path = Paths.get(this.modulePath + module.getName() + ".json");
                registerFile(path);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

                JsonObject jsonObject = new JsonObject();

                jsonObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
                jsonObject.add("Messages", new JsonPrimitive(module.isMessages()));
                jsonObject.add("Drawn", new JsonPrimitive(module.isDrawn()));
                jsonObject.add("Bind", new JsonPrimitive(module.getBind()));

                JsonObject settingObject = new JsonObject();

                Tensor.INSTANCE.SETTING_MANAGER.getSettingsForModule(module).forEach(objectSetting -> {
                    if (objectSetting.getValue() instanceof Color) {
                        settingObject.add(objectSetting.getName(), new JsonPrimitive(((Color) objectSetting.getValue()).getRGB()));
                    } else {
                        settingObject.add(objectSetting.getName(), new JsonPrimitive(objectSetting.getValue().toString()));
                    }
                });

                jsonObject.add("Settings", settingObject);

                String jsonString = gson.toJson(new JsonParser().parse(jsonObject.toString()));
                outputStreamWriter.write(jsonString);
                outputStreamWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void savePrefix() throws IOException {
        Path path = Paths.get(this.mainPath + "Prefix.json");
        registerFile(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Prefix", new JsonPrimitive(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix()));

        String jsonString = gson.toJson(new JsonParser().parse(jsonObject.toString()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }

    private void saveFriends() throws IOException {
        Path path = Paths.get(this.mainPath + "Friends.json");
        registerFile(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        Tensor.INSTANCE.FRIEND_MANAGER.getFriends().forEach(jsonArray::add);
        jsonObject.add("Friends", jsonArray);

        String jsonString = gson.toJson(new JsonParser().parse(jsonObject.toString()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }

    private void saveCapes() throws IOException {
        Path path = Paths.get(this.mainPath + "Capes.json");
        registerFile(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        Tensor.INSTANCE.CAPE_MANAGER.getCapes().forEach(jsonArray::add);
        jsonObject.add("Capes", jsonArray);

        String jsonString = gson.toJson(new JsonParser().parse(jsonObject.toString()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }

    private void saveClickGUI() throws IOException {
        Path path = Paths.get(this.mainPath + "ClickGUI.json");
        registerFile(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("X Position", new JsonPrimitive(Tensor.INSTANCE.GUI_MANAGER.getGUI().getX().getValue()));
        jsonObject.add("Y Position", new JsonPrimitive(Tensor.INSTANCE.GUI_MANAGER.getGUI().getY().getValue()));

        String jsonString = gson.toJson(new JsonParser().parse(jsonObject.toString()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }

    private void registerFile(Path path) throws IOException {
        if (Files.exists(path)) Files.delete(path);

        Files.createFile(path);
    }
}
