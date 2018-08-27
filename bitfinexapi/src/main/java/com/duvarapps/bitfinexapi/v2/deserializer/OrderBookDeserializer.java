package com.duvarapps.bitfinexapi.v2.deserializer;

import com.duvarapps.bitfinexapi.v2.pojo.OrderBook;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBookFunding;
import com.duvarapps.bitfinexapi.v2.pojo.OrderBookTrading;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderBookDeserializer implements JsonDeserializer<OrderBook>
{
    @Override
    public OrderBook deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        List<OrderBookTrading> tradingBooks = new ArrayList<>();
        List<OrderBookFunding> fundingBooks = new ArrayList<>();

        JsonArray jsonArray = json.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++)
        {
            JsonArray orderBookArray = jsonArray.get(i).getAsJsonArray();
            int size = orderBookArray.size();
            if (size == 3)
            {
                /*
                 * 0. PRICE	    float	Price level
                 * 1. COUNT	    int	    Number of orders at that price level
                 * 2. ±AMOUNT   float	Total amount available at that price level. If AMOUNT > 0 then bid else ask.
                 */
                OrderBookTrading bookTrading = new OrderBookTrading();
                bookTrading.setPrice(orderBookArray.get(0).getAsDouble());
                bookTrading.setCount(orderBookArray.get(1).getAsInt());
                bookTrading.setAmount(orderBookArray.get(2).getAsDouble());

                tradingBooks.add(bookTrading);
            }
            else if (size == 4)
            {
                /*
                 * 0. RATE	    float	Rate level
                 * 1. PERIOD	float	Period level (Currency only)
                 * 2. COUNT	    int	    Number of orders at that price level
                 * 3. ±AMOUNT	float	Total amount available at that price level. If AMOUNT > 0 then ask else bid.
                 */
                OrderBookFunding bookFunding = new OrderBookFunding();
                bookFunding.setRate(orderBookArray.get(0).getAsDouble());
                bookFunding.setPeriod(orderBookArray.get(1).getAsInt());
                bookFunding.setCount(orderBookArray.get(2).getAsInt());
                bookFunding.setAmount(orderBookArray.get(3).getAsDouble());

                fundingBooks.add(bookFunding);
            }
        }

        OrderBook orderBook = new OrderBook();
        orderBook.setFundingBooks(fundingBooks);
        orderBook.setTradingBooks(tradingBooks);
        return orderBook;
    }
}
