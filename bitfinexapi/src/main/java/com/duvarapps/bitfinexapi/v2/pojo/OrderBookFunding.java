package com.duvarapps.bitfinexapi.v2.pojo;

/**
 * Created by mk on 07.03.2018.
 */

public class OrderBookFunding
{
    private double rate;
    private int period;
    private int count;
    private double amount;

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

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }
}
