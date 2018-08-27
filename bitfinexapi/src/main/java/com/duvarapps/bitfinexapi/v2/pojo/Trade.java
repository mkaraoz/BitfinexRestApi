package com.duvarapps.bitfinexapi.v2.pojo;

import java.util.List;

public class Trade
{
    private List<PairTradeHistory> pairTradeHistory;
    private List<CurrencyTrades> currencyTrades;

    public List<PairTradeHistory> getPairTradeHistory()
    {
        return pairTradeHistory;
    }

    public void setPairTradeHistory(List<PairTradeHistory> pairTradeHistory)
    {
        this.pairTradeHistory = pairTradeHistory;
    }

    public List<CurrencyTrades> getCurrencyTrades()
    {
        return currencyTrades;
    }

    public void setCurrencyTrades(List<CurrencyTrades> currencyTrades)
    {
        this.currencyTrades = currencyTrades;
    }
}
