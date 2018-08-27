package com.duvarapps.bitfinexapi.v2.pojo;

public class TradingPair
{
    protected String symbol;
    protected double bid;
    protected double bidSize;
    protected double ask;
    protected double askSize;
    protected double dailyChange;
    protected double dailyChangePerc;
    protected double lastPrice;
    protected double volume;
    protected double high;
    protected double low;

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public double getBid()
    {
        return bid;
    }

    public void setBid(double bid)
    {
        this.bid = bid;
    }

    public double getBidSize()
    {
        return bidSize;
    }

    public void setBidSize(double bidSize)
    {
        this.bidSize = bidSize;
    }

    public double getAsk()
    {
        return ask;
    }

    public void setAsk(double ask)
    {
        this.ask = ask;
    }

    public double getAskSize()
    {
        return askSize;
    }

    public void setAskSize(double askSize)
    {
        this.askSize = askSize;
    }

    public double getDailyChange()
    {
        return dailyChange;
    }

    public void setDailyChange(double dailyChange)
    {
        this.dailyChange = dailyChange;
    }

    public double getDailyChangePerc()
    {
        return dailyChangePerc;
    }

    public void setDailyChangePerc(double dailyChangePerc)
    {
        this.dailyChangePerc = dailyChangePerc;
    }

    public double getLastPrice()
    {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice)
    {
        this.lastPrice = lastPrice;
    }

    public double getVolume()
    {
        return volume;
    }

    public void setVolume(double volume)
    {
        this.volume = volume;
    }

    public double getHigh()
    {
        return high;
    }

    public void setHigh(double high)
    {
        this.high = high;
    }

    public double getLow()
    {
        return low;
    }

    public void setLow(double low)
    {
        this.low = low;
    }
}
