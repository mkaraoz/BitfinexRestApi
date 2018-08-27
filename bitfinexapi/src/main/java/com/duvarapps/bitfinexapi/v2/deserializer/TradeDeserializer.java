package com.duvarapps.bitfinexapi.v2.deserializer;

import com.duvarapps.bitfinexapi.v2.pojo.CurrencyTrades;
import com.duvarapps.bitfinexapi.v2.pojo.PairTradeHistory;
import com.duvarapps.bitfinexapi.v2.pojo.Trade;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TradeDeserializer implements JsonDeserializer<Trade>
{
    @Override
    public Trade deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        List<PairTradeHistory> pairTradeHistoryList = new ArrayList<>();
        List<CurrencyTrades> currencyTradesList = new ArrayList<>();

        JsonArray historicalData = json.getAsJsonArray();
        JsonArray tradeData = historicalData.get(0).getAsJsonArray();
        int size = tradeData.size();
        if (size == 4) // trading
        {
            /*
             * 0. ID
             * 1. MTS	    int	    millisecond time stamp
             * 2. AMOUNT	float	How much was bought (positive) or sold (negative).
             * 3. PRICE	    float	Price at which the trade was executed
             */
            for (int i = 0; i<historicalData.size(); i++)
            {
                tradeData = historicalData.get(i).getAsJsonArray();
                PairTradeHistory tradeHistory = new PairTradeHistory();
                tradeHistory.setId(tradeData.get(0).getAsInt());
                tradeHistory.setMts(tradeData.get(1).getAsLong());
                tradeHistory.setAmount(tradeData.get(2).getAsDouble());
                tradeHistory.setPrice(tradeData.get(3).getAsDouble());
                pairTradeHistoryList.add(tradeHistory);
            }

        }
        else if (size == 5) // funding
        {
            /*
             * 0. ID
             * 1. MTS	    long	millisecond time stamp
             * 2. AMOUNT	float	How much was bought (positive) or sold (negative).
             * 3. RATE	    float	Rate at which funding transaction occurred
             * 4. PERIOD	int	    Amount of time the funding transaction was for
             */
            for (int i = 0; i<historicalData.size(); i++)
            {
                tradeData = historicalData.get(i).getAsJsonArray();
                CurrencyTrades cth = new CurrencyTrades();
                cth.setId(tradeData.get(0).getAsInt());
                cth.setMts(tradeData.get(1).getAsLong());
                cth.setAmount(tradeData.get(2).getAsDouble());
                cth.setRate(tradeData.get(3).getAsDouble());
                cth.setPeriod(tradeData.get(4).getAsInt());
                currencyTradesList.add(cth);
            }
        }

        Trade trade = new Trade();
        trade.setCurrencyTrades(currencyTradesList);
        trade.setPairTradeHistory(pairTradeHistoryList);
        return trade;
    }
}
