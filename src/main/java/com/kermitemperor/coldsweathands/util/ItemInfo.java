package com.kermitemperor.coldsweathands.util;


import com.google.gson.JsonObject;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.world.item.Item;


import java.util.Objects;

import static com.kermitemperor.coldsweathands.config.ConfigHandler.CONFIG;

@SuppressWarnings("unused")
public class ItemInfo {
    private final String registryName;
    private JsonObject info;
    private Temperature.Units measure;
    private Boolean clickable;
    private Integer max;
    private Integer min;

    public ItemInfo(Item itemRegistry) {
        this.registryName = Objects.requireNonNull(itemRegistry.getRegistryName()).toString();
        if (CONFIG.has(this.registryName)) {
            this.info = CONFIG.getAsJsonObject(this.registryName);

            this.measure = this.info.has("Measure") ?
                    Temperature.Units.valueOf(this.info.get("Measure").getAsString().toUpperCase())  :
                    Temperature.Units.valueOf(CONFIG.get("default_measure").getAsString().toUpperCase());


            this.clickable = this.info.has("Clickable") ?
                    this.info.get("Clickable").getAsBoolean() :
                    null;

            this.max = this.info.has("Max") ?
                    this.info.get("Max").getAsInt() :
                    null;

            this.min = this.info.has("Min") ?
                    this.info.get("Min").getAsInt() :
                    null;


        }
    }

    public String getRegistryName() {
        return this.registryName;
    }

    public JsonObject getInfo() {
        return this.info;
    }

    public Temperature.Units getMeasure() {
        return this.measure;
    }

    public Boolean getClickable() {
        return this.clickable;
    }

    public Integer getMax() {
        return this.max;
    }

    public Integer getMin() {
        return this.min;
    }
}
