package com.duvarapps.bitfinexapi.v2.pojo;

/**
 * Created by mk on 06.03.2018.
 */

public class CurrencyTrades
{
    private int id;
    private long mts;
    private double amount;
    private double rate;
    private int period;

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

    public double getRate()
    {
        return rate;
    }

    public void setRate(double rate)
    {
        this.rate = rate;
    }

    public int getPeriod()
    {
        return period;
    }

    public void setPeriod(int period)
    {
        this.period = period;
    }
}
