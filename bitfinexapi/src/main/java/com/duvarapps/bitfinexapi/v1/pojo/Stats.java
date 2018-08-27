package com.duvarapps.bitfinexapi.v1.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Stats
{
    @SerializedName("volume") @Expose private double volume;
    @SerializedName("period") @Expose private int period;

    public void setVolume(double volume)
    {
        this.volume = volume;
    }

    public double getVolume()
    {
        return volume;
    }

    public void setPeriod(int period)
    {
        this.period = period;
    }

    public int getPeriod()
    {
        return period;
    }
}