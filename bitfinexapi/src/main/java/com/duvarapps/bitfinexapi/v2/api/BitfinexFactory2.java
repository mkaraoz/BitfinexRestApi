package com.duvarapps.bitfinexapi.v2.api;

import retrofit2.Retrofit;

public class BitfinexFactory2
{
    public static BitfinexApi2 createClient()
    {
        return Bitfinex2.publicEndpoint();
    }

    public static BitfinexApi2 createClient(Retrofit retrofit)
    {
        return Bitfinex2.publicEndpoint(retrofit);
    }
}
