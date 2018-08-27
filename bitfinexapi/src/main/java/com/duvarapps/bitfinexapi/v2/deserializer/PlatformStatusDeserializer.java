package com.duvarapps.bitfinexapi.v2.deserializer;

import com.duvarapps.bitfinexapi.v2.pojo.PlatformStatus;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PlatformStatusDeserializer implements JsonDeserializer<PlatformStatus>
{
    @Override
    public PlatformStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonArray jsonArray = json.getAsJsonArray();
        int status = jsonArray.get(0).getAsInt();
        PlatformStatus ps = new PlatformStatus();
        ps.setStatus(status);
        return ps;
    }
}
