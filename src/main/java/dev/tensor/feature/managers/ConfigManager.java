package dev.tensor.feature.managers;

import com.google.gson.*;
import dev.tensor.Tensor;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Profile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author IUDevman
 * @since 04-28-2021
 */

public final class ConfigManager implements Manager {

    private final String mainPath = Tensor.INSTANCE.MOD_NAME.toLowerCase(Locale.ROOT) + "/";
    private final String spammerPath = mainPath + "spammer/";

    private final ArrayList<Profile> profiles = new ArrayList<>();
    private Profile currentProfile;

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("ConfigManager");

        try {
            if (!Files.exists(Paths.get(this.spammerPath))) {
                Files.createDirectories(Paths.get(this.spammerPath));
            }

            loadProfiles();

            if (this.currentProfile == null) {
                this.currentProfile = new Profile("default", this.mainPath);

                this.profiles.add(this.currentProfile);
            }

            this.currentProfile.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("SimplifyOptionalCallChains")
    private void loadProfiles() throws IOException {
        Path path = Paths.get(this.mainPath + "Profiles.json");

        if (!Files.exists(path)) return;

        InputStream inputStream = Files.newInputStream(path);
        JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("Profiles").getAsJsonArray();

        this.profiles.clear();
        jsonArray.forEach(jsonElement -> addAndCreateProfile(jsonElement.getAsString()));

        Profile profile = this.profiles.stream().filter(profile1 -> profile1.getName().equalsIgnoreCase(jsonObject.get("Default").getAsString())).findFirst().orElse(null);

        if (profile != null) {
            this.currentProfile = profile;
        }
    }

    public void save() {
        this.currentProfile.save();

        try {
            Path path = Paths.get(this.mainPath + "Profiles.json");
            registerFile(path);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path.toString()), StandardCharsets.UTF_8);

            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();

            jsonObject.add("Default", new JsonPrimitive(this.currentProfile.getName()));

            this.profiles.forEach(profile -> jsonArray.add(profile.getName()));
            jsonObject.add("Profiles", jsonArray);

            String jsonString = gson.toJson(new JsonParser().parse(jsonObject.toString()));
            outputStreamWriter.write(jsonString);
            outputStreamWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerFile(Path path) throws IOException {
        if (Files.exists(path)) Files.delete(path);

        Files.createFile(path);
    }

    private void addAndCreateProfile(String profileName) {
        if (this.profiles.stream().noneMatch(profile1 -> profile1.getName().equalsIgnoreCase(profileName))) {
            this.profiles.add(new Profile(profileName, this.mainPath));
        }
    }

    public void setCurrentProfile(String newProfile) {
        this.currentProfile.save();

        Profile profile = this.profiles.stream().filter(profile1 -> profile1.getName().equalsIgnoreCase(newProfile)).findFirst().orElse(null);

        if (profile != null) {
            this.currentProfile = profile;
            this.currentProfile.load();
        }
    }

    public void addProfile(String profileName) {
        if (!containsProfile(profileName)) {
            this.profiles.add(new Profile(profileName, this.mainPath));
        }
    }

    public void removeProfile(String profileName) {
        Profile profile = this.profiles.stream().filter(profile1 -> profile1.getName().equalsIgnoreCase(profileName)).findFirst().orElse(null);

        if (profile != null && this.currentProfile != profile) {
            this.profiles.remove(profile);
        }
    }

    public Profile getCurrentProfile() {
        return this.currentProfile;
    }

    public ArrayList<Profile> getProfiles() {
        return this.profiles;
    }

    public boolean containsProfile(String profileName) {
        return this.profiles.stream().anyMatch(profile -> profile.getName().equalsIgnoreCase(profileName));
    }
}
