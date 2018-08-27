package com.duvarapps.bitfinexapi.v2.pojo;

/**
 * Created by mk on 06.03.2018.
 */

public class Candle
{
    private long mts;
    private double open;
    private double close;
    private double high;
    private double low;
    private double volume;

    public long getMts()
    {
        return mts;
    }

    public void setMts(long mts)
    {
        this.mts = mts;
    }

    public double getOpen()
    {
        return open;
    }

    public void setOpen(double open)
    {
        this.open = open;
    }

    public double getClose()
    {
        return close;
    }

    public void setClose(double close)
    {
        this.close = close;
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

    public double getVolume()
    {
        return volume;
    }

    public void setVolume(double volume)
    {
        this.volume = volume;
    }
}
