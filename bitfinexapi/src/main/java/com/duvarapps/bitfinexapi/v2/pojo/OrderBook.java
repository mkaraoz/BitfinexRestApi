package com.duvarapps.bitfinexapi.v2.pojo;

import java.util.List;

public class OrderBook
{
    private List<OrderBookTrading> tradingBooks;
    private List<OrderBookFunding> fundingBooks;

    public List<OrderBookTrading> getTradingBooks()
    {
        return tradingBooks;
    }

    public void setTradingBooks(List<OrderBookTrading> tradingBooks)
    {
        this.tradingBooks = tradingBooks;
    }

    public List<OrderBookFunding> getFundingBooks()
    {
        return fundingBooks;
    }

    public void setFundingBooks(List<OrderBookFunding> fundingBooks)
    {
        this.fundingBooks = fundingBooks;
    }
}
