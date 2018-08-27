package com.duvarapps.bitfinexapi.v2.api;

import com.duvarapps.bitfinexapi.v2.deserializer.CandleDeserializer;
import com.duvarapps.bitfinexapi.v2.deserializer.OrderBookDeserializer;
import com.duvarapps.bitfinexapi.v2.deserializer.PlatformStatusDeserializer;
import com.duvarapps.bitfinexapi.v2.deserializer.StatsDeserializer;
import com.duvarapps.bitfinexapi.v2.deserializer.TickerDeserializer;
import com.duvarapps.bitfinexapi.v2.deserializer.TickersDeserializer;
import com.duvarapps.bitfinexapi.v2.deserializer.TradeDeserializer;
import com.duvarapps.bitfinexapi.v2.pojo.CandleHolder;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBook;
import com.duvarapps.bitfinexapi.v2.pojo.PlatformStatus;
import com.duvarapps.bitfinexapi.v2.pojo.StatsHolder;
import com.duvarapps.bitfinexapi.v2.pojo.Ticker;
import com.duvarapps.bitfinexapi.v2.pojo.Tickers;
import com.duvarapps.bitfinexapi.v2.pojo.Trade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

class RetrofitClient2
{
    private static Retrofit retrofit = null;

    static Retrofit getClient(final String url)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(buildGsonConverter()).build();
        }
        return retrofit;
    }

    private static GsonConverterFactory buildGsonConverter()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PlatformStatus.class, new PlatformStatusDeserializer());
        gsonBuilder.registerTypeAdapter(Tickers.class, new TickersDeserializer());
        gsonBuilder.registerTypeAdapter(Ticker.class, new TickerDeserializer());
        gsonBuilder.registerTypeAdapter(Trade.class, new TradeDeserializer());
        gsonBuilder.registerTypeAdapter(OrderBook.class, new OrderBookDeserializer());
        gsonBuilder.registerTypeAdapter(StatsHolder.class, new StatsDeserializer());
        gsonBuilder.registerTypeAdapter(CandleHolder.class, new CandleDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }
}
