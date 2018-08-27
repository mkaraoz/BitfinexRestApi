package com.duvarapps.bitfinexapi.v2.deserializer;

import com.duvarapps.bitfinexapi.v2.pojo.FundingCurrency;
import com.duvarapps.bitfinexapi.v2.pojo.Tickers;
import com.duvarapps.bitfinexapi.v2.pojo.TradingPair;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TickersDeserializer implements JsonDeserializer<Tickers>
{
    @Override
    public Tickers deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        List<TradingPair> tradingPairs = new ArrayList<>();
        List<FundingCurrency> fundingCurrencies = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++)
        {
            JsonArray tickerArray = jsonArray.get(i).getAsJsonArray();
            String symbol = tickerArray.get(0).getAsString();
            if (symbol.startsWith("t"))
            {
                TradingPair tp = createTradingPair(tickerArray);
                tradingPairs.add(tp);
            }
            else if (symbol.startsWith("f"))
            {
                FundingCurrency fc = createFundingCurrency(tickerArray);
                fundingCurrencies.add(fc);
            }
        }

        Tickers tickers = new Tickers();
        tickers.setTradingPairs(tradingPairs);
        tickers.setFundingCurrencies(fundingCurrencies);
        return tickers;
    }

    private TradingPair createTradingPair(JsonArray jsonArray)
    {
        /*
         * 0.  SYMBOL            String   a trading pair, formed prepending a "t" before the pair (i.e tBTCUSD, tETHUSD)
         * 1.  BID               float    Price of last highest bid,
         * 2.  BID_SIZE          float    Size of the last highest bid,
         * 3.  ASK               float	  Price of last lowest ask,
         * 4.  ASK_SIZE          float    Size of the last lowest ask,
         * 5.  DAILY_CHANGE      float	  Amount that the last price has changed since yesterday,
         * 6.  DAILY_CHANGE_PERC float	  Amount that the price has changed expressed in percentage terms,
         * 7.  LAST_PRICE        float	  Price of the last trade,
         * 8.  VOLUME            float	  Daily volume,
         * 9.  HIGH              float	  Daily high,
         * 10. LOW               float	  Daily low
         */
        TradingPair tp = new TradingPair();
        tp.setSymbol(jsonArray.get(0).getAsString());
        tp.setBid(jsonArray.get(1).getAsDouble());
        tp.setBidSize(jsonArray.get(2).getAsDouble());
        tp.setAsk(jsonArray.get(3).getAsDouble());
        tp.setAskSize(jsonArray.get(4).getAsDouble());
        tp.setDailyChange(jsonArray.get(5).getAsDouble());
        tp.setDailyChangePerc(jsonArray.get(6).getAsDouble());
        tp.setLastPrice(jsonArray.get(7).getAsDouble());
        tp.setVolume(jsonArray.get(8).getAsDouble());
        tp.setHigh(jsonArray.get(9).getAsDouble());
        tp.setLow(jsonArray.get(10).getAsDouble());
        return tp;
    }

    private FundingCurrency createFundingCurrency(JsonArray jsonArray)
    {
        /*
         * 0. SYMBOL            String  a margin currency, formed prepending a f before the currency (i.e fUSD, fBTC)
         * 1. FRR               float   Flash Return Rate - average of all fixed rate funding over the last hour
         * 2. BID               float   Price of last highest bid
         * 3. BID_PERIOD        int     Bid period covered in days
         * 4. BID_SIZE          float   Size of the last highest bid
         * 5. ASK               float   Price of last lowest ask
         * 6. ASK_PERIOD        int     Ask period covered in days
         * 7. ASK_SIZE          float   Size of the last lowest ask
         * 8. DAILY_CHANGE      float   Amount that the last price has changed since yesterday
         * 9. DAILY_CHANGE_PERC float   Amount that the price has changed expressed in percentage terms
         * 10. LAST_PRICE       float   Price of the last trade
         * 11. VOLUME           float   Daily volume
         * 12. HIGH             float   Daily high
         * 13. LOW              float   Daily low
         */
        FundingCurrency fc = new FundingCurrency();
        fc.setSymbol(jsonArray.get(0).getAsString());
        fc.setFrr(jsonArray.get(1).getAsDouble());
        fc.setBid(jsonArray.get(2).getAsDouble());
        fc.setBid_period(jsonArray.get(3).getAsInt());
        fc.setBidSize(jsonArray.get(4).getAsDouble());
        fc.setAsk(jsonArray.get(5).getAsDouble());
        fc.setAskPeriod(jsonArray.get(6).getAsInt());
        fc.setAskSize(jsonArray.get(7).getAsDouble());
        fc.setDailyChange(jsonArray.get(8).getAsDouble());
        fc.setDailyChangePerc(jsonArray.get(9).getAsDouble());
        fc.setLastPrice(jsonArray.get(10).getAsDouble());
        fc.setVolume(jsonArray.get(11).getAsDouble());
        fc.setHigh(jsonArray.get(12).getAsDouble());
        fc.setLow(jsonArray.get(13).getAsDouble());
        return fc;
    }
}
