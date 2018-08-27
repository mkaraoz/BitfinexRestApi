package com.duvarapps.bitfinexapi.v2.deserializer;

import com.duvarapps.bitfinexapi.v2.pojo.Candle;
import com.duvarapps.bitfinexapi.v2.pojo.CandleHolder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CandleDeserializer implements JsonDeserializer<CandleHolder>
{
    @Override
    public CandleHolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        CandleHolder candleHolder = new CandleHolder();
        List<Candle> candleList = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        Object obj = jsonArray.get(0);
        if (obj instanceof JsonArray) // response with Section = "hist"
        {
            for (int i = 0; i < jsonArray.size(); i++)
            {
                JsonArray array = jsonArray.get(i).getAsJsonArray();
                Candle candle = createCandle(array);
                candleList.add(candle);
            }
        }
        else // response with Section = "last"
        {
            Candle candle = createCandle(jsonArray);
            candleList.add(candle);
        }

        candleHolder.setCandleList(candleList);
        return candleHolder;
    }

    private Candle createCandle(JsonArray jsonArray)
    {
        /*
         * 0. MTS	    int	    millisecond time stamp
         * 1. OPEN	    float	First execution during the time frame
         * 2. CLOSE	    float	Last execution during the time frame
         * 3. HIGH	    float	Highest execution during the time frame
         * 4. LOW	    float	Lowest execution during the timeframe
         * 5. VOLUME	float	Quantity of symbol traded within the timeframe
         */
        Candle candle = new Candle();
        candle.setMts(jsonArray.get(0).getAsLong());
        candle.setOpen(jsonArray.get(1).getAsDouble());
        candle.setClose(jsonArray.get(2).getAsDouble());
        candle.setHigh(jsonArray.get(3).getAsDouble());
        candle.setLow(jsonArray.get(4).getAsDouble());
        candle.setVolume(jsonArray.get(5).getAsDouble());
        return candle;
    }
}
