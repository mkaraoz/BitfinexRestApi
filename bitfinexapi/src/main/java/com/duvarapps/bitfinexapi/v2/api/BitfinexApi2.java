package com.duvarapps.bitfinexapi.v2.api;

import android.support.annotation.NonNull;

import com.duvarapps.bitfinexapi.core.BitfinexApiCallback;
import com.duvarapps.bitfinexapi.core.Result;
import com.duvarapps.bitfinexapi.v2.pojo.Candle;
import com.duvarapps.bitfinexapi.v2.pojo.CurrencyTrades;
import com.duvarapps.bitfinexapi.v2.pojo.FundingCurrency;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBookFunding;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBookTrading;
import com.duvarapps.bitfinexapi.v2.pojo.PairTradeHistory;
import com.duvarapps.bitfinexapi.v2.pojo.PlatformStatus;
import com.duvarapps.bitfinexapi.v2.pojo.Stats;
import com.duvarapps.bitfinexapi.v2.pojo.Ticker;
import com.duvarapps.bitfinexapi.v2.pojo.Tickers;
import com.duvarapps.bitfinexapi.v2.pojo.TradingPair;
import com.duvarapps.bitfinexapi.helper.candle_key.AggregateFundingCandleKey;
import com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.*;
import com.duvarapps.bitfinexapi.helper.candle_key.FundingCandleKey;
import com.duvarapps.bitfinexapi.helper.candle_key.TradingCandleKey;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("JavaDoc")
public interface BitfinexApi2
{
    /*
     * Platform Status
     *
     * Get the current status of the platform. Maintenance periods last for just few minutes and
     * might be necessary from time to time during upgrades of core components of our infrastructure
     *
     * see: https://docs.bitfinex.com/v2/reference#rest-public-platform-status
     */
    Result<PlatformStatus> pullPlatformStatus() throws IOException;

    void getPlatformStatus(final BitfinexApiCallback<PlatformStatus> userCallback);

    /*
     * Tickers
     *
     * The ticker is a high level overview of the state of the market. It shows you the current best
     * bid and ask, as well as the last trade price. It also includes information such as daily
     * volume and how much the price has moved over the last day.
     *
     * see: https://docs.bitfinex.com/v2/reference#rest-public-tickers
     */
    Result<Tickers> pullTickers(@NonNull String[] symbols) throws IOException;

    void getTickers(@NonNull String[] symbols, final BitfinexApiCallback<Tickers> userCallback);

    Result<Tickers> pullAllTickers() throws IOException;

    void getAllTickers(final BitfinexApiCallback<Tickers> userCallback);

    Result<List<TradingPair>> pullTradingPairs(@NonNull String[] symbols) throws IOException;

    void getTradingPairs(@NonNull String[] symbols, final BitfinexApiCallback<List<TradingPair>> userCallback);

    Result<List<FundingCurrency>> pullFundingCurrencies(@NonNull String[] symbols) throws IOException;

    void getFundingCurrencies(@NonNull String[] symbols, final BitfinexApiCallback<List<FundingCurrency>> userCallback);

    /*
     * Ticker
     *
     * The ticker is a high level overview of the state of the market. It shows you the current best
     * bid and ask, as well as the last trade price. It also includes information such as daily
     * volume and how much the price has moved over the last day.
     *
     * see: https://docs.bitfinex.com/v2/reference#rest-public-ticker
     */
    Result<Ticker> pullTicker(@NonNull String symbol) throws IOException;

    void getTicker(@NonNull String symbol, final BitfinexApiCallback<Ticker> userCallback);

    Result<TradingPair> pullTradingPair(@NonNull String symbol) throws IOException;

    void getTradingPair(@NonNull String symbol, final BitfinexApiCallback<TradingPair> userCallback);

    Result<FundingCurrency> pullFundingCurrency(@NonNull String symbol) throws IOException;

    void getFundingCurrency(@NonNull String symbol, final BitfinexApiCallback<FundingCurrency> userCallback);

