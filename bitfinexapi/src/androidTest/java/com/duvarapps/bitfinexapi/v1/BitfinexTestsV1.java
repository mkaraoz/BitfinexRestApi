package com.duvarapps.bitfinexapi.v1;

import android.support.test.runner.AndroidJUnit4;

import com.duvarapps.bitfinexapi.core.ApiResponse;
import com.duvarapps.bitfinexapi.core.BitfinexApiCallback;
import com.duvarapps.bitfinexapi.core.Result;
import com.duvarapps.bitfinexapi.v1.api.BitfinexApi;
import com.duvarapps.bitfinexapi.v1.api.BitfinexFactory;
import com.duvarapps.bitfinexapi.v1.pojo.FundingBook;
import com.duvarapps.bitfinexapi.v1.pojo.Lend;
import com.duvarapps.bitfinexapi.v1.pojo.OrderBook;
import com.duvarapps.bitfinexapi.v1.pojo.Stats;
import com.duvarapps.bitfinexapi.v1.pojo.SymbolDetail;
import com.duvarapps.bitfinexapi.v1.pojo.Ticker;
import com.duvarapps.bitfinexapi.v1.pojo.Trade;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_FUNDING_BOOK_GET;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_FUNDING_BOOK_PULL;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_ORDER_BOOK_GET;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_ORDER_BOOK_PULL;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_STATS_GET;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_STATS_PULL;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_SYMBOLS;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_TICKER_GET;
import static com.duvarapps.bitfinexapi.v1.FakeResponses1.FAKE_TICKER_PULL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class BitfinexTestsV1
{
    private BitfinexApi createBitfinex(final String... fakeResponse)
    {
        if (fakeResponse.length != 0)
        {
            return BitfinexFactory.createClient(FakeBitfinexClient1.getFakeClient(fakeResponse[0]));
        }
        else
        {
            return BitfinexFactory.createClient();
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
        // Prevents rate limit
        Thread.sleep(1000);
    }

    @Test
    public void pullSymbols() throws Exception
    {
        String fakeResponse = FAKE_SYMBOLS;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        List<String> symbols = bfx.pullSymbols().body;
        assertTrue(symbols.size() == 269);
        assertTrue(symbols.contains("btcusd"));
        assertTrue(symbols.contains("ethusd"));
        assertTrue(symbols.contains("ltcusd"));
        assertTrue(symbols.contains("xrpusd"));
        assertTrue(symbols.contains("iotusd"));
        assertTrue(symbols.contains("etcusd"));
    }

    @Test
    public void getSymbols()
    {
        String fakeResponse = FAKE_SYMBOLS;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        bfx.getSymbols(new BitfinexApiCallback<List<String>>()
        {
            @Override
            public void onResponse(List<String> symbols, ApiResponse response)
            {
                assertTrue(symbols.size() == 269);
                assertTrue(symbols.contains("btcusd"));
                assertTrue(symbols.contains("ethusd"));
                assertTrue(symbols.contains("ltcusd"));
                assertTrue(symbols.contains("xrpusd"));
                assertTrue(symbols.contains("iotusd"));
                assertTrue(symbols.contains("etcusd"));

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
    public void pullTicker() throws Exception
    {
        String fakeResponse = FAKE_TICKER_PULL;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        Ticker ticker = bfx.pullTicker("btcusd").body;
        assertEquals(6484, ticker.getMid(), 1);
        assertEquals(6484, ticker.getBid(), 1);
        assertEquals(6485.0, ticker.getAsk(), 1);
        assertEquals(6490.2, ticker.getLastPrice(), 1);
        assertEquals(6245.0, ticker.getLow(), 1);
        assertEquals(6526.2, ticker.getHigh(), 1);
        assertEquals(30058.911, ticker.getVolume(), 1);
        assertEquals(1534894077, ticker.getTimestamp(), 1);
    }

    @Test
    public void getTicker()
    {
        String fakeResponse = FAKE_TICKER_GET;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        bfx.getTicker("btcusd", new BitfinexApiCallback<Ticker>()
        {
            @Override
            public void onResponse(Ticker ticker, ApiResponse response)
            {
                assertEquals(0, ticker.getMid(), 0.1);
                assertEquals(6484, ticker.getBid(), 1);
                assertEquals(6485.0, ticker.getAsk(), 1);
                assertEquals(6490.2, ticker.getLastPrice(), 1);
                assertEquals(6245.0, ticker.getLow(), 1);
                assertEquals(6526.2, ticker.getHigh(), 1);
                assertEquals(30058.911, ticker.getVolume(), 1);
                assertEquals(1534894077, ticker.getTimestamp(), 1);

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
    public void pullStats() throws Exception
    {
        String fakeResponse = FAKE_STATS_PULL;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        List<Stats> stats = bfx.pullStats("etcusd").body;
        assertEquals(stats.size(), 3);
        assertEquals(1, stats.get(0).getPeriod(), 1);
        assertEquals(464652.15033289, stats.get(0).getVolume(), 1);
        assertEquals(7, stats.get(1).getPeriod(), 1);
        assertEquals(9953197.60049782, stats.get(1).getVolume(), 1);
        assertEquals(30, stats.get(2).getPeriod(), 1);
        assertEquals(30965303.2177267, stats.get(2).getVolume(), 1);
    }

    @Test
    public void getStats()
    {
        String fakeResponse = FAKE_STATS_GET;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        bfx.getStats("etcusd", new BitfinexApiCallback<List<Stats>>()
        {
            @Override
            public void onResponse(List<Stats> stats, ApiResponse response)
            {
                assertEquals(stats.size(), 3);
                assertEquals(1, stats.get(0).getPeriod(), 1);
                assertEquals(464652.15033289, stats.get(0).getVolume(), 1);
                assertEquals(7, stats.get(1).getPeriod(), 1);
                assertEquals(9953197.60049782, stats.get(1).getVolume(), 1);
                assertEquals(30, stats.get(2).getPeriod(), 1);
                assertEquals(30965303.2177267, stats.get(2).getVolume(), 1);

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
    public void pullFundingBook() throws Exception
    {
        String fakeResponse = FAKE_FUNDING_BOOK_PULL;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        FundingBook fb = bfx.pullFundingBook("btc").body;

        assertEquals(4, fb.getAsks().size());
        assertEquals(3, fb.getBids().size());

        assertEquals(48, fb.getBids().get(0).getAmount(), 1);
        assertEquals(10, fb.getBids().get(2).getPeriod());

        assertEquals(3.1049, fb.getAsks().get(0).getRate(), 0.2);
        assertEquals(1534931331, fb.getAsks().get(3).getTimestamp(), 1);
    }

    @Test
    public void getFundingBook()
    {
        String fakeResponse = FAKE_FUNDING_BOOK_GET;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        bfx.getFundingBook("usd", new BitfinexApiCallback<FundingBook>()
        {
            @Override
            public void onResponse(FundingBook fb, ApiResponse response)
            {
                if (response.code() == 200)
                {
                    assertEquals(3, fb.getAsks().size());
                    assertEquals(4, fb.getBids().size());

                    assertEquals(7163, fb.getBids().get(0).getAmount(), 1);
                    assertEquals(30, fb.getBids().get(2).getPeriod());

                    assertEquals(2.8, fb.getAsks().get(0).getRate(), 0.2);
                    assertEquals(1534932667, fb.getAsks().get(1).getTimestamp(), 1);
                }
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
    public void pullOrderBook() throws Exception
    {
        String fakeResponse = FAKE_ORDER_BOOK_PULL;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        OrderBook ob = bfx.pullOrderBook("etcusd").body;
        assertEquals(2, ob.getBids().size());
        assertEquals(6, ob.getAsks().size());

        assertEquals(12.9, ob.getBids().get(1).getPrice(), 1);
        assertEquals(145, ob.getBids().get(0).getAmount(), 1);
        assertEquals(1534934120, ob.getBids().get(1).getTimestamp(), 1);

        assertEquals(135, ob.getAsks().get(1).getAmount(), 0.1);
        assertEquals(13.056, ob.getAsks().get(5).getPrice(), 1);
    }

    @Test
    public void getOrderBook()
    {
        String fakeResponse = FAKE_ORDER_BOOK_GET;
        BitfinexApi bfx = createBitfinex(fakeResponse);

        bfx.getOrderBook("etcusd", new BitfinexApiCallback<OrderBook>()
        {
            @Override
            public void onResponse(OrderBook ob, ApiResponse response)
            {
                if (response.code() == 200)
                {
                    assertEquals(2, ob.getBids().size());
                    assertEquals(6, ob.getAsks().size());

                    assertEquals(12.9, ob.getBids().get(1).getPrice(), 1);
                    assertEquals(145, ob.getBids().get(0).getAmount(), 1);
                    assertEquals(1534934120, ob.getBids().get(1).getTimestamp(), 1);

                    assertEquals(135, ob.getAsks().get(1).getAmount(), 0.1);
                    assertEquals(13.056, ob.getAsks().get(5).getPrice(), 1);
                }
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
    public void pullTrades() throws Exception
    {
        BitfinexApi bfx = createBitfinex();

        Trade t = bfx.pullTrades("etcusd").body.get(0);
        assertTrue(t.getAmount() > 0);
        assertEquals(t.getExchange(), "bitfinex");
        assertTrue(t.getTid() > 200142280);
        assertFalse(t.getPrice() < 0);
        assertTrue(t.getTimestamp() > 1534934050);
    }

    @Test
    public void getTrades()
    {
        BitfinexApi bfx = createBitfinex();

        bfx.getTrades("etcusd", new BitfinexApiCallback<List<Trade>>()
        {
            @Override
            public void onResponse(List<Trade> trades, ApiResponse response)
            {
                Trade t = trades.get(0);
                assertTrue(t.getAmount() > 0);
                assertEquals(t.getExchange(), "bitfinex");
                assertTrue(t.getTid() > 200142280);
                assertFalse(t.getPrice() < 0);
                assertTrue(t.getTimestamp() > 1534934050);

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
    public void pullLend() throws Exception
    {
        BitfinexApi bfx = createBitfinex();

        Result<List<Lend>> result = bfx.pullLend("usd");
        if (result.responseCode == 200)
        {
            Lend lend = result.body.get(0);
            assertTrue(lend.getAmountLent() > 0);
            assertTrue(lend.getAmountUsed() > 0);
            assertFalse(lend.getRate() < 0);
        }
    }

    @Test
    public void getLend()
    {
        BitfinexApi bfx = createBitfinex();

        bfx.getLend("usd", new BitfinexApiCallback<List<Lend>>()
        {
            @Override
            public void onResponse(List<Lend> lends, ApiResponse response)
            {
                Lend lend = lends.get(0);
                assertTrue(lend.getAmountLent() > 0);
                assertTrue(lend.getAmountUsed() > 0);
                assertFalse(lend.getRate() < 0);

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
    public void pullSymbolDetails() throws Exception
    {
        BitfinexApi bfx = createBitfinex();

        Result<List<SymbolDetail>> result = bfx.pullSymbolDetails();
        if (result.responseCode == 200)
        {
            SymbolDetail symbolDetail = result.body.get(0);
            assertTrue(symbolDetail.getInitialMargin() > 0);
            assertTrue(symbolDetail.getMaximumOrderSize() > 100);
            assertTrue(symbolDetail.getPricePrecision() > 1);
            assertEquals(symbolDetail.getExpiration(), "NA");
            assertFalse(symbolDetail.getMinimumOrderSize() < 0);
        }
    }

    @Test
    public void getSymbolDetails()
    {
        BitfinexApi bfx = createBitfinex();

        bfx.getSymbolDetails(new BitfinexApiCallback<List<SymbolDetail>>()
        {
            @Override
            public void onResponse(List<SymbolDetail> symbolDetails, ApiResponse response)
            {
                SymbolDetail symbolDetail = symbolDetails.get(0);
                assertTrue(symbolDetail.getInitialMargin() > 0);
                assertTrue(symbolDetail.getMaximumOrderSize() > 100);
                assertTrue(symbolDetail.getPricePrecision() > 1);
                assertEquals(symbolDetail.getExpiration(), "NA");
                assertFalse(symbolDetail.getMinimumOrderSize() < 0);

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