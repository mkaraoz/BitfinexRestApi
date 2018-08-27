package com.duvarapps.bitfinexapi.v1.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Ticker
{
    @SerializedName("volume") @Expose private double volume;
    @SerializedName("high") @Expose private double high;
    @SerializedName("low") @Expose private double low;
    @SerializedName("ask") @Expose private double ask;
    @SerializedName("mid") @Expose private double mid;
    @SerializedName("bid") @Expose private double bid;
    @SerializedName("last_price") @Expose private double lastPrice;
    @SerializedName("timestamp") @Expose private double timestamp;

    public void setVolume(double volume)
    {
        this.volume = volume;
    }

    public double getVolume()
    {
        return volume;
    }

    public void setHigh(double high)
    {
        this.high = high;
    }

    public double getHigh()
    {
        return high;
    }

    public void setLow(double low)
    {
        this.low = low;
    }

    public double getLow()
    {
        return low;
    }

    public void setAsk(double ask)
    {
        this.ask = ask;
    }

    public double getAsk()
    {
        return ask;
    }

    public void setMid(double mid)
    {
        this.mid = mid;
    }

    public double getMid()
    {
        return mid;
    }

    public void setBid(double bid)
    {
        this.bid = bid;
    }

    public double getBid()
    {
        return bid;
    }

    public void setLastPrice(double lastPrice)
    {
        this.lastPrice = lastPrice;
    }

    public double getLastPrice()
    {
        return lastPrice;
    }

    public void setTimestamp(double timestamp)
    {
        this.timestamp = timestamp;
    }

    public double getTimestamp()
    {
        return timestamp;
    }
}