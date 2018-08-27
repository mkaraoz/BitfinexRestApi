package com.duvarapps.bitfinexapi.helper.candle_key;

import com.duvarapps.bitfinexapi.helper.Bitfinex2Enums;

public class FundingCandleKey implements CandleKey
{
    private final Bitfinex2Enums.TimeFrame timeFrame;
    private final String symbolFunding;
    private final Bitfinex2Enums.FundingPeriod period;

    public FundingCandleKey(Bitfinex2Enums.TimeFrame timeFrame, String symbolFunding, Bitfinex2Enums.FundingPeriod period)
    {
        this.timeFrame = timeFrame;
        this.symbolFunding = symbolFunding;
        this.period = period;
    }

    @Override
    public String getKey()
    {
        return  "trade:" + timeFrame.toString() + ":" + symbolFunding + ":" + period.toString();
    }
}
