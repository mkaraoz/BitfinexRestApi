package com.duvarapps.bitfinexapi.v1.api;

import retrofit2.Retrofit;

public class BitfinexFactory
{
    public static BitfinexApi createClient()
    {
        return Bitfinex.publicEndpoint();
    }

    public static BitfinexApi createClient(Retrofit retrofit)
    {
        return Bitfinex.publicEndpoint(retrofit);
    }
}
