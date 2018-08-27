package com.duvarapps.bitfinexapi.v1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

class RetrofitClient
{
    private static Retrofit retrofit = null;

    static Retrofit getClient(final String url)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
