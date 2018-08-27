package com.duvarapps.bitfinexapi.v2.pojo;

public class Ticker
{
    private TradingPair tradingPair;
    private FundingCurrency fundingCurrency;

    public TradingPair getTradingPair()
    {
        return tradingPair;
    }

    public void setTradingPair(TradingPair tradingPair)
    {
        this.tradingPair = tradingPair;
    }

    public FundingCurrency getFundingCurrency()
    {
        return fundingCurrency;
    }

    public void setFundingCurrency(FundingCurrency fundingCurrency)
    {
        this.fundingCurrency = fundingCurrency;
    }
}
