package com.duvarapps.bitfinexapi.v1.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class SymbolDetail
{
    @SerializedName("maximum_order_size") @Expose private double maximumOrderSize;
    @SerializedName("initial_margin") @Expose private double initialMargin;
    @SerializedName("margin") @Expose private boolean margin;
    @SerializedName("minimum_margin") @Expose private double minimumMargin;
    @SerializedName("expiration") @Expose private String expiration;
    @SerializedName("pair") @Expose private String pair;
    @SerializedName("minimum_order_size") @Expose private double minimumOrderSize;
    @SerializedName("price_precision") @Expose private int pricePrecision;

    public void setMaximumOrderSize(double maximumOrderSize)
    {
        this.maximumOrderSize = maximumOrderSize;
    }

    public double getMaximumOrderSize()
    {
        return maximumOrderSize;
    }

    public void setInitialMargin(double initialMargin)
    {
        this.initialMargin = initialMargin;
    }

    public double getInitialMargin()
    {
        return initialMargin;
    }

    public void setMargin(boolean margin)
    {
        this.margin = margin;
    }

    public boolean getMargin()
    {
        return margin;
    }

    public void setMinimumMargin(double minimumMargin)
    {
        this.minimumMargin = minimumMargin;
    }

    public double getMinimumMargin()
    {
        return minimumMargin;
    }

    public void setExpiration(String expiration)
    {
        this.expiration = expiration;
    }

    public String getExpiration()
    {
        return expiration;
    }

    public void setPair(String pair)
    {
        this.pair = pair;
    }

    public String getPair()
    {
        return pair;
    }

    public void setMinimumOrderSize(double minimumOrderSize)
    {
        this.minimumOrderSize = minimumOrderSize;
    }

    public double getMinimumOrderSize()
    {
        return minimumOrderSize;
    }

    public void setPricePrecision(int pricePrecision)
    {
        this.pricePrecision = pricePrecision;
    }

    public int getPricePrecision()
    {
        return pricePrecision;
    }
}