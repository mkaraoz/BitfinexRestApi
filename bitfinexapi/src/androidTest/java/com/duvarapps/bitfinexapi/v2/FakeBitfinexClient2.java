package com.duvarapps.bitfinexapi.v2;

import com.duvarapps.bitfinexapi.helper.FakeInterceptor;
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

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class FakeBitfinexClient2
{
    private static final String BITFINEX_API_BASE_URL = "https://api.bitfinex.com/v2/";

    public static Retrofit getFakeClient(final String fakeResponse)
    {
        // ***YOUR CUSTOM INTERCEPTOR GOES HERE***
        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                new FakeInterceptor(fakeResponse)).build();

        return new Retrofit.Builder().addConverterFactory(
                buildGsonConverter()).baseUrl(BITFINEX_API_BASE_URL).client(client).build();
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
