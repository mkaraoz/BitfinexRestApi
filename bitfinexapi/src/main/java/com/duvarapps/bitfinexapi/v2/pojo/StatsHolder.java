package com.duvarapps.bitfinexapi.v2.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mk on 06.03.2018.
 */

public class StatsHolder
{
    private List<Stats> statsList = new ArrayList<>();

    public List<Stats> getStatsList()
    {
        return statsList;
    }

    public void setStatsList(List<Stats> statsList)
    {
        this.statsList = statsList;
    }
}
