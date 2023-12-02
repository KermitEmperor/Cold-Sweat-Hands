package com.kermitemperor.coldsweathands.config;


import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.kermitemperor.coldsweathands.ColdSweatHands;

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
        }
        loadConfig(configFile);

    }

    private static void createDefaultConfig(File configFile) {
        try {
            FileWriter writer = new FileWriter(configFile);
            writer.write(DefaultJSON.DefaultJSONWriter(new JsonObject()).toString());
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

    public static JsonObject reloadConfigAndReturn() {
        loadConfig(new File(CONFIG_PATH, CONFIG_FILE_NAME));
        return CONFIG;
    }

    public static void reloadConfig() {
        loadConfig(new File(CONFIG_PATH, CONFIG_FILE_NAME));
    }
}
