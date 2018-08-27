package com.duvarapps.bitfinexapi.v1.api;

import android.support.annotation.NonNull;

import com.duvarapps.bitfinexapi.core.ApiResponse;
import com.duvarapps.bitfinexapi.core.BitfinexApiCallback;
import com.duvarapps.bitfinexapi.core.Result;
import com.duvarapps.bitfinexapi.v1.pojo.FundingBook;
import com.duvarapps.bitfinexapi.v1.pojo.Lend;
import com.duvarapps.bitfinexapi.v1.pojo.OrderBook;
import com.duvarapps.bitfinexapi.v1.pojo.Stats;
import com.duvarapps.bitfinexapi.v1.pojo.SymbolDetail;
import com.duvarapps.bitfinexapi.v1.pojo.Ticker;
import com.duvarapps.bitfinexapi.v1.pojo.Trade;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

class Bitfinex implements BitfinexApi
{
    private static final String BITFINEX_API_BASE_URL = "https://api.bitfinex.com/v1/";
    private final BitfinexService mBitfinexApi;

    private Bitfinex(BitfinexService bitfinexApi)
    {
        mBitfinexApi = bitfinexApi;
    }

    @NonNull
    public static Bitfinex publicEndpoint()
    {
        Retrofit retrofit = RetrofitClient.getClient(BITFINEX_API_BASE_URL);
        return new Bitfinex(retrofit.create(BitfinexService.class));
    }

    @NonNull
    public static Bitfinex publicEndpoint(Retrofit retrofit)
    {
        return new Bitfinex(retrofit.create(BitfinexService.class));
    }

    private <T> Result<T> pull(Call<T> call) throws IOException
    {
        Response<T> response = call.execute();
        return new Result<>(response.code(), response.body());
    }

    private <T> void get(final Call<T> c, final BitfinexApiCallback<T> userCallback)
    {
        c.enqueue(new Callback<T>()
        {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response)
            {
                T t = response.body();

                ApiResponse res = new ApiResponse(response.raw(), response.errorBody());
                userCallback.onResponse(t, res);
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable)
            {
                userCallback.onError(throwable);
            }
        });
    }

    @Override
    public Result<List<String>> pullSymbols() throws IOException
    {
        Call<List<String>> call = mBitfinexApi.getSymbols();
        return pull(call);
    }

    @Override
    public void getSymbols(final BitfinexApiCallback<List<String>> userCallback)
    {
        Call<List<String>> call = mBitfinexApi.getSymbols();
        get(call, userCallback);
    }

    @Override
    public Result<Ticker> pullTicker(final String symbol) throws IOException
    {
        Call<Ticker> call = mBitfinexApi.getTicker(symbol);
        return pull(call);
    }

    @Override
    public void getTicker(final String symbol, final BitfinexApiCallback<Ticker> userCallback)
    {
        Call<Ticker> call = mBitfinexApi.getTicker(symbol);
        get(call, userCallback);
    }

    @Override
    public Result<List<Stats>> pullStats(final String symbol) throws IOException
    {
        Call<List<Stats>> call = mBitfinexApi.getStats(symbol);
        return pull(call);
    }

    @Override
    public void getStats(final String symbol, final BitfinexApiCallback<List<Stats>> userCallback)
    {
        Call<List<Stats>> call = mBitfinexApi.getStats(symbol);
        get(call, userCallback);
    }

    @Override
    public Result<FundingBook> pullFundingBook(String currency) throws IOException
    {
        Call<FundingBook> call = mBitfinexApi.getFundingBook(currency);
        return pull(call);
    }

    @Override
    public void getFundingBook(String currency, BitfinexApiCallback<FundingBook> userCallback)
    {
        Call<FundingBook> call = mBitfinexApi.getFundingBook(currency);
        get(call, userCallback);
    }

    @Override
    public Result<OrderBook> pullOrderBook(String symbol) throws IOException
    {
        Call<OrderBook> call = mBitfinexApi.getOrderBook(symbol);
        return pull(call);
    }

    @Override
    public void getOrderBook(String symbol, BitfinexApiCallback<OrderBook> userCallback)
    {
        Call<OrderBook> call = mBitfinexApi.getOrderBook(symbol);
        get(call, userCallback);
    }

    @Override
    public Result<List<Trade>> pullTrades(String symbol) throws IOException
    {
        Call<List<Trade>> call = mBitfinexApi.getTrades(symbol);
        return pull(call);
    }

    @Override
    public void getTrades(String symbol, BitfinexApiCallback<List<Trade>> userCallback)
    {
        Call<List<Trade>> call = mBitfinexApi.getTrades(symbol);
        get(call, userCallback);
    }

    @Override
    public Result<List<Lend>> pullLend(String currency) throws IOException
    {
        Call<List<Lend>> call = mBitfinexApi.getLends(currency);
        return pull(call);
    }

    @Override
    public void getLend(String currency, BitfinexApiCallback<List<Lend>> userCallback)
    {
        Call<List<Lend>> call = mBitfinexApi.getLends(currency);
        get(call, userCallback);
    }

    @Override
    public Result<List<SymbolDetail>> pullSymbolDetails() throws IOException
    {
        Call<List<SymbolDetail>> call = mBitfinexApi.getSymbolDetails();
        return pull(call);
    }

    @Override
    public void getSymbolDetails(BitfinexApiCallback<List<SymbolDetail>> userCallback)
    {
        Call<List<SymbolDetail>> call = mBitfinexApi.getSymbolDetails();
        get(call, userCallback);
    }
}
