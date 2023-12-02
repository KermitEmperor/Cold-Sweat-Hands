package com.kermitemperor.coldsweathands.config;

import com.google.gson.JsonObject;

public class DefaultJSON {
    public static JsonObject DefaultJSONWriter(JsonObject json) {

        json.addProperty("Test", true);

        return json;
    }
}
