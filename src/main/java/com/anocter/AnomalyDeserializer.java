package com.anocter;

import com.google.gson.*;
import org.opencv.core.Mat;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AnomalyDeserializer implements JsonDeserializer<Anomaly> {
    @Override
    public Anomaly deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObj = json.getAsJsonObject();

        Instant capturedTime = LocalDateTime.parse(jsonObj.get("capturedTime").getAsString(), Globals.formatter)
                .atZone(ZoneId.systemDefault()).toInstant();

        double movement = jsonObj.get("movement").getAsDouble();

        return new Anomaly(new Mat(), capturedTime, movement);
    }
}
