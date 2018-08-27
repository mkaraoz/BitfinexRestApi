package com.duvarapps.bitfinexapi.v1.api;

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

@SuppressWarnings("JavaDoc")
public interface BitfinexApi
{

    /**
     * [SYNC] A list of symbol names. Ex: btcusd, ltcusd, ltcbtc
     *
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-symbols">Symbols@Bitfinex</a>
     */
    Result<List<String>> pullSymbols() throws IOException;

    /**
     * [ASYNC] A list of symbol names. Ex: btcusd, ltcusd, ltcbtc
     *
     * @param userCallback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-symbols">Symbols@Bitfinex</a>
     */
    void getSymbols(final BitfinexApiCallback<List<String>> userCallback);

    /**
     * [SYNC] The ticker is a high level overview of the state of the market. It shows you the current best
     * bid and ask, as well as the last trade price. It also includes information such as daily
     * volume and how much the price has moved over the last day.
     *
     * @param symbol The symbol you want information about. You can find the list of valid symbols
     *               by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *               method.
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-ticker">Tickers@Bitfinex</a>
     */
    Result<Ticker> pullTicker(final String symbol) throws IOException;

    /**
     * [ASYNC] The ticker is a high level overview of the state of the market. It shows you the current best
     * bid and ask, as well as the last trade price. It also includes information such as daily
     * volume and how much the price has moved over the last day.
     *
     * @param symbol       The symbol you want information about. You can find the list of valid symbols
     *                     by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *                     method.
     * @param userCallback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-ticker">Tickers@Bitfinex</a>
     */
    void getTicker(final String symbol, final BitfinexApiCallback<Ticker> userCallback);

    /**
     * [SYNC] Various statistics about the requested pair.
     * <p>
     *
     * @param symbol The symbol you want information about. You can find the list of valid symbols
     *               by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *               method.
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-stats">Statisticks@Bitfinex</a>
     */
    Result<List<Stats>> pullStats(final String symbol) throws IOException;

    /**
     * [ASYNC] Various statistics about the requested pair.
     *
     * @param symbol       The symbol you want information about. You can find the list of valid symbols
     *                     by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *                     method.
     * @param userCallback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-stats">Statisticks@Bitfinex</a>
     */
    void getStats(final String symbol, final BitfinexApiCallback<List<Stats>> userCallback);

    /**
     * [SYNC] Get the full margin funding book
     *
     * @param currency Could be BTC, USD, EUR, LTC, ETH, IOT, NEO...
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-fundingbook">Fundingbook@Bitfinex</a>
     */
    Result<FundingBook> pullFundingBook(final String currency) throws IOException;

    /**
     * [ASYNC] Get the full margin funding book
     *
     * @param currency     Could be BTC, USD, EUR, LTC, ETH, IOT, NEO...
     * @param userCallback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-fundingbook">Fundingbook@Bitfinex</a>
     */
    void getFundingBook(final String currency, final BitfinexApiCallback<FundingBook> userCallback);

    /**
     * [SYNC] Get the full order book.
     *
     * @param symbol The symbol you want information about. You can find the list of valid symbols
     *               by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *               method.
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-orderbook">Orderbook@Bitfinex</a>
     */
    Result<OrderBook> pullOrderBook(final String symbol) throws IOException;

    /**
     * [ASYNC] Get the full order book.
     *
     * @param symbol       The symbol you want information about. You can find the list of valid symbols
     *                     by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *                     method.
     * @param userCallback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-orderbook">Orderbook@Bitfinex</a>
     */
    void getOrderBook(final String symbol, final BitfinexApiCallback<OrderBook> userCallback);

    /**
     * [SYNC] Get a list of the most recent trades for the given symbol.
     *
     * @param symbol The symbol you want information about. You can find the list of valid symbols
     *               by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *               method.
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-trades">Trades@Bitfinex</a>
     */
    Result<List<Trade>> pullTrades(final String symbol) throws IOException;

    /**
     * [ASYNC] Get a list of the most recent trades for the given symbol.
     *
     * @param symbol       The symbol you want information about. You can find the list of valid symbols
     *                     by calling the {@link #getSymbols(BitfinexApiCallback) getValidSymbols}
     *                     method.
     * @param userCallback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-trades">Trades@Bitfinex</a>
     */
    void getTrades(final String symbol, final BitfinexApiCallback<List<Trade>> userCallback);

    /**
     * [SYNC] Get a list of the most recent funding data for the given currency: total amount
     * provided and Flash Return Rate (in % by 365 days) over time.
     *
     * @param currency Could be BTC, USD, EUR, LTC, ETH, IOT, NEO...
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-lends">Lends@Bitfinex</a>
     */
    Result<List<Lend>> pullLend(final String currency) throws IOException;

    /**
     * [ASYNC] Get a list of the most recent funding data for the given currency: total amount
     * provided and Flash Return Rate (in % by 365 days) over time.
     *
     * @param currency     Could be BTC, USD, EUR, LTC, ETH, IOT, NEO...
     * @param userCallback callback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-lends">Lends@Bitfinex</a>
     */
    void getLend(final String currency, final BitfinexApiCallback<List<Lend>> userCallback);

    /**
     * [SYNC] A list of valid symbol IDs and the pair details.
     *
     * @return
     * @throws IOException
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-symbol-details">Symbol Details@Bitfinex</a>
     */
    Result<List<SymbolDetail>> pullSymbolDetails() throws IOException;

    /**
     * [ASYNC] A list of valid symbol IDs and the pair details.
     *
     * @param userCallback callback
     * @see <a href="https://bitfinex.readme.io/v1/reference#rest-public-symbol-details">Symbol Details@Bitfinex</a>
     */
    void getSymbolDetails(final BitfinexApiCallback<List<SymbolDetail>> userCallback);
}
