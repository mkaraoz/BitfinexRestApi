package com.duvarapps.bitfinexapi.v1.api;

import com.duvarapps.bitfinexapi.v1.pojo.FundingBook;
import com.duvarapps.bitfinexapi.v1.pojo.Lend;
import com.duvarapps.bitfinexapi.v1.pojo.OrderBook;
import com.duvarapps.bitfinexapi.v1.pojo.Stats;
import com.duvarapps.bitfinexapi.v1.pojo.SymbolDetail;
import com.duvarapps.bitfinexapi.v1.pojo.Ticker;
import com.duvarapps.bitfinexapi.v1.pojo.Trade;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface BitfinexService
{
    @GET("symbols")
    Call<List<String>> getSymbols();

    @GET("pubticker/{symbol}")
    Call<Ticker> getTicker(@Path("symbol") String symbol);

    @GET("stats/{symbol}")
    Call<List<Stats>> getStats(@Path("symbol") String symbol);

    @GET("lendbook/{currency}")
    Call<FundingBook> getFundingBook(@Path("currency") String currency);

    @GET("book/{symbol}")
    Call<OrderBook> getOrderBook(@Path("symbol") String symbol);

    @GET("trades/{symbol}")
    Call<List<Trade>> getTrades(@Path("symbol") String symbol);

    @GET("lends/{currency}")
    Call<List<Lend>> getLends(@Path("currency") String currency);

    @GET("symbols_details")
    Call<List<SymbolDetail>> getSymbolDetails();
}
