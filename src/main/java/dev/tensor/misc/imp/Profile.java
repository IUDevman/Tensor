package dev.tensor.misc.imp;

import com.google.gson.*;
import dev.tensor.Tensor;
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

/**
 * @author IUDevman
 * @since 07-12-2021
 */

public final class Profile {

    private final String mainPath;
    private final String name;

    public Profile(String name, String mainPath) {
        this.name = name;
        this.mainPath = mainPath + "profiles/";
    }

    public String getName() {
        return this.name;
    }

    public String getNamePath() {
        return this.mainPath + this.name + "/";
    }

    public void reset() {
        Tensor.INSTANCE.COMMAND_MANAGER.setPrefix("-");
        Tensor.INSTANCE.FRIEND_MANAGER.clearFriends();
        Tensor.INSTANCE.CAPE_MANAGER.clearCapes();
        Tensor.INSTANCE.GUI_MANAGER.getGUI().getX().reset();
        Tensor.INSTANCE.GUI_MANAGER.getGUI().getY().reset();

        Tensor.INSTANCE.MODULE_MANAGER.getModules().forEach(module -> {
            module.reset();

            Tensor.INSTANCE.SETTING_MANAGER.getSettingsForModule(module).forEach(Setting::reset);
        });
    }

    public void load() {
        try {
            if (!Files.exists(Paths.get(getMainPath()))) {
                Files.createDirectories(Paths.get(getMainPath()));
            }

            if (!Files.exists(Paths.get(getModulePath()))) {
                Files.createDirectories(Paths.get(getModulePath()));
            }

            loadPrefix();
            loadFriends();
            loadCapes();
            loadClickGUI();

        } catch (Exception ignored) {
            Tensor.INSTANCE.LOGGER.warn("Failed to load profile (" + this.name + ")!");
        }

        Tensor.INSTANCE.MODULE_MANAGER.getModules().forEach(module -> {

            try {
                Path path = Paths.get(getModulePath() + module.getName() + ".json");

                if (!Files.exists(path)) return;

                JsonObject jsonObject = getJsonObjectFromPath(path);

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

                module.setEnabled(jsonObject.get("Enabled").getAsBoolean());

            } catch (IOException ignored) {
                Tensor.INSTANCE.LOGGER.warn("Failed to load profile (" + this.name + ")!");
            }
        });
    }

    private void loadPrefix() throws IOException {
        Path path = Paths.get(getMainPath() + "Prefix.json");

        if (!Files.exists(path)) return;

        JsonObject jsonObject = getJsonObjectFromPath(path);

        Tensor.INSTANCE.COMMAND_MANAGER.setPrefix(jsonObject.get("Prefix").getAsString());
    }

    private void loadFriends() throws IOException {
        Path path = Paths.get(getMainPath() + "Friends.json");

        if (!Files.exists(path)) return;

        JsonObject jsonObject = getJsonObjectFromPath(path);
        JsonArray jsonArray = jsonObject.get("Friends").getAsJsonArray();

        Tensor.INSTANCE.FRIEND_MANAGER.clearFriends();
        jsonArray.forEach(jsonElement -> Tensor.INSTANCE.FRIEND_MANAGER.addFriend(jsonElement.getAsString()));
    }

    private void loadCapes() throws IOException {
        Path path = Paths.get(getMainPath() + "Capes.json");

        if (!Files.exists(path)) return;

        JsonObject jsonObject = getJsonObjectFromPath(path);
        JsonArray jsonArray = jsonObject.get("Capes").getAsJsonArray();

        Tensor.INSTANCE.CAPE_MANAGER.clearCapes();
        jsonArray.forEach(jsonElement -> Tensor.INSTANCE.CAPE_MANAGER.addCape(jsonElement.getAsString()));
    }

    private void loadClickGUI() throws IOException {
        Path path = Paths.get(getMainPath() + "ClickGUI.json");

        if (!Files.exists(path)) return;

        JsonObject jsonObject = getJsonObjectFromPath(path);

        Tensor.INSTANCE.GUI_MANAGER.getGUI().getX().setValue(jsonObject.get("X Position").getAsDouble());
        Tensor.INSTANCE.GUI_MANAGER.getGUI().getY().setValue(jsonObject.get("Y Position").getAsDouble());
    }

    public void save() {
        try {
            savePrefix();
            saveFriends();
            saveCapes();
            saveClickGUI();

        } catch (IOException ignored) {
            Tensor.INSTANCE.LOGGER.warn("Failed to save profile (" + this.name + ")!");
        }

        Tensor.INSTANCE.MODULE_MANAGER.getModules().forEach(module -> {

            try {
                Path path = Paths.get(getModulePath() + module.getName() + ".json");
                registerFile(path);

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

                createAndWrite(path, jsonObject);

            } catch (IOException ignored) {
                Tensor.INSTANCE.LOGGER.warn("Failed to save profile (" + this.name + ")!");
            }
        });
    }

    private void savePrefix() throws IOException {
        Path path = Paths.get(getMainPath() + "Prefix.json");
        registerFile(path);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Prefix", new JsonPrimitive(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix()));

        createAndWrite(path, jsonObject);
    }

    private void saveFriends() throws IOException {
        Path path = Paths.get(getMainPath() + "Friends.json");
        registerFile(path);

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        Tensor.INSTANCE.FRIEND_MANAGER.getFriends().forEach(jsonArray::add);
        jsonObject.add("Friends", jsonArray);

        createAndWrite(path, jsonObject);
    }

    private void saveCapes() throws IOException {
        Path path = Paths.get(getMainPath() + "Capes.json");
        registerFile(path);

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        Tensor.INSTANCE.CAPE_MANAGER.getCapes().forEach(jsonArray::add);
        jsonObject.add("Capes", jsonArray);

        createAndWrite(path, jsonObject);
    }

    private void saveClickGUI() throws IOException {
        Path path = Paths.get(getMainPath() + "ClickGUI.json");
        registerFile(path);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("X Position", new JsonPrimitive(Tensor.INSTANCE.GUI_MANAGER.getGUI().getX().getValue()));
        jsonObject.add("Y Position", new JsonPrimitive(Tensor.INSTANCE.GUI_MANAGER.getGUI().getY().getValue()));

        createAndWrite(path, jsonObject);
    }

    private JsonObject getJsonObjectFromPath(Path path) throws IOException {
        InputStream inputStream = Files.newInputStream(path);
        return new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
    }

    private void registerFile(Path path) throws IOException {
        if (Files.exists(path)) Files.delete(path);

        Files.createFile(path);
    }

    private void createAndWrite(Path path, JsonObject jsonObject) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

        String jsonString = gson.toJson(new JsonParser().parse(jsonObject.toString()));
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
    }

    private String getMainPath() {
        return this.mainPath + this.name + "/";
    }

    private String getModulePath() {
        return this.mainPath + this.name + "/modules/";
    }
}
