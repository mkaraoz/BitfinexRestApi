package com.duvarapps.bitfinexapi.helper.candle_key;

import com.duvarapps.bitfinexapi.helper.Bitfinex2Enums;

public class TradingCandleKey implements CandleKey
{
    private final Bitfinex2Enums.TimeFrame timeFrame;
    private final String symbolTrading;

    public TradingCandleKey(Bitfinex2Enums.TimeFrame timeFrame, String symbolTrading)
    {
        this.timeFrame = timeFrame;
        this.symbolTrading = symbolTrading;
    }

    @Override
    public String getKey()
    {
        return "trade:" + timeFrame.toString() + ":" + symbolTrading;
    }
}
