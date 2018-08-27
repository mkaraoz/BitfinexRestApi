package com.duvarapps.bitfinexapi.v2.pojo;

public class FundingCurrency extends TradingPair
{
    private double frr;
    private int bidPeriod;
    private int askPeriod;

    public double getFrr()
    {
        return frr;
    }

    public void setFrr(double frr)
    {
        this.frr = frr;
    }

    public int getBidPeriod()
    {
        return bidPeriod;
    }

    public void setBid_period(int bid_period)
    {
        this.bidPeriod = bid_period;
    }

    public int getAskPeriod()
    {
        return askPeriod;
    }

    public void setAskPeriod(int askPeriod)
    {
        this.askPeriod = askPeriod;
    }
}
