package com.duvarapps.bitfinexapi.v1;

import com.duvarapps.bitfinexapi.helper.FakeInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FakeBitfinexClient1
{
    private static final String BITFINEX_API_BASE_URL = "https://api.bitfinex.com/v2/";

    public static Retrofit getFakeClient(final String fakeResponse)
    {
        // ***YOUR CUSTOM INTERCEPTOR GOES HERE***
        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                new FakeInterceptor(fakeResponse)).build();

        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                BITFINEX_API_BASE_URL).client(client).build();
    }
}
