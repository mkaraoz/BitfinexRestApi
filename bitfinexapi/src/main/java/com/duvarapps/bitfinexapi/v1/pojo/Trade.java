package com.duvarapps.bitfinexapi.v1.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Trade
{
    @SerializedName("amount") @Expose private double amount;
    @SerializedName("price") @Expose private double price;
    @SerializedName("exchange") @Expose private String exchange;
    @SerializedName("type") @Expose private String type;
    @SerializedName("tid") @Expose private int tid;
    @SerializedName("timestamp") @Expose private double timestamp;

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return price;
    }

    public void setExchange(String exchange)
    {
        this.exchange = exchange;
    }

    public String getExchange()
    {
        return exchange;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setTid(int tid)
    {
        this.tid = tid;
    }

    public int getTid()
    {
        return tid;
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