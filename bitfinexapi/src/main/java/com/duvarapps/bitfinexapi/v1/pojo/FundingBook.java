package com.duvarapps.bitfinexapi.v1.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class FundingBook
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
        @SerializedName("period") @Expose private int period;
        @SerializedName("rate") @Expose private double rate;
        @SerializedName("frr") @Expose private String frr;
        @SerializedName("timestamp") @Expose private double timestamp;

        public void setAmount(double amount)
        {
            this.amount = amount;
        }

        public double getAmount()
        {
            return amount;
        }

        public void setPeriod(int period)
        {
            this.period = period;
        }

        public int getPeriod()
        {
            return period;
        }

        public void setRate(double rate)
        {
            this.rate = rate;
        }

        public double getRate()
        {
            return rate;
        }

        public void setFrr(String frr)
        {
            this.frr = frr;
        }

        public String getFrr()
        {
            return frr;
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
        @SerializedName("period") @Expose private int period;
        @SerializedName("rate") @Expose private double rate;
        @SerializedName("frr") @Expose private String frr;
        @SerializedName("timestamp") @Expose private double timestamp;

        public void setAmount(double amount)
        {
            this.amount = amount;
        }

        public double getAmount()
        {
            return amount;
        }

        public void setPeriod(int period)
        {
            this.period = period;
        }

        public int getPeriod()
        {
            return period;
        }

        public void setRate(double rate)
        {
            this.rate = rate;
        }

        public double getRate()
        {
            return rate;
        }

        public void setFrr(String frr)
        {
            this.frr = frr;
        }

        public String getFrr()
        {
            return frr;
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