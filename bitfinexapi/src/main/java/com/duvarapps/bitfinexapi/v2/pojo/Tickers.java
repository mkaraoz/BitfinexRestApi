package com.duvarapps.bitfinexapi.v2.pojo;

import java.util.List;

public class Tickers
{
    private List<TradingPair> tradingPairs;
    private List<FundingCurrency> fundingCurrencies;

    public List<TradingPair> getTradingPairs()
    {
        return tradingPairs;
    }

    public void setTradingPairs(List<TradingPair> tradingPairs)
    {
        this.tradingPairs = tradingPairs;
    }

    public List<FundingCurrency> getFundingCurrencies()
    {
        return fundingCurrencies;
    }

    public void setFundingCurrencies(List<FundingCurrency> fundingCurrencies)
    {
        this.fundingCurrencies = fundingCurrencies;
    }
}
