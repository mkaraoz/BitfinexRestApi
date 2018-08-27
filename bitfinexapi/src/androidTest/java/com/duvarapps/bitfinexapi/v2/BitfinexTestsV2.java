package com.duvarapps.bitfinexapi.v2;

import android.support.test.runner.AndroidJUnit4;

import com.duvarapps.bitfinexapi.core.ApiResponse;
import com.duvarapps.bitfinexapi.core.BitfinexApiCallback;
import com.duvarapps.bitfinexapi.core.Result;
import com.duvarapps.bitfinexapi.helper.Bitfinex2Enums;
import com.duvarapps.bitfinexapi.helper.candle_key.AggregateFundingCandleKey;
import com.duvarapps.bitfinexapi.helper.candle_key.FundingCandleKey;
import com.duvarapps.bitfinexapi.helper.candle_key.TradingCandleKey;
import com.duvarapps.bitfinexapi.v2.api.BitfinexApi2;
import com.duvarapps.bitfinexapi.v2.api.BitfinexFactory2;
import com.duvarapps.bitfinexapi.v2.pojo.Candle;
import com.duvarapps.bitfinexapi.v2.pojo.CurrencyTrades;
import com.duvarapps.bitfinexapi.v2.pojo.FundingCurrency;
import com.duvarapps.bitfinexapi.v2.pojo.PairTradeHistory;
import com.duvarapps.bitfinexapi.v2.pojo.PlatformStatus;
import com.duvarapps.bitfinexapi.v2.pojo.Stats;
import com.duvarapps.bitfinexapi.v2.pojo.Ticker;
import com.duvarapps.bitfinexapi.v2.pojo.Tickers;
import com.duvarapps.bitfinexapi.v2.pojo.TradingPair;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static com.duvarapps.bitfinexapi.v2.FakeResponses2.ACTIVE_FUNDING_USED_IN_POSITIONS_PER_TRADING_SYMBOL_HIST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.ACTIVE_FUNDING_USED_IN_POSITIONS_PER_TRADING_SYMBOL_LAST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.AGGREGATE_FUNDING_CANDLES;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.AGGREGATE_FUNDING_CANDLES_HIST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.ALL_TICKERS;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.FUNDING_CANDLES_LAST_P10_HIST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.FUNDING_CANDLES_LAST_P2;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.FUNDING_PAIRS_EUR_USD_ETC;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.GET_CANDLES;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.PULL_CANDLES;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.STATS_ACTIVE_FUNDING_HIST_NEW2OLD;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.STATS_ACTIVE_FUNDING_LAST_OLD2NEW;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TADING_CANDLES_HIST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TADING_CANDLES_LAST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TICKER_BTCUSD;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TICKER_LONG;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TICKER_SHORT;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TICKER_fETC;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TICKER_fETH;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TOTAL_ACTIVE_FUNDING_HIST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TOTAL_ACTIVE_FUNDING_LAST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TOTAL_OPEN_POS_HIST_REVERSE_ORDER;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TOTAL_OPEN_POS_LAST;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TOTAL_OPEN_POS_LAST_SHORT;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADE_fBTC;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADE_fBTC_LIMITED_10;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADE_fBTC_TIME_FRAME;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADE_tETHEUR;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADE_tXMRUSD;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADE_tXRPUSD_LIMITED_10;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADE_tXRPUSD_LIMITED_2;
import static com.duvarapps.bitfinexapi.v2.FakeResponses2.TRADING_PAIRS_PULL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class BitfinexTestsV2
{
    private static final double DELTA = 1e-8;

    private BitfinexApi2 createBitfinex(final String... fakeResponse)
    {
        if (fakeResponse.length != 0)
        {
            return BitfinexFactory2.createClient(
                    FakeBitfinexClient2.getFakeClient(fakeResponse[0]));
        }
        else
        {
            return BitfinexFactory2.createClient();
        }
    }

    private final Object syncObject = new Object();

    private void waitAsyncRequest()
    {
        synchronized (syncObject)
        {
            try
            {
                syncObject.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void notifyAsyncRequest()
    {

        synchronized (syncObject)
        {
            syncObject.notify();
        }
    }

    @After
    public void sleep() throws InterruptedException
    {
        // prevents rate limit
        Thread.sleep(1000);
    }

    @Test
    public void pullPlatformStatus() throws IOException
    {
        BitfinexApi2 bfx2 = createBitfinex();

        Result<PlatformStatus> res = bfx2.pullPlatformStatus();
        int platformStatus = res.body.getStatus();
        assertTrue(platformStatus == 1);
    }

    @Test
    public void pullPlatformStatusOperational() throws IOException
    {
        String fakeResponse = "[1]";
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<PlatformStatus> res = bfx2.pullPlatformStatus();
        int platformStatus = res.body.getStatus();
        assertTrue(platformStatus == 1);
    }

    @Test
    public void pullPlatformStatusMaintenance() throws IOException
    {
        String fakeResponse = "[0]";
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<PlatformStatus> res = bfx2.pullPlatformStatus();
        int platformStatus = res.body.getStatus();
        assertTrue(platformStatus == 0);
    }

    @Test
    public void getPlatformStatus()
    {
        BitfinexApi2 bfx2 = createBitfinex();

        bfx2.getPlatformStatus(new BitfinexApiCallback<PlatformStatus>()
        {
            @Override
            public void onResponse(PlatformStatus res, ApiResponse response)
            {
                int platformStatus = res.getStatus();
                assertTrue(platformStatus == 1);

                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void getPlatformStatusOperational()
    {
        String fakeResponse = "[1]";
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getPlatformStatus(new BitfinexApiCallback<PlatformStatus>()
        {
            @Override
            public void onResponse(PlatformStatus res, ApiResponse response)
            {
                int platformStatus = res.getStatus();
                assertTrue(platformStatus == 1);

                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void getPlatformStatusMaintenance()
    {
        String fakeResponse = "[0]";
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getPlatformStatus(new BitfinexApiCallback<PlatformStatus>()
        {
            @Override
            public void onResponse(PlatformStatus res, ApiResponse response)
            {
                int platformStatus = res.getStatus();
                assertTrue(platformStatus == 0);

                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullTickers_LIVE() throws IOException
    {
        BitfinexApi2 bfx2 = createBitfinex();

        Result<Tickers> tickers = bfx2.pullTickers(new String[]{"tBTCUSD", "tLTCUSD", "fUSD"});
        assertEquals(1, tickers.body.getFundingCurrencies().size());
        assertEquals(2, tickers.body.getTradingPairs().size());

        assertTrue(tickers.body.getTradingPairs().get(0).getSymbol().equals("tBTCUSD"));
        assertTrue(tickers.body.getTradingPairs().get(1).getSymbol().equals("tLTCUSD"));
        assertTrue(tickers.body.getFundingCurrencies().get(0).getSymbol().equals("fUSD"));
    }

    @Test
    public void pullTickers() throws IOException
    {
        String fakeResponse = TICKER_SHORT;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<Tickers> tickers = bfx2.pullTickers(new String[]{"tBTCUSD", "tLTCUSD", "fUSD"});
        assertEquals(1, tickers.body.getFundingCurrencies().size());
        assertEquals(2, tickers.body.getTradingPairs().size());

        assertTrue(tickers.body.getTradingPairs().get(0).getSymbol().equals("tBTCUSD"));
        assertTrue(tickers.body.getTradingPairs().get(1).getSymbol().equals("tLTCUSD"));
        assertTrue(tickers.body.getFundingCurrencies().get(0).getSymbol().equals("fUSD"));

        assertEquals(6375, tickers.body.getTradingPairs().get(0).getLastPrice(), 1);
        assertEquals(6250, tickers.body.getTradingPairs().get(0).getLow(), 1);
        assertEquals(0.00010348, tickers.body.getFundingCurrencies().get(0).getFrr(), DELTA);
    }

    @Test
    public void getTickers()
    {
        String fakeResponse = TICKER_LONG;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getTickers(new String[]{"ALL"}, new BitfinexApiCallback<Tickers>()
        {
            @Override
            public void onResponse(Tickers tickers, ApiResponse response)
            {
                assertEquals(21, tickers.getFundingCurrencies().size());
                assertEquals(275, tickers.getTradingPairs().size());

                assertTrue(tickers.getTradingPairs().get(0).getSymbol().equals("tBTCUSD"));
                assertTrue(tickers.getTradingPairs().get(1).getSymbol().equals("tLTCUSD"));
                assertTrue(tickers.getTradingPairs().get(19).getSymbol().equals("tIOTUSD"));
                assertTrue(tickers.getFundingCurrencies().get(0).getSymbol().equals("fUSD"));

                assertEquals(6428, tickers.getTradingPairs().get(0).getLastPrice(), 1);
                assertEquals(6250, tickers.getTradingPairs().get(0).getLow(), 1);
                assertEquals(1.031E-4, tickers.getFundingCurrencies().get(0).getFrr(), DELTA);

                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullAllTickers() throws IOException
    {
        String fakeResponse = ALL_TICKERS;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<Tickers> tickers = bfx2.pullAllTickers();
        assertEquals(21, tickers.body.getFundingCurrencies().size());
        assertEquals(275, tickers.body.getTradingPairs().size());

        assertTrue(tickers.body.getTradingPairs().get(0).getSymbol().equals("tBTCUSD"));
        assertEquals(57.7890, tickers.body.getTradingPairs().get(1).getLastPrice(), DELTA);
        assertEquals(0.00856, tickers.body.getTradingPairs().get(2).getLow(), DELTA);

        assertTrue(tickers.body.getFundingCurrencies().get(0).getSymbol().equals("fUSD"));
        assertTrue(tickers.body.getFundingCurrencies().get(20).getSymbol().equals("fBTG"));
        assertEquals(tickers.body.getFundingCurrencies().get(1).getAskPeriod(), 2);
        assertEquals(-0.0994, tickers.body.getFundingCurrencies().get(20).getDailyChangePerc(),
                0.001);
    }

    @Test
    public void getAllTickers()
    {
        String fakeResponse = ALL_TICKERS;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        //BitfinexApi2 bfx2 = createBitfinex();

        bfx2.getAllTickers(new BitfinexApiCallback<Tickers>()
        {
            @Override
            public void onResponse(Tickers tickers, ApiResponse response)
            {
                assertEquals(21, tickers.getFundingCurrencies().size());
                assertEquals(275, tickers.getTradingPairs().size());

                assertTrue(tickers.getTradingPairs().get(0).getSymbol().equals("tBTCUSD"));
                assertEquals(57.7890, tickers.getTradingPairs().get(1).getLastPrice(), DELTA);
                assertEquals(0.00856, tickers.getTradingPairs().get(2).getLow(), DELTA);

                assertTrue(tickers.getFundingCurrencies().get(0).getSymbol().equals("fUSD"));
                assertTrue(tickers.getFundingCurrencies().get(20).getSymbol().equals("fBTG"));
                assertEquals(tickers.getFundingCurrencies().get(1).getAskPeriod(), 2);
                assertEquals(-0.0994, tickers.getFundingCurrencies().get(20).getDailyChangePerc(),
                        0.001);

                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullTradingPairs() throws IOException
    {
        String fakeResponse = TRADING_PAIRS_PULL;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<TradingPair>> result = bfx2.pullTradingPairs(
                new String[]{"tBTCUSD", "tLTCUSD"});
        List<TradingPair> tickers = result.body;

        assertEquals(2, tickers.size());
        assertEquals(6625.9, tickers.get(0).getBid(), DELTA);
        assertEquals(198.9, tickers.get(0).getDailyChange(), DELTA);

        assertEquals(57.705, tickers.get(1).getBid(), DELTA);
        assertEquals(57.702, tickers.get(1).getLastPrice(), DELTA);
    }

    @Test
    public void getTradingPairs()
    {
        String fakeResponse = TRADING_PAIRS_PULL;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        // BitfinexApi2 bfx2 = createBitfinex();

        bfx2.getTradingPairs(new String[]{"tBTCUSD", "tLTCUSD"},
                new BitfinexApiCallback<List<TradingPair>>()
                {
                    @Override
                    public void onResponse(List<TradingPair> tickers, ApiResponse response)
                    {
                        assertEquals(2, tickers.size());
                        assertEquals(6625.9, tickers.get(0).getBid(), DELTA);
                        assertEquals(198.9, tickers.get(0).getDailyChange(), DELTA);

                        assertEquals(57.705, tickers.get(1).getBid(), DELTA);
                        assertEquals(57.702, tickers.get(1).getLastPrice(), DELTA);

                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void getTradingPairs_LIVE()
    {
        BitfinexApi2 bfx2 = createBitfinex();

        bfx2.getTradingPairs(new String[]{"tBTCUSD", "tLTCUSD"},
                new BitfinexApiCallback<List<TradingPair>>()
                {
                    @Override
                    public void onResponse(List<TradingPair> tickers, ApiResponse response)
                    {
                        assertEquals(2, tickers.size());
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullFundingCurrencies() throws IOException
    {
        String fakeResponse = FUNDING_PAIRS_EUR_USD_ETC;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<FundingCurrency>> result = bfx2.pullFundingCurrencies(
                new String[]{"fEUR", "fUSD", "fETC"});
        List<FundingCurrency> tickers = result.body;

        assertEquals(3, tickers.size());

        assertTrue(tickers.get(0).getSymbol().equals("fEUR"));
        assertTrue(tickers.get(1).getSymbol().equals("fUSD"));
        assertTrue(tickers.get(2).getSymbol().equals("fETC"));

        assertEquals(0.00003676, tickers.get(0).getFrr(), DELTA);
        assertEquals(0.000101, tickers.get(1).getBid(), DELTA);
        assertEquals(30, tickers.get(2).getBidPeriod());
    }

    @Test
    public void getFundingCurrencies()
    {
        String fakeResponse = FUNDING_PAIRS_EUR_USD_ETC;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        // BitfinexApi2 bfx2 = createBitfinex();

        bfx2.getFundingCurrencies(new String[]{"fEUR", "fUSD", "fETC"},
                new BitfinexApiCallback<List<FundingCurrency>>()
                {
                    @Override
                    public void onResponse(List<FundingCurrency> tickers, ApiResponse response)
                    {
                        assertEquals(3, tickers.size());

                        assertTrue(tickers.get(0).getSymbol().equals("fEUR"));
                        assertTrue(tickers.get(1).getSymbol().equals("fUSD"));
                        assertTrue(tickers.get(2).getSymbol().equals("fETC"));

                        assertEquals(0.00003676, tickers.get(0).getFrr(), DELTA);
                        assertEquals(0.000101, tickers.get(1).getBid(), DELTA);
                        assertEquals(30, tickers.get(2).getBidPeriod());

                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullTicker() throws IOException
    {
        String fakeResponse = TICKER_BTCUSD;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<Ticker> result = bfx2.pullTicker("tBTCUSD");
        Ticker ticker = result.body;

        assertTrue(ticker.getTradingPair().getSymbol().equals("tBTCUSD"));
        assertEquals(6607.7, ticker.getTradingPair().getLastPrice(), DELTA);
    }

    @Test
    public void getTicker()
    {
        String fakeResponse = TICKER_BTCUSD;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getTicker("tBTCUSD", new BitfinexApiCallback<Ticker>()
        {
            @Override
            public void onResponse(Ticker ticker, ApiResponse response)
            {
                assertTrue(ticker.getTradingPair().getSymbol().equals("tBTCUSD"));
                assertEquals(6607.7, ticker.getTradingPair().getLastPrice(), DELTA);
                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullTicker_fETH() throws IOException
    {
        String fakeResponse = TICKER_fETH;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<Ticker> result = bfx2.pullTicker("fETH");
        Ticker ticker = result.body;

        assertTrue(ticker.getFundingCurrency().getSymbol().equals("fETH"));
        assertEquals(0.000015, ticker.getFundingCurrency().getLastPrice(), DELTA);
    }

    @Test
    public void getTicker_fETH()
    {
        String fakeResponse = TICKER_fETH;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getTicker("fETH", new BitfinexApiCallback<Ticker>()
        {
            @Override
            public void onResponse(Ticker ticker, ApiResponse response)
            {
                assertTrue(ticker.getFundingCurrency().getSymbol().equals("fETH"));
                assertEquals(0.000015, ticker.getFundingCurrency().getLastPrice(), DELTA);
                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullTradingPair() throws IOException
    {
        String fakeResponse = TICKER_BTCUSD;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<TradingPair> result = bfx2.pullTradingPair("tBTCUSD");
        TradingPair ticker = result.body;

        assertTrue(ticker.getSymbol().equals("tBTCUSD"));
        assertEquals(6607.7, ticker.getLastPrice(), DELTA);
    }

    @Test
    public void getTradingPair()
    {
        String fakeResponse = TICKER_BTCUSD;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getTradingPair("tBTCUSD", new BitfinexApiCallback<TradingPair>()
        {
            @Override
            public void onResponse(TradingPair ticker, ApiResponse response)
            {
                assertTrue(ticker.getSymbol().equals("tBTCUSD"));
                assertEquals(6607.7, ticker.getLastPrice(), DELTA);
                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullFundingCurrency() throws IOException
    {
        String fakeResponse = TICKER_fETC;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<FundingCurrency> result = bfx2.pullFundingCurrency("fETC");
        FundingCurrency ticker = result.body;

        assertTrue(ticker.getSymbol().equals("fETC"));
        assertEquals(0.0000079, ticker.getLastPrice(), DELTA);
    }

    @Test
    public void getFundingCurrency()
    {
        String fakeResponse = TICKER_fETC;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getFundingCurrency("fETC", new BitfinexApiCallback<FundingCurrency>()
        {
            @Override
            public void onResponse(FundingCurrency ticker, ApiResponse response)
            {
                assertTrue(ticker.getSymbol().equals("fETC"));
                assertEquals(0.0000079, ticker.getLastPrice(), DELTA);
                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullCurrencyTrades() throws IOException
    {
        String fakeResponse = TRADE_fBTC;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<CurrencyTrades>> result = bfx2.pullCurrencyTrades("fBTC");
        List<CurrencyTrades> currencyTrades = result.body;

        assertEquals(120, currencyTrades.size());
        assertEquals(76267352, currencyTrades.get(0).getId());
        assertEquals(1535183867743L, currencyTrades.get(1).getMts());
        assertEquals(-0.01005836, currencyTrades.get(2).getAmount(), DELTA);
        assertEquals(0.00010877, currencyTrades.get(3).getRate(), DELTA);
        assertEquals(2, currencyTrades.get(currencyTrades.size() - 1).getPeriod(), DELTA);
    }

    @Test
    public void getCurrencyTrades()
    {
        String fakeResponse = TRADE_fBTC;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getCurrencyTrades("fETC", new BitfinexApiCallback<List<CurrencyTrades>>()
        {
            @Override
            public void onResponse(List<CurrencyTrades> currencyTrades, ApiResponse response)
            {
                assertEquals(120, currencyTrades.size());
                assertEquals(76267352, currencyTrades.get(0).getId());
                assertEquals(1535183867743L, currencyTrades.get(1).getMts());
                assertEquals(-0.01005836, currencyTrades.get(2).getAmount(), DELTA);
                assertEquals(0.00010877, currencyTrades.get(3).getRate(), DELTA);
                assertEquals(2, currencyTrades.get(currencyTrades.size() - 1).getPeriod(), DELTA);
                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullCurrencyTrades_fBTC_LIMITED_10() throws IOException
    {
        String fakeResponse = TRADE_fBTC_LIMITED_10;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<CurrencyTrades>> result = bfx2.pullCurrencyTrades("fBTC", 10, null, null, 1);
        List<CurrencyTrades> currencyTrades = result.body;

        assertEquals(10, currencyTrades.size());
        assertEquals(36, currencyTrades.get(0).getId());
        assertEquals(1469734300000L, currencyTrades.get(1).getMts());
        assertEquals(0.04568922, currencyTrades.get(2).getAmount(), DELTA);
        assertEquals(0.00004855, currencyTrades.get(3).getRate(), DELTA);
        assertEquals(30, currencyTrades.get(currencyTrades.size() - 1).getPeriod(), DELTA);
    }

    @Test
    public void getCurrencyTrades_LIMITED_2()
    {
        String fakeResponse = TRADE_fBTC_TIME_FRAME;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getCurrencyTrades("fBTC", 10, 1535188161146L, 1535188163143L, 0,
                new BitfinexApiCallback<List<CurrencyTrades>>()
                {
                    @Override
                    public void onResponse(List<CurrencyTrades> currencyTrades, ApiResponse response)
                    {
                        assertEquals(7, currencyTrades.size());
                        assertEquals(1535188163143L, currencyTrades.get(0).getMts());
                        assertEquals(1535188161146L,
                                currencyTrades.get(currencyTrades.size() - 1).getMts());
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullPairTrades() throws IOException
    {
        String fakeResponse = TRADE_tXMRUSD;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<PairTradeHistory>> result = bfx2.pullPairTrades("tXMRUSD");
        List<PairTradeHistory> pairTrades = result.body;

        assertEquals(120, pairTrades.size());
        assertEquals(285143797, pairTrades.get(0).getId());
        assertEquals(1535188692056L, pairTrades.get(1).getMts());
        assertEquals(1, pairTrades.get(2).getAmount(), 0.0001);
        assertEquals(92.89, pairTrades.get(pairTrades.size() - 1).getPrice(), DELTA);
    }

    @Test
    public void getPairTrades()
    {
        String fakeResponse = TRADE_tETHEUR;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getPairTrades("tETHEUR", new BitfinexApiCallback<List<PairTradeHistory>>()
        {
            @Override
            public void onResponse(List<PairTradeHistory> pairTrades, ApiResponse response)
            {
                assertEquals(120, pairTrades.size());
                assertEquals(285144835, pairTrades.get(0).getId());
                assertEquals(1535188764369L, pairTrades.get(1).getMts());
                assertEquals(-0.06, pairTrades.get(2).getAmount(), DELTA);
                assertEquals(238.60999948, pairTrades.get(pairTrades.size() - 1).getPrice(),
                        0.0001);
                notifyAsyncRequest();
            }

            @Override
            public void onError(Throwable t)
            {
                notifyAsyncRequest();
            }
        });

        waitAsyncRequest();
    }

    @Test
    public void pullPairTrades_Limit_10() throws IOException
    {
        String fakeResponse = TRADE_tXRPUSD_LIMITED_10;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<PairTradeHistory>> result = bfx2.pullPairTrades("tXRPUSD", 10, null, null, 1);
        List<PairTradeHistory> pairTrades = result.body;

        assertEquals(10, pairTrades.size());
        assertEquals(32609915, pairTrades.get(0).getId());
        assertEquals(1495216754000L, pairTrades.get(1).getMts());
        assertEquals(10, pairTrades.get(2).getAmount(), 0.0001);
        assertEquals(0.37986, pairTrades.get(pairTrades.size() - 1).getPrice(), DELTA);
    }

    @Test
    public void pullPairTrades_Limit_10_LIVE() throws IOException
    {
        BitfinexApi2 bfx2 = createBitfinex();

        Result<List<PairTradeHistory>> result = bfx2.pullPairTrades("tXRPUSD", 10, null, null, 1);
        List<PairTradeHistory> pairTrades = result.body;

        assertEquals(10, pairTrades.size());
    }

    @Test
    public void getPairTrades_LIMITED_2()
    {
        String fakeResponse = TRADE_tXRPUSD_LIMITED_2;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getPairTrades("tXRPUSD", 2, null, null, 0,
                new BitfinexApiCallback<List<PairTradeHistory>>()
                {
                    @Override
                    public void onResponse(List<PairTradeHistory> pairTrades, ApiResponse response)
                    {
                        assertEquals(2, pairTrades.size());
                        assertEquals(285146624, pairTrades.get(0).getId());
                        assertEquals(1535189927351L, pairTrades.get(1).getMts());
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullStatsTotalOpenPositions() throws IOException
    {
        String fakeResponse = TOTAL_OPEN_POS_LAST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        Result<List<Stats>> result = bfx2.pullStatsTotalOpenPositions("tBTCUSD",
                Bitfinex2Enums.Side.LONG, Bitfinex2Enums.Section.LAST,
                Bitfinex2Enums.SortOrder.NEW_TO_OLD);

        List<Stats> stats = result.body;

        assertEquals(1535190180000L, stats.get(0).getMts());
        assertEquals(26988.45023172, stats.get(0).getValue(), DELTA);
    }

    @Test
    public void pullStatsTotalOpenPositions_Short() throws IOException
    {
        String fakeResponse = TOTAL_OPEN_POS_LAST_SHORT;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        Result<List<Stats>> result = bfx2.pullStatsTotalOpenPositions("tBTCUSD",
                Bitfinex2Enums.Side.SHORT, Bitfinex2Enums.Section.LAST,
                Bitfinex2Enums.SortOrder.NEW_TO_OLD);

        List<Stats> stats = result.body;

        assertEquals(1535191260000L, stats.get(0).getMts());
        assertEquals(36881.90647622, stats.get(0).getValue(), DELTA);
    }

    @Test
    public void getStatsTotalOpenPositions()
    {
        String fakeResponse = TOTAL_OPEN_POS_HIST_REVERSE_ORDER;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getStatsTotalOpenPositions("tBTCUSD", Bitfinex2Enums.Side.LONG,
                Bitfinex2Enums.Section.HIST, Bitfinex2Enums.SortOrder.OLD_TO_NEW,
                new BitfinexApiCallback<List<Stats>>()
                {
                    @Override
                    public void onResponse(List<Stats> stats, ApiResponse response)
                    {
                        assertEquals(120, stats.size());
                        assertEquals(1478466780000L, stats.get(1).getMts());
                        assertEquals(31985.27159474, stats.get(1).getValue(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void getStatsTotalOpenPositions_LIVE()
    {
        BitfinexApi2 bfx2 = createBitfinex();

        bfx2.getStatsTotalOpenPositions("tBTCUSD", Bitfinex2Enums.Side.LONG,
                Bitfinex2Enums.Section.HIST, Bitfinex2Enums.SortOrder.OLD_TO_NEW,
                new BitfinexApiCallback<List<Stats>>()
                {
                    @Override
                    public void onResponse(List<Stats> stats, ApiResponse response)
                    {
                        assertEquals(120, stats.size());
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullStatsTotalActiveFunding_LAST() throws IOException
    {
        String fakeResponse = TOTAL_ACTIVE_FUNDING_LAST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        Result<List<Stats>> result = bfx2.pullStatsTotalActiveFunding("fUSD",
                Bitfinex2Enums.Section.LAST, Bitfinex2Enums.SortOrder.NEW_TO_OLD);

        List<Stats> stats = result.body;

        assertEquals(1535191380000L, stats.get(0).getMts());
        assertEquals(421395375.3073991, stats.get(0).getValue(), DELTA);
    }

    @Test
    public void getStatsTotalActiveFunding_HIST()
    {
        String fakeResponse = TOTAL_ACTIVE_FUNDING_HIST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getStatsTotalActiveFunding("fUSD", Bitfinex2Enums.Section.HIST,
                Bitfinex2Enums.SortOrder.NEW_TO_OLD, new BitfinexApiCallback<List<Stats>>()
                {
                    @Override
                    public void onResponse(List<Stats> stats, ApiResponse response)
                    {
                        assertEquals(120, stats.size());
                        assertEquals(1535191320000L, stats.get(0).getMts());
                        assertEquals(421395375.3073991, stats.get(0).getValue(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullStatsActiveFundingUsedInPositions() throws IOException
    {
        String fakeResponse = STATS_ACTIVE_FUNDING_LAST_OLD2NEW;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        Result<List<Stats>> result = bfx2.pullStatsTotalActiveFunding("fEUR",
                Bitfinex2Enums.Section.LAST, Bitfinex2Enums.SortOrder.OLD_TO_NEW);

        List<Stats> stats = result.body;

        assertEquals(1535193120000L, stats.get(0).getMts());
        assertEquals(2379945.3627558, stats.get(0).getValue(), DELTA);
    }

    @Test
    public void getStatsActiveFundingUsedInPositions()
    {
        String fakeResponse = STATS_ACTIVE_FUNDING_HIST_NEW2OLD;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getStatsActiveFundingUsedInPositions("fEUR", Bitfinex2Enums.Section.HIST,
                Bitfinex2Enums.SortOrder.NEW_TO_OLD, new BitfinexApiCallback<List<Stats>>()
                {
                    @Override
                    public void onResponse(List<Stats> stats, ApiResponse response)
                    {
                        assertEquals(120, stats.size());
                        assertEquals(1535193000000L, stats.get(0).getMts());
                        assertEquals(2379945.3627558, stats.get(0).getValue(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullStatsActiveFundingUsedInPositions_Last() throws IOException
    {
        String fakeResponse = ACTIVE_FUNDING_USED_IN_POSITIONS_PER_TRADING_SYMBOL_LAST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);
        Result<List<Stats>> result = bfx2.pullStatsActiveFundingUsedInPositions("fEUR", "tBTCEUR",
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.Section.LAST,
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.SortOrder.OLD_TO_NEW);

        List<Stats> stats = result.body;

        assertEquals(1535193960000L, stats.get(0).getMts());
        assertEquals(1580253.02084081, stats.get(0).getValue(), DELTA);
    }

    @Test
    public void getStatsActiveFundingUsedInPositions_Hist()
    {
        String fakeResponse = ACTIVE_FUNDING_USED_IN_POSITIONS_PER_TRADING_SYMBOL_HIST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getStatsActiveFundingUsedInPositions("fUSD", "tBTCUSD",
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.Section.HIST,
                Bitfinex2Enums.SortOrder.NEW_TO_OLD, new BitfinexApiCallback<List<Stats>>()
                {
                    @Override
                    public void onResponse(List<Stats> stats, ApiResponse response)
                    {
                        assertEquals(120, stats.size());
                        assertEquals(1535193900000L, stats.get(2).getMts());
                        assertEquals(173180697.6231527, stats.get(2).getValue(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });

        waitAsyncRequest();
    }

    @Test
    public void pullCandles() throws IOException
    {
        String fakeResponse = PULL_CANDLES;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<Candle>> result = bfx2.pullCandles("trade:1m:tBTCUSD",
                Bitfinex2Enums.Section.HIST.toString(), 30, 1535194020000L, 1535195100000L, 1);

        List<Candle> candles = result.body;

        assertEquals(19, candles.size());
        assertEquals(1535194020000L, candles.get(0).getMts());
        assertEquals(6713.1, candles.get(0).getOpen(), DELTA);
        assertEquals(6712.9, candles.get(0).getClose(), DELTA);
        assertEquals(6723.4, candles.get(18).getHigh(), DELTA);
        assertEquals(6713.7, candles.get(18).getLow(), DELTA);
        assertEquals(64.64747758, candles.get(18).getVolume(), DELTA);
    }

    @Test
    public void getCandles()
    {
        String fakeResponse = GET_CANDLES;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getCandles("trade:5m:tBTCUSD", Bitfinex2Enums.Section.LAST.toString(), 5, null, null,
                null, new BitfinexApiCallback<List<Candle>>()
                {
                    @Override
                    public void onResponse(List<Candle> candles, ApiResponse response)
                    {
                        assertEquals(1, candles.size());
                        assertEquals(1535196000000L, candles.get(0).getMts());
                        assertEquals(6712.2, candles.get(0).getOpen(), DELTA);
                        assertEquals(6716.1, candles.get(0).getClose(), DELTA);
                        assertEquals(6716.1, candles.get(0).getHigh(), DELTA);
                        assertEquals(6711, candles.get(0).getLow(), DELTA);
                        assertEquals(31.51262473, candles.get(0).getVolume(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });
        waitAsyncRequest();
    }

    @Test
    public void pullTradingCandles() throws IOException
    {
        String fakeResponse = TADING_CANDLES_LAST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<Candle>> result = bfx2.pullTradingCandles(
                new TradingCandleKey(com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.TimeFrame._1D,
                        "tBTCUSD"), com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.Section.LAST,
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.SortOrder.OLD_TO_NEW);

        List<Candle> candles = result.body;

        assertEquals(1, candles.size());
        assertEquals(1535155200000L, candles.get(0).getMts());
        assertEquals(6693, candles.get(0).getOpen(), 1);
        assertEquals(6722, candles.get(0).getClose(), 1);
        assertEquals(6799, candles.get(0).getHigh(), 1);
        assertEquals(6670, candles.get(0).getLow(), 1);
        assertEquals(9056.99174111, candles.get(0).getVolume(), DELTA);
    }

    @Test
    public void getTradingCandles()
    {
        String fakeResponse = TADING_CANDLES_HIST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getTradingCandles(new TradingCandleKey(Bitfinex2Enums.TimeFrame._1M, "tETCUSD"),
                Bitfinex2Enums.Section.HIST, Bitfinex2Enums.SortOrder.NEW_TO_OLD,
                new BitfinexApiCallback<List<Candle>>()
                {
                    @Override
                    public void onResponse(List<Candle> candles, ApiResponse response)
                    {
                        assertEquals(25, candles.size());
                        assertEquals(1493596800000L, candles.get(15).getMts());
                        assertEquals(2.8125, candles.get(15).getOpen(), DELTA);
                        assertEquals(6.77, candles.get(15).getClose(), DELTA);
                        assertEquals(7.0081, candles.get(15).getHigh(), DELTA);
                        assertEquals(2.443, candles.get(15).getLow(), DELTA);
                        assertEquals(1.628928374645037E7, candles.get(15).getVolume(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });
        waitAsyncRequest();
    }

    @Test
    public void pullFundingCandles() throws IOException
    {
        String fakeResponse = FUNDING_CANDLES_LAST_P2;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<Candle>> result = bfx2.pullFundingCandles(
                new FundingCandleKey(com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.TimeFrame._1D,
                        "fUSD", com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.FundingPeriod.P2),
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.Section.LAST,
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.SortOrder.NEW_TO_OLD);

        List<Candle> candles = result.body;

        assertEquals(1, candles.size());
        assertEquals(1535197080000L, candles.get(0).getMts());
        assertEquals(0.00009589, candles.get(0).getOpen(), DELTA);
        assertEquals(0.00009582, candles.get(0).getClose(), DELTA);
        assertEquals(0.00009589, candles.get(0).getHigh(), DELTA);
        assertEquals(0.00009315, candles.get(0).getLow(), DELTA);
        assertEquals(698.22413712, candles.get(0).getVolume(), DELTA);
    }

    @Test
    public void getFundingCandles()
    {
        String fakeResponse = FUNDING_CANDLES_LAST_P10_HIST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getFundingCandles(
                new FundingCandleKey(com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.TimeFrame._1m,
                        "fUSD", com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.FundingPeriod.P10),
                Bitfinex2Enums.Section.HIST,
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.SortOrder.NEW_TO_OLD,
                new BitfinexApiCallback<List<Candle>>()
                {
                    @Override
                    public void onResponse(List<Candle> candles, ApiResponse response)
                    {
                        assertEquals(120, candles.size());
                        assertEquals(1535157420000L, candles.get(53).getMts());
                        assertEquals(9.996E-5, candles.get(53).getClose(), DELTA);
                        assertEquals(9.996E-5, candles.get(53).getHigh(), DELTA);
                        assertEquals(1321.95549316, candles.get(53).getVolume(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });
        waitAsyncRequest();
    }

    @Test
    public void getFundingCandles_LIVE()
    {
        BitfinexApi2 bfx2 = createBitfinex();

        bfx2.getFundingCandles(
                new FundingCandleKey(com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.TimeFrame._1m,
                        "fUSD", com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.FundingPeriod.P10),
                Bitfinex2Enums.Section.HIST,
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.SortOrder.NEW_TO_OLD,
                new BitfinexApiCallback<List<Candle>>()
                {
                    @Override
                    public void onResponse(List<Candle> candles, ApiResponse response)
                    {
                        assertEquals(120, candles.size());
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });
        waitAsyncRequest();
    }

    @Test
    public void pullAggregateFundingCandles() throws IOException
    {
        String fakeResponse = AGGREGATE_FUNDING_CANDLES;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        Result<List<Candle>> result = bfx2.pullAggregateFundingCandles(
                new AggregateFundingCandleKey(
                        com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.TimeFrame._1m, "fUSD",
                        Bitfinex2Enums.Aggregate.A10,
                        com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.FundingPeriod.P2,
                        Bitfinex2Enums.FundingPeriod.P10),
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.Section.LAST,
                com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.SortOrder.NEW_TO_OLD);

        List<Candle> candles = result.body;

        assertEquals(1, candles.size());
        assertEquals(1535198220000L, candles.get(0).getMts());
        assertEquals(0.00007401, candles.get(0).getOpen(), DELTA);
        assertEquals(0.00009038, candles.get(0).getClose(), DELTA);
        assertEquals(0.00009038, candles.get(0).getHigh(), DELTA);
        assertEquals(0.00007401, candles.get(0).getLow(), DELTA);
        assertEquals(142931.36491509, candles.get(0).getVolume(), DELTA);
    }

    @Test
    public void getAggregateFundingCandles()
    {
        String fakeResponse = AGGREGATE_FUNDING_CANDLES_HIST;
        BitfinexApi2 bfx2 = createBitfinex(fakeResponse);

        bfx2.getAggregateFundingCandles(new AggregateFundingCandleKey(
                        com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.TimeFrame._1m, "fUSD",
                        Bitfinex2Enums.Aggregate.A30,
                        com.duvarapps.bitfinexapi.helper.Bitfinex2Enums.FundingPeriod.P2,
                        Bitfinex2Enums.FundingPeriod.P30), Bitfinex2Enums.Section.HIST,
                Bitfinex2Enums.SortOrder.OLD_TO_NEW, new BitfinexApiCallback<List<Candle>>()
                {
                    @Override
                    public void onResponse(List<Candle> candles, ApiResponse response)
                    {
                        assertEquals(120, candles.size());
                        assertEquals(1470015000000L, candles.get(53).getMts());
                        assertEquals(3.37E-4, candles.get(53).getClose(), DELTA);
                        assertEquals(3.5616E-4, candles.get(53).getHigh(), DELTA);
                        assertEquals(14064.57590628, candles.get(53).getVolume(), DELTA);
                        notifyAsyncRequest();
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        notifyAsyncRequest();
                    }
                });
        waitAsyncRequest();
    }
}
































