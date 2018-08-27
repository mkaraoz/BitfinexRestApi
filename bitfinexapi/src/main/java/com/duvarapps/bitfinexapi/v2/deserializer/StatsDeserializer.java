package com.duvarapps.bitfinexapi.v2.deserializer;

import com.duvarapps.bitfinexapi.v2.pojo.StatsHolder;
import com.duvarapps.bitfinexapi.v2.pojo.Stats;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatsDeserializer implements JsonDeserializer<StatsHolder>
{
    @Override
    public StatsHolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        StatsHolder statsHolder = new StatsHolder();
        List<Stats> statsList = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        Object obj = jsonArray.get(0);
        if (obj instanceof JsonArray) // response with Section = "hist"
        {
            for (int i = 0; i < jsonArray.size(); i++)
            {
                JsonArray array = jsonArray.get(i).getAsJsonArray();
                Stats s = createStats(array);
                statsList.add(s);
            }
        }
        else // response with Section = "last"
        {
            Stats stats = createStats(jsonArray);
            statsList.add(stats);
        }

        statsHolder.setStatsList(statsList);
        return statsHolder;
    }

    private Stats createStats(JsonArray jsonArray)
    {
        /*
         * 0. MTS     long    millisecond timestamp
         * 1. VALUE   float	Total amount
         */
        Stats stats = new Stats();
        stats.setMts(jsonArray.get(0).getAsLong());
        stats.setValue(jsonArray.get(1).getAsDouble());
        return stats;
    }
}
