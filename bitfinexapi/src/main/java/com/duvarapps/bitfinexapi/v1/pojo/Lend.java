package com.duvarapps.bitfinexapi.v1.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Lend
{
    @SerializedName("rate") @Expose private double rate;
    @SerializedName("amount_used") @Expose private double amountUsed;
    @SerializedName("amount_lent") @Expose private double amountLent;
    @SerializedName("timestamp") @Expose private double timestamp;

    public void setRate(double rate)
    {
        this.rate = rate;
    }

    public double getRate()
    {
        return rate;
    }

    public void setAmountUsed(double amountUsed)
    {
        this.amountUsed = amountUsed;
    }

    public double getAmountUsed()
    {
        return amountUsed;
    }

    public void setAmountLent(double amountLent)
    {
        this.amountLent = amountLent;
    }

    public double getAmountLent()
    {
        return amountLent;
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