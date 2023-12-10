package com.kermitemperor.coldsweathands.config;


import com.google.gson.*;
import com.kermitemperor.coldsweathands.ColdSweatHands;

import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ConfigHandler {

    public static JsonObject CONFIG = new JsonObject();

    private static final String CONFIG_FILE_NAME = "coldsweathands_limitations.json";
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


            writer.write(DefS());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String DefS() {
        //I know it would be "easier" with a json builder but then the recursive formating sucks
        return """
                {
                  "default_measure": "C",
                  "example_mod:first_example_block": {"Max": 100, "Min": -100},
                  "example_mod:second_example_block": {"Max": 100, "Min": -100, "Measure": "F"},
                  "example_mod:third_example_block": {"Max": 100, "Min": -100, "Measure": "C"},
                  "example_mod:fourth_example_block": {"Max": 100, "Min": -100, "Measure": "C", "Clickable": true},
                  "example_mod:first_example_item": {"Max": 100, "Min": -100},
                  "example_mod:second_example_item": {"Max": 100, "Min": -100, "Measure": "F"},
                  "example_mod:third_example_item": {"Max": 100, "Min": -100, "Measure": "C"}
                }""";
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
