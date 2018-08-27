package com.duvarapps.bitfinexapi.v2.pojo;

/**
 * Created by mk on 07.03.2018.
 */

public class OrderBookTrading
{
    private double price;
    private int count;
    private double amount;

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
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
