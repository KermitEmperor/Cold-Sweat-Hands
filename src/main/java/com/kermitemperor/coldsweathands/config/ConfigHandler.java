package com.kermitemperor.coldsweathands.config;


import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.kermitemperor.coldsweathands.ColdSweatHands;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigHandler {

    public static JsonObject CONFIG = new JsonObject();
    private static final String CONFIG_FILE_NAME = "coldsweathands_blocks.json";
    private static final String CONFIG_PATH = "config/%s/".formatted(ColdSweatHands.MOD_ID);

    public static void init() {
        File configFolder = new File(CONFIG_PATH);

        if (!configFolder.exists()) {
            //noinspection ResultOfMethodCallIgnored
            configFolder.mkdirs();
        }

        File configFile = new File(configFolder, CONFIG_FILE_NAME);

        if (!configFile.exists()) {
            createDefaultConfig(configFile);
        } else {
            loadConfig(configFile);
        }

    }

    private static void createDefaultConfig(File configFile) {
        try {
            JsonObject defaultConfig = new JsonObject();
            // Add your default configuration values here

            defaultConfig.addProperty("Test", true);

            FileWriter writer = new FileWriter(configFile);
            writer.write(defaultConfig.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig(File configFile) {
        try {
            FileReader reader = new FileReader(configFile);
            CONFIG = JsonParser.parseReader(reader).getAsJsonObject();


            reader.close();
            LOGGER.info("Config loading was succesful!");
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public static class ReloadEvent {
        @SubscribeEvent
        public void onReload(OnDatapackSyncEvent event) {
            try {
                FileReader reader = new FileReader(CONFIG_PATH + CONFIG_FILE_NAME);
                CONFIG = JsonParser.parseReader(reader).getAsJsonObject();

                reader.close();
                LOGGER.info("Config reloading was succesful!");
            } catch (JsonIOException | JsonSyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
