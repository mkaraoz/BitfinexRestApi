package com.duvarapps.bitfinexapi.v2.api;

import android.support.annotation.NonNull;

import com.duvarapps.bitfinexapi.core.ApiResponse;
import com.duvarapps.bitfinexapi.core.BitfinexApiCallback;
import com.duvarapps.bitfinexapi.core.Result;
import com.duvarapps.bitfinexapi.v2.pojo.Candle;
import com.duvarapps.bitfinexapi.v2.pojo.CandleHolder;
import com.duvarapps.bitfinexapi.v2.pojo.CurrencyTrades;
import com.duvarapps.bitfinexapi.v2.pojo.FundingCurrency;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBook;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBookFunding;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBookTrading;
import com.duvarapps.bitfinexapi.v2.pojo.PairTradeHistory;
import com.duvarapps.bitfinexapi.v2.pojo.PlatformStatus;
import com.duvarapps.bitfinexapi.v2.pojo.StatsHolder;
import com.duvarapps.bitfinexapi.v2.pojo.Stats;
import com.duvarapps.bitfinexapi.v2.pojo.Ticker;
import com.duvarapps.bitfinexapi.v2.pojo.Tickers;
import com.duvarapps.bitfinexapi.v2.pojo.Trade;
import com.duvarapps.bitfinexapi.v2.pojo.TradingPair;
import com.duvarapps.bitfinexapi.helper.candle_key.AggregateFundingCandleKey;
import com.duvarapps.bitfinexapi.helper.candle_key.CandleKey;
import com.duvarapps.bitfinexapi.helper.candle_key.FundingCandleKey;
import com.duvarapps.bitfinexapi.helper.candle_key.TradingCandleKey;
import com.duvarapps.bitfinexapi.helper.TextOps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.StatsKey.CREDITS_SIZE;
import static com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.StatsKey.CREDITS_SIZE_SYM;
import static com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.StatsKey.FUNDING_SIZE;
import static com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.StatsKey.POS_SIZE;
import static com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.*;

class Bitfinex2 implements BitfinexApi2
{
    private static final String BITFINEX_API_BASE_URL_V2 = "https://api.bitfinex.com/v2/";
    private final BitfinexService2 mBitfinexApi;
    private final int CODE_SUCCESS = 200;

    private Bitfinex2(BitfinexService2 bitfinexApi)
    {
        mBitfinexApi = bitfinexApi;
    }

    @NonNull
    public static Bitfinex2 publicEndpoint()
    {
        Retrofit retrofit = RetrofitClient2.getClient(BITFINEX_API_BASE_URL_V2);
        return new Bitfinex2(retrofit.create(BitfinexService2.class));
    }