    /*
     * Trades
     *
     * Trades endpoint includes all the pertinent details of the trade, such as price, size and time.
     *
     * see: https://docs.bitfinex.com/v2/reference#rest-public-trades
     */
    Result<List<CurrencyTrades>> pullCurrencyTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort) throws IOException;

    void getCurrencyTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort, final BitfinexApiCallback<List<CurrencyTrades>> userCallback);

    Result<List<CurrencyTrades>> pullCurrencyTrades(@NonNull String symbol) throws IOException;

    void getCurrencyTrades(@NonNull String symbol, final BitfinexApiCallback<List<CurrencyTrades>> userCallback);

    Result<List<PairTradeHistory>> pullPairTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort) throws IOException;

    void getPairTrades(@NonNull String symbol, Integer limit, Long start, Long end, Integer sort, final BitfinexApiCallback<List<PairTradeHistory>> userCallback);

    Result<List<PairTradeHistory>> pullPairTrades(@NonNull String symbol) throws IOException;

    void getPairTrades(@NonNull String symbol, final BitfinexApiCallback<List<PairTradeHistory>> userCallback);

    /*
     * Books
     *
     * The Order Books channel allow you to keep track of the state of the Bitfinex order book. It
     * is provided on a price aggregated basis, with customizable precision.
     *
     * see: https://docs.bitfinex.com/v2/reference#rest-public-books
     */
    Result<List<OrderBookTrading>> pullOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation, Integer len) throws IOException;

    void getOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation, Integer len, final BitfinexApiCallback<List<OrderBookTrading>> userCallback);

    Result<List<OrderBookTrading>> pullOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation) throws IOException;

    void getOrderBooksTraining(@NonNull String symbol, PriceAggregation priceAggregation, final BitfinexApiCallback<List<OrderBookTrading>> userCallback);

    Result<List<OrderBookFunding>> pullOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation, Integer len) throws IOException;

    void getOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation, Integer len, final BitfinexApiCallback<List<OrderBookFunding>> userCallback);

    Result<List<OrderBookFunding>> pullOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation) throws IOException;

    void getOrderBookFunding(@NonNull String symbol, PriceAggregation priceAggregation, final BitfinexApiCallback<List<OrderBookFunding>> userCallback);

    /*
     * Stats
     *
     * Various statistics about the requested pair.
     *
     * see: https://docs.bitfinex.com/v2/reference#rest-public-stats
     */
    Result<List<Stats>> pullStats(String key, final String section, final int sortOrder) throws IOException;

    void getStats(String key, final String section, final int sortOrder, final BitfinexApiCallback<List<Stats>> userCallback);

    Result<List<Stats>> pullStatsTotalOpenPositions(@NonNull String symbolTrading, Side side, Section section, SortOrder sortOrder) throws IOException;

    void getStatsTotalOpenPositions(@NonNull String symbolTrading, Side side, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Stats>> userCallback);

    Result<List<Stats>> pullStatsTotalActiveFunding(@NonNull String symbolFunding, Section section, SortOrder sortOrder) throws IOException;

    void getStatsTotalActiveFunding(@NonNull String symbolFunding, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Stats>> userCallback);

    Result<List<Stats>> pullStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, Section section, SortOrder sortOrder) throws IOException;

    void getStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Stats>> userCallback);

    Result<List<Stats>> pullStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, @NonNull String symbolTrading, Section section, SortOrder sortOrder) throws IOException;

    void getStatsActiveFundingUsedInPositions(@NonNull String symbolFunding, @NonNull String symbolTrading, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Stats>> userCallback);

    /*
     * Candles
     *
     * Provides a way to access charting candle info
     *
     * see: https://docs.bitfinex.com/v2/reference#rest-public-candles
     */
    Result<List<Candle>> pullCandles(final String key, final String section, final Integer limit, final Long start, final Long end, final Integer sortOrder) throws IOException;

    void getCandles(final String key, final String section, final Integer limit, final Long start, final Long end, final Integer sortOrder, final BitfinexApiCallback<List<Candle>> userCallback);

    Result<List<Candle>> pullTradingCandles(TradingCandleKey key, Section section, SortOrder sortOrder) throws IOException;

    void getTradingCandles(TradingCandleKey key, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Candle>> userCallback);

    Result<List<Candle>> pullFundingCandles(FundingCandleKey key, Section section, SortOrder sortOrder) throws IOException;

    void getFundingCandles(FundingCandleKey key, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Candle>> userCallback);

    Result<List<Candle>> pullAggregateFundingCandles(AggregateFundingCandleKey key, Section section, SortOrder sortOrder) throws IOException;

    void getAggregateFundingCandles(AggregateFundingCandleKey key, Section section, SortOrder sortOrder, final BitfinexApiCallback<List<Candle>> userCallback);
}
