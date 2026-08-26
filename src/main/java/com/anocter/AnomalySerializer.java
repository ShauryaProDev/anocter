package com.anocter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class AnomalySerializer implements JsonSerializer<Anomaly> {
    @Override
    public JsonElement serialize(Anomaly src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.addProperty("capturedTime", Globals.formatter.format(src.capturedTime()));
        json.addProperty("movement", src.movement());

        return json;
    }
}
