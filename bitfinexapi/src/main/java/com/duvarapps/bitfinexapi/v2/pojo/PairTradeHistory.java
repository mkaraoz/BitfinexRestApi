package com.duvarapps.bitfinexapi.v2.pojo;

/**
 * Created by mk on 06.03.2018.
 */

public class PairTradeHistory
{
    private int id;
    private long mts;
    private double amount;
    private double price;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getMts()
    {
        return mts;
    }

    public void setMts(long mts)
    {
        this.mts = mts;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}
