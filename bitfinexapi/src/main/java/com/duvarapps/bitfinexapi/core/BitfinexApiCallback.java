package com.duvarapps.bitfinexapi.core;

public interface BitfinexApiCallback<B>
{
    void onResponse(B result, ApiResponse response);

    void onError(Throwable t);
}
