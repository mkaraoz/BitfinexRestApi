package com.duvarapps.bitfinexapi.v2.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mk on 06.03.2018.
 */

public class CandleHolder
{
    private List<Candle> CandleList = new ArrayList<>();

    public List<Candle> getCandleList()
    {
        return CandleList;
    }

    public void setCandleList(List<Candle> CandleList)
    {
        this.CandleList = CandleList;
    }
}
