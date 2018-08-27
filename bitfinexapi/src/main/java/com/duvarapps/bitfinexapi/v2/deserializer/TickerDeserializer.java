package com.duvarapps.bitfinexapi.v2.deserializer;

import com.duvarapps.bitfinexapi.v2.pojo.FundingCurrency;
import com.duvarapps.bitfinexapi.v2.pojo.Ticker;
import com.duvarapps.bitfinexapi.v2.pojo.TradingPair;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TickerDeserializer implements JsonDeserializer<Ticker>
{
    @Override
    public Ticker deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        TradingPair tradingPair = null;
        FundingCurrency fundingCurrency = null;

        JsonArray jsonArray = json.getAsJsonArray();
        int size = jsonArray.size();
        if (size == 10) // trading
        {
            tradingPair = createTradingPair(jsonArray);
        }
        else if (size == 13) // funding
        {
            fundingCurrency = createFundingCurrency(jsonArray);
        }

        Ticker ticker = new Ticker();
        ticker.setTradingPair(tradingPair);
        ticker.setFundingCurrency(fundingCurrency);
        return ticker;
    }

    private TradingPair createTradingPair(JsonArray jsonArray)
    {
        /*
         * 0. BID               float	Price of last highest bid,
         * 1. BID_SIZE          float   Size of the last highest bid,
         * 2. ASK               float	Price of last lowest ask,
         * 3. ASK_SIZE          float   Size of the last lowest ask,
         * 4. DAILY_CHANGE      float	Amount that the last price has changed since yesterday,
         * 5. DAILY_CHANGE_PERC float	Amount that the price has changed expressed in percentage terms,
         * 6. LAST_PRICE        float	Price of the last trade,
         * 7. VOLUME            float	Daily volume,
         * 8. HIGH              float	Daily high,
         * 9. LOW               float	Daily low
         */
        TradingPair tp = new TradingPair();
        tp.setBid(jsonArray.get(0).getAsDouble());
        tp.setBidSize(jsonArray.get(1).getAsDouble());
        tp.setAsk(jsonArray.get(2).getAsDouble());
        tp.setAskSize(jsonArray.get(3).getAsDouble());
        tp.setDailyChange(jsonArray.get(4).getAsDouble());
        tp.setDailyChangePerc(jsonArray.get(5).getAsDouble());
        tp.setLastPrice(jsonArray.get(6).getAsDouble());
        tp.setVolume(jsonArray.get(7).getAsDouble());
        tp.setHigh(jsonArray.get(8).getAsDouble());
        tp.setLow(jsonArray.get(9).getAsDouble());
        return tp;
    }

    private FundingCurrency createFundingCurrency(JsonArray jsonArray)
    {
        /*
         * trading pairs (ex. fBTC)
         * 0. FRR               float Flash Return Rate - average of all fixed rate funding over the last hour
         * 1. BID               float Price of last highest bid
         * 2. BID_PERIOD        int Bid period covered in days
         * 3. BID_SIZE          float Size of the last highest bid
         * 4. ASK               float Price of last lowest ask
         * 5. ASK_PERIOD        int Ask period covered in days
         * 6. ASK_SIZE          float Size of the last lowest ask
         * 7. DAILY_CHANGE      float Amount that the last price has changed since yesterday
         * 8. DAILY_CHANGE_PERC float Amount that the price has changed expressed in percentage terms
         * 9.  LAST_PRICE       float Price of the last trade
         * 10. VOLUME           float Daily volume
         * 11. HIGH             float Daily high
         * 12. LOW              float Daily low
         */
        FundingCurrency fc = new FundingCurrency();
        fc.setFrr(jsonArray.get(0).getAsDouble());
        fc.setBid(jsonArray.get(1).getAsDouble());
        fc.setBid_period(jsonArray.get(2).getAsInt());
        fc.setBidSize(jsonArray.get(3).getAsDouble());
        fc.setAsk(jsonArray.get(4).getAsDouble());
        fc.setAskPeriod(jsonArray.get(5).getAsInt());
        fc.setAskSize(jsonArray.get(6).getAsDouble());
        fc.setDailyChange(jsonArray.get(7).getAsDouble());
        fc.setDailyChangePerc(jsonArray.get(8).getAsDouble());
        fc.setLastPrice(jsonArray.get(9).getAsDouble());
        fc.setVolume(jsonArray.get(10).getAsDouble());
        fc.setHigh(jsonArray.get(11).getAsDouble());
        fc.setLow(jsonArray.get(12).getAsDouble());
        return fc;
    }


}