    @NonNull
    public static Bitfinex2 publicEndpoint(Retrofit retrofit)
    {
        return new Bitfinex2(retrofit.create(BitfinexService2.class));
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

    // Platform Status
    @Override
    public Result<PlatformStatus> pullPlatformStatus() throws IOException
    {
        Call<PlatformStatus> call = mBitfinexApi.getPlatformStatus();
        return pull(call);
    }

    @Override
    public void getPlatformStatus(final BitfinexApiCallback<PlatformStatus> userCallback)
    {
        Call<PlatformStatus> call = mBitfinexApi.getPlatformStatus();
        get(call, userCallback);
    }

    // Tickers
    @Override
    public Result<Tickers> pullTickers(@NonNull String[] symbols) throws IOException
    {
        Call<Tickers> call = mBitfinexApi.getTickers(TextOps.makeCommaSeparated(symbols));
        return pull(call);
    }

    @Override
    public void getTickers(@NonNull String[] symbols, final BitfinexApiCallback<Tickers> userCallback)
    {
        Call<Tickers> call = mBitfinexApi.getTickers(TextOps.makeCommaSeparated(symbols));
        get(call, userCallback);
    }

    @Override
    public Result<Tickers> pullAllTickers() throws IOException
    {
        return pullTickers(new String[]{"ALL"});
    }

    @Override
    public void getAllTickers(final BitfinexApiCallback<Tickers> userCallback)
    {
        getTickers(new String[]{"ALL"}, userCallback);
    }

    @Override
    public Result<List<TradingPair>> pullTradingPairs(@NonNull String[] symbols) throws IOException
    {
        Result<Tickers> tickersResult = pullTickers(symbols);

        List<TradingPair> tradingPairList = new ArrayList<>();
        if (tickersResult.responseCode == CODE_SUCCESS)
        {
            tradingPairList = tickersResult.body.getTradingPairs();
        }

        Result<List<TradingPair>> result = new Result<>(tickersResult.responseCode,
                tradingPairList);
        return result;
    }

    @Override
    public void getTradingPairs(@NonNull String[] symbols, final BitfinexApiCallback<List<TradingPair>> userCallback)
    {
        getTickers(symbols, new BitfinexApiCallback<Tickers>()
        {
            @Override
            public void onResponse(Tickers tickers, ApiResponse response)
            {
                List<TradingPair> tradingPairs = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    tradingPairs = tickers.getTradingPairs();
                }
                userCallback.onResponse(tradingPairs, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<List<FundingCurrency>> pullFundingCurrencies(@NonNull String[] symbols) throws IOException
    {
        Result<Tickers> tickersResult = pullTickers(symbols);
        List<FundingCurrency> fundingCurrencyList = new ArrayList<>();
        if (tickersResult.responseCode == CODE_SUCCESS)
        {
            fundingCurrencyList = tickersResult.body.getFundingCurrencies();
        }
        Result<List<FundingCurrency>> result = new Result<>(tickersResult.responseCode,
                fundingCurrencyList);
        return result;
    }

    @Override
    public void getFundingCurrencies(@NonNull String[] symbols, final BitfinexApiCallback<List<FundingCurrency>> userCallback)
    {
        getTickers(symbols, new BitfinexApiCallback<Tickers>()
        {
            @Override
            public void onResponse(Tickers tickers, ApiResponse response)
            {
                List<FundingCurrency> fundingCurrencies = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    fundingCurrencies = tickers.getFundingCurrencies();
                }
                userCallback.onResponse(fundingCurrencies, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    // Ticker
    @Override
    public Result<Ticker> pullTicker(@NonNull String symbol) throws IOException
    {
        Call<Ticker> call = mBitfinexApi.getTicker(symbol);
        Result<Ticker> tickerResult = pull(call);
        if (tickerResult.responseCode == CODE_SUCCESS)
        {
            if (symbol.startsWith("f"))
            {
                tickerResult.body.getFundingCurrency().setSymbol(symbol);
            }
            else if (symbol.startsWith("t"))
            {
                tickerResult.body.getTradingPair().setSymbol(symbol);
            }
        }
        return tickerResult;
    }

    @Override
    public void getTicker(@NonNull final String symbol, final BitfinexApiCallback<Ticker> userCallback)
    {
        Call<Ticker> call = mBitfinexApi.getTicker(symbol);
        get(call, new BitfinexApiCallback<Ticker>()
        {
            @Override
            public void onResponse(Ticker ticker, ApiResponse response)
            {
                if (response.code() == CODE_SUCCESS)
                {
                    if (symbol.startsWith("f"))
                    {
                        ticker.getFundingCurrency().setSymbol(symbol);
                    }
                    else if (symbol.startsWith("t"))
                    {
                        ticker.getTradingPair().setSymbol(symbol);
                    }
                }

                userCallback.onResponse(ticker, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<TradingPair> pullTradingPair(@NonNull String symbol) throws IOException
    {
        Result<Ticker> resultTicker = pullTicker(symbol);
        TradingPair tradingPair = null;
        if (resultTicker.responseCode == CODE_SUCCESS)
        {
            tradingPair = resultTicker.body.getTradingPair();
        }
        Result<TradingPair> result = new Result<>(resultTicker.responseCode, tradingPair);
        return result;
    }

    @Override
    public void getTradingPair(@NonNull String symbol, final BitfinexApiCallback<TradingPair> userCallback)
    {
        getTicker(symbol, new BitfinexApiCallback<Ticker>()
        {
            @Override
            public void onResponse(Ticker ticker, ApiResponse response)
            {
                TradingPair tradingPair = null;
                if (response.code() == CODE_SUCCESS)
                {
                    tradingPair = ticker.getTradingPair();
                }
                userCallback.onResponse(tradingPair, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<FundingCurrency> pullFundingCurrency(@NonNull String symbol) throws IOException
    {
        Result<Ticker> resultTicker = pullTicker(symbol);
        FundingCurrency fundingCurrency = null;
        if (resultTicker.responseCode == CODE_SUCCESS)
        {
            fundingCurrency = resultTicker.body.getFundingCurrency();
        }
        Result<FundingCurrency> result = new Result<>(resultTicker.responseCode, fundingCurrency);
        return result;
    }

    @Override
    public void getFundingCurrency(@NonNull String symbol, final BitfinexApiCallback<FundingCurrency> userCallback)
    {
        getTicker(symbol, new BitfinexApiCallback<Ticker>()
        {
            @Override
            public void onResponse(Ticker ticker, ApiResponse response)
            {
                FundingCurrency fundingCurrency = null;
                if (response.code() == CODE_SUCCESS)
                {
                    fundingCurrency = ticker.getFundingCurrency();
                }
                userCallback.onResponse(fundingCurrency, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    // Trades
    private Result<Trade> pullTrades(String symbol, Integer limit, Long start, Long end, Integer sort) throws IOException
    {
        Call<Trade> call = mBitfinexApi.getTrades(symbol, limit, start, end, sort);
        return pull(call);
    }

    private void getTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort, final BitfinexApiCallback<Trade> callback)
    {
        Call<Trade> call = mBitfinexApi.getTrades(symbol, limit, start, end, sort);
        get(call, new BitfinexApiCallback<Trade>()
        {
            @Override
            public void onResponse(Trade trade, ApiResponse response)
            {
                callback.onResponse(trade, response);
            }

            @Override
            public void onError(Throwable t)
            {
                callback.onError(t);
            }
        });
    }

    @Override
    public Result<List<CurrencyTrades>> pullCurrencyTrades(String symbol, Integer limit, Long start, Long end, Integer sort) throws IOException
    {
        Result<Trade> tradeResult = pullTrades(symbol, limit, start, end, sort);
        List<CurrencyTrades> currencyTrades = new ArrayList<>();
        if (tradeResult.responseCode == CODE_SUCCESS)
        {
            currencyTrades = tradeResult.body.getCurrencyTrades();
        }
        Result<List<CurrencyTrades>> result = new Result<>(tradeResult.responseCode,
                currencyTrades);
        return result;
    }

    @Override
    public void getCurrencyTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort, final BitfinexApiCallback<List<CurrencyTrades>> userCallback)
    {
        getTrades(symbol, limit, start, end, sort, new BitfinexApiCallback<Trade>()
        {
            @Override
            public void onResponse(Trade trade, ApiResponse response)
            {
                List<CurrencyTrades> currencyTrades = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    currencyTrades = trade.getCurrencyTrades();
                }
                userCallback.onResponse(currencyTrades, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<List<CurrencyTrades>> pullCurrencyTrades(@NonNull String symbol) throws IOException
    {
        return pullCurrencyTrades(symbol, null, null, null, null);
    }

    @Override
    public void getCurrencyTrades(@NonNull String symbol, BitfinexApiCallback<List<CurrencyTrades>> userCallback)
    {
        getCurrencyTrades(symbol, null, null, null, null, userCallback);
    }

    @Override
    public Result<List<PairTradeHistory>> pullPairTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort) throws IOException
    {
        Result<Trade> tradeResult = pullTrades(symbol, limit, start, end, sort);
        List<PairTradeHistory> pairTradeHistoryList = new ArrayList<>();
        if (tradeResult.responseCode == CODE_SUCCESS)
        {
            pairTradeHistoryList = tradeResult.body.getPairTradeHistory();
        }
        Result<List<PairTradeHistory>> result = new Result<>(tradeResult.responseCode,
                pairTradeHistoryList);
        return result;
    }

    @Override
    public void getPairTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort, final BitfinexApiCallback<List<PairTradeHistory>> userCallback)
    {
        getTrades(symbol, limit, start, end, sort, new BitfinexApiCallback<Trade>()
        {
            @Override
            public void onResponse(Trade trade, ApiResponse response)
            {
                List<PairTradeHistory> pairTradeHistoryList = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    pairTradeHistoryList = trade.getPairTradeHistory();
                }
                userCallback.onResponse(pairTradeHistoryList, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<List<PairTradeHistory>> pullPairTrades(@NonNull String symbol) throws IOException
    {
        return pullPairTrades(symbol, null, null, null, null);
    }

    @Override
    public void getPairTrades(@NonNull String symbol, BitfinexApiCallback<List<PairTradeHistory>> userCallback)
    {
        getPairTrades(symbol, null, null, null, null, userCallback);
    }

    // Books
    private Result<OrderBook> pullBooks(@NonNull String symbol, PriceAggregation priceAggregation, Integer len) throws IOException
    {
        Call<OrderBook> call = mBitfinexApi.getOrderBooks(symbol, priceAggregation.level(), len);
        return pull(call);
    }

    private void getBooks(@NonNull String symbol, PriceAggregation priceAggregation, Integer len, final BitfinexApiCallback<OrderBook> callback)
    {
        Call<OrderBook> call = mBitfinexApi.getOrderBooks(symbol, priceAggregation.level(), len);
        get(call, new BitfinexApiCallback<OrderBook>()
        {
            @Override
            public void onResponse(OrderBook result, ApiResponse response)
            {
                callback.onResponse(result, response);
            }

            @Override
            public void onError(Throwable t)
            {
                callback.onError(t);
            }
        });
    }

    @Override
    public Result<List<OrderBookTrading>> pullOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation, Integer len) throws IOException
    {
        Result<OrderBook> tradeResult = pullBooks(symbol, priceAggregation, len);
        List<OrderBookTrading> orderBooks = new ArrayList<>();
        if (tradeResult.responseCode == CODE_SUCCESS)
        {
            orderBooks = tradeResult.body.getTradingBooks();
        }
        Result<List<OrderBookTrading>> result = new Result<>(tradeResult.responseCode, orderBooks);
        return result;
    }

    @Override
    public void getOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation, Integer len, final BitfinexApiCallback<List<OrderBookTrading>> userCallback)
    {
        getBooks(symbol, priceAggregation, len, new BitfinexApiCallback<OrderBook>()
        {
            @Override
            public void onResponse(OrderBook result, ApiResponse response)
            {
                List<OrderBookTrading> orderBookTradingList = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    orderBookTradingList = result.getTradingBooks();
                }
                userCallback.onResponse(orderBookTradingList, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<List<OrderBookTrading>> pullOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation) throws IOException
    {
        return pullOrderBooksTraining(symbol, priceAggregation, null);
    }

    @Override
    public void getOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation, BitfinexApiCallback<List<OrderBookTrading>> userCallback)
    {
        getOrderBooksTraining(symbol, priceAggregation, null, userCallback);
    }

    @Override
    public Result<List<OrderBookFunding>> pullOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation, Integer len) throws IOException
    {
        Result<OrderBook> tradeResult = pullBooks(symbol, priceAggregation, len);
        List<OrderBookFunding> orderBooks = new ArrayList<>();
        if (tradeResult.responseCode == CODE_SUCCESS)
        {
            orderBooks = tradeResult.body.getFundingBooks();
        }
        Result<List<OrderBookFunding>> result = new Result<>(tradeResult.responseCode, orderBooks);
        return result;
    }

    @Override
    public void getOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation, Integer len, final BitfinexApiCallback<List<OrderBookFunding>> userCallback)
    {
        getBooks(symbol, priceAggregation, len, new BitfinexApiCallback<OrderBook>()
        {
            @Override
            public void onResponse(OrderBook result, ApiResponse response)
            {
                List<OrderBookFunding> orderBookFundingList = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    orderBookFundingList = result.getFundingBooks();
                }
                userCallback.onResponse(orderBookFundingList, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<List<OrderBookFunding>> pullOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation) throws IOException
    {
        return pullOrderBookFunding(symbol, priceAggregation, null);
    }

    @Override
    public void getOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation, BitfinexApiCallback<List<OrderBookFunding>> userCallback)
    {
        getOrderBookFunding(symbol, priceAggregation, null, userCallback);
    }

    // Stats
    @Override
    public Result<List<Stats>> pullStats(final String key, final String section, final int sortOrder) throws IOException
    {
        Call<StatsHolder> call = mBitfinexApi.getStats(key, section, sortOrder);
        Result<StatsHolder> statsHolderResult = pull(call);
        List<Stats> stats = new ArrayList<>();
        if (statsHolderResult.responseCode == CODE_SUCCESS)
        {
            stats = statsHolderResult.body.getStatsList();
        }
        Result<List<Stats>> result = new Result<>(statsHolderResult.responseCode, stats);
        return result;
    }

    @Override
    public void getStats(String key, String section, int sortOrder, final BitfinexApiCallback<List<Stats>> userCallback)
    {
        Call<StatsHolder> call = mBitfinexApi.getStats(key, section, sortOrder);
        get(call, new BitfinexApiCallback<StatsHolder>()
        {
            @Override
            public void onResponse(StatsHolder result, ApiResponse response)
            {
                List<Stats> stats = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    stats = result.getStatsList();
                }
                userCallback.onResponse(stats, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<List<Stats>> pullStatsTotalOpenPositions(@NonNull String symbolTrading, Side side, Section section, SortOrder sortOrder) throws IOException
    {
        final String key = POS_SIZE.toString() + ":" + "1m" + ":" + symbolTrading + ":" + side.toString();
        return pullStats(key, section.toString(), sortOrder.order());
    }

    @Override
    public void getStatsTotalOpenPositions(@NonNull String symbolTrading, Side side, Section section, SortOrder sortOrder, BitfinexApiCallback<List<Stats>> userCallback)
    {
        final String key = POS_SIZE.toString() + ":" + "1m" + ":" + symbolTrading + ":" + side.toString();
        getStats(key, section.toString(), sortOrder.order(), userCallback);
    }

    @Override
    public Result<List<Stats>> pullStatsTotalActiveFunding(@NonNull String symbolFunding, Section section, SortOrder sortOrder) throws IOException
    {
        final String key = FUNDING_SIZE.toString() + ":" + "1m" + ":" + symbolFunding;
        return pullStats(key, section.toString(), sortOrder.order());
    }

    @Override
    public void getStatsTotalActiveFunding(@NonNull String symbolFunding, Section section, SortOrder sortOrder, BitfinexApiCallback<List<Stats>> userCallback)
    {
        final String key = FUNDING_SIZE.toString() + ":" + "1m" + ":" + symbolFunding;
        getStats(key, section.toString(), sortOrder.order(), userCallback);
    }

    @Override
    public Result<List<Stats>> pullStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, Section section, SortOrder sortOrder) throws IOException
    {
        final String key = CREDITS_SIZE.toString() + ":" + "1m" + ":" + symbolFunding;
        return pullStats(key, section.toString(), sortOrder.order());
    }

    @Override
    public void getStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, Section section, SortOrder sortOrder, BitfinexApiCallback<List<Stats>> userCallback)
    {
        final String key = CREDITS_SIZE.toString() + ":" + "1m" + ":" + symbolFunding;
        getStats(key, section.toString(), sortOrder.order(), userCallback);
    }

    @Override
    public Result<List<Stats>> pullStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, @NonNull String symbolTrading, Section section, SortOrder sortOrder) throws IOException
    {
        final String key = CREDITS_SIZE_SYM.toString() + ":" + "1m" + ":" + symbolFunding + ":" + symbolTrading;
        return pullStats(key, section.toString(), sortOrder.order());
    }

    @Override
    public void getStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, @NonNull String symbolTrading, Section section, SortOrder sortOrder, BitfinexApiCallback<List<Stats>> userCallback)
    {
        final String key = CREDITS_SIZE_SYM.toString() + ":" + "1m" + ":" + symbolFunding + ":" + symbolTrading;
        getStats(key, section.toString(), sortOrder.order(), userCallback);
    }

    // Candles
    @Override
    public Result<List<Candle>> pullCandles(String key, String section, Integer limit, Long start, Long end, Integer sortOrder) throws IOException
    {
        Call<CandleHolder> call = mBitfinexApi.getCandles(key, section, limit, start, end,
                sortOrder);
        Result<CandleHolder> candleHolderResult = pull(call);

        List<Candle> candleList = new ArrayList<>();
        if (candleHolderResult.responseCode == CODE_SUCCESS)
        {
            candleList = candleHolderResult.body.getCandleList();
        }

        Result<List<Candle>> result = new Result<>(candleHolderResult.responseCode, candleList);
        return result;
    }

    @Override
    public void getCandles(String key, String section, Integer limit, Long start, Long end, Integer sortOrder, final BitfinexApiCallback<List<Candle>> userCallback)
    {
        Call<CandleHolder> call = mBitfinexApi.getCandles(key, section, limit, start, end,
                sortOrder);
        get(call, new BitfinexApiCallback<CandleHolder>()
        {
            @Override
            public void onResponse(CandleHolder result, ApiResponse response)
            {
                List<Candle> candles = new ArrayList<>();
                if (response.code() == CODE_SUCCESS)
                {
                    candles = result.getCandleList();
                }
                userCallback.onResponse(candles, response);
            }

            @Override
            public void onError(Throwable t)
            {
                userCallback.onError(t);
            }
        });
    }

    @Override
    public Result<List<Candle>> pullTradingCandles(TradingCandleKey key, Section section, SortOrder sortOrder) throws IOException
    {
        return pullCandles(key.getKey(), section.toString(), null, null, null, sortOrder.order());
    }

    @Override
    public void getTradingCandles(TradingCandleKey key, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Candle>> userCallback)
    {
        getSubCandles(key, section, sortOrder, userCallback);
    }

    @Override
    public Result<List<Candle>> pullFundingCandles(FundingCandleKey key, Section section, SortOrder sortOrder) throws IOException
    {
        return pullCandles(key.getKey(), section.toString(), null, null, null, sortOrder.order());
    }

    @Override
    public void getFundingCandles(FundingCandleKey key, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Candle>> userCallback)
    {
        getSubCandles(key, section, sortOrder, userCallback);
    }

    @Override
    public Result<List<Candle>> pullAggregateFundingCandles(AggregateFundingCandleKey key, Section section, SortOrder sortOrder) throws IOException
    {
        return pullCandles(key.getKey(), section.toString(), null, null, null, sortOrder.order());
    }

    @Override
    public void getAggregateFundingCandles(AggregateFundingCandleKey key, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Candle>> userCallback)
    {
        getSubCandles(key, section, sortOrder, userCallback);
    }

    private void getSubCandles(CandleKey key, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Candle>> userCallback)
    {
        getCandles(key.getKey(), section.toString(), null, null, null, sortOrder.order(),
                new BitfinexApiCallback<List<Candle>>()
                {
                    @Override
                    public void onResponse(List<Candle> candles, ApiResponse response)
                    {
                        userCallback.onResponse(candles, response);
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        userCallback.onError(t);
                    }
                });
    }
}
