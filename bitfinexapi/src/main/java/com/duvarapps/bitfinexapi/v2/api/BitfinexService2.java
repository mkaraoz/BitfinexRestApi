package com.duvarapps.bitfinexapi.v2.api;

import com.duvarapps.bitfinexapi.v2.pojo.CandleHolder;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBook;
import com.duvarapps.bitfinexapi.v2.pojo.PlatformStatus;
import com.duvarapps.bitfinexapi.v2.pojo.StatsHolder;
import com.duvarapps.bitfinexapi.v2.pojo.Ticker;
import com.duvarapps.bitfinexapi.v2.pojo.Tickers;
import com.duvarapps.bitfinexapi.v2.pojo.Trade;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface BitfinexService2
{
    @GET("platform/status")
    Call<PlatformStatus> getPlatformStatus();

    @GET("tickers")
    Call<Tickers> getTickers(@Query("symbols") String symbols);

    @GET("ticker/{symbol}")
    Call<Ticker> getTicker(@Path("symbol") String symbol);

    @GET("trades/{symbol}/hist")
    Call<Trade> getTrades(
            @Path("symbol") String symbol,
            @Query("limit") Integer limit,
            @Query("start") Long start,
            @Query("end") Long end,
            @Query("sort") Integer sort);

    @GET("book/{symbol}/{precision}")
    Call<OrderBook> getOrderBooks(
            @Path("symbol") String symbol,
            @Path("precision") String precision,
            @Query("len") Integer len);

    @GET("stats1/{key}/{section}")
    Call<StatsHolder> getStats(
            @Path("key") String key,
            @Path("section") String section,
            @Query("sort") Integer sort);

    @GET("candles/{key}/{section}")
    Call<CandleHolder> getCandles(
            @Path("key") String key,
            @Path("section") String section,
            @Query("limit") Integer limit,
            @Query("start") Long start,
            @Query("end") Long end,
            @Query("sort") Integer sort);

}
