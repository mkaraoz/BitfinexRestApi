package com.duvarapps.bitfinexapi.helper.candle_key;

import com.duvarapps.bitfinexapi.helper.Bitfinex2Enums;

public class AggregateFundingCandleKey implements CandleKey
{
    private final Bitfinex2Enums.TimeFrame timeFrame;
    private final String symbolFunding;
    private final Bitfinex2Enums.Aggregate aggregate;
    private final Bitfinex2Enums.FundingPeriod start;
    private final Bitfinex2Enums.FundingPeriod end;

    public AggregateFundingCandleKey(Bitfinex2Enums.TimeFrame timeFrame, String symbolFunding, Bitfinex2Enums.Aggregate aggregate, Bitfinex2Enums.FundingPeriod start, Bitfinex2Enums.FundingPeriod end)
    {
        this.timeFrame = timeFrame;
        this.symbolFunding = symbolFunding;
        this.aggregate = aggregate;
        this.start = start;
        this.end = end;
    }

    @Override
    public String getKey()
    {
        return "trade:" + timeFrame.toString() + ":" + symbolFunding + ":" + aggregate.toString() + ":" + start.toString() + ":" + end.toString();
    }
}
