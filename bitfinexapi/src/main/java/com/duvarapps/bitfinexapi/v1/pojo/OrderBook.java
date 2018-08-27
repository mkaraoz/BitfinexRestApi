package com.duvarapps.bitfinexapi.v1.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class OrderBook
{
    @SerializedName("asks") @Expose private List<Asks> asks;
    @SerializedName("bids") @Expose private List<Bids> bids;

    public void setAsks(List<Asks> asks)
    {
        this.asks = asks;
    }

    public List<Asks> getAsks()
    {
        return asks;
    }

    public void setBids(List<Bids> bids)
    {
        this.bids = bids;
    }

    public List<Bids> getBids()
    {
        return bids;
    }

    public class Asks
    {
        @SerializedName("amount") @Expose private double amount;
        @SerializedName("price") @Expose private double price;
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

        public void setTimestamp(double timestamp)
        {
            this.timestamp = timestamp;
        }

        public double getTimestamp()
        {
            return timestamp;
        }
    }

    public class Bids
    {
        @SerializedName("amount") @Expose private double amount;
        @SerializedName("price") @Expose private double price;
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

        public void setTimestamp(double timestamp)
        {
            this.timestamp = timestamp;
        }

        public double getTimestamp()
        {
            return timestamp;
        }
    }
}