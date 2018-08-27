package com.duvarapps.bitfinexapi.helper;

public class Bitfinex2Enums
{
    public enum PriceAggregation
    {
        P0("P0"),
        P1("P1"),
        P2("P2"),
        P3("P3");

        private final String priceAggregationLevel;

        PriceAggregation(String priceAggregationLevel)
        {
            this.priceAggregationLevel = priceAggregationLevel;
        }

        public String level()
        {
            return this.priceAggregationLevel;
        }
    }

    public enum Section
    {
        LAST("last"),
        HIST("hist");

        private final String text;

        Section(final String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }

    public enum Side
    {
        LONG("long"),
        SHORT("short");

        private final String text;

        Side(final String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }

    public enum SortOrder
    {
        NEW_TO_OLD(0),
        OLD_TO_NEW(1);

        private final int sortOrder;

        SortOrder(final int sortOrder)
        {
            this.sortOrder = sortOrder;
        }

        public int order()
        {
            return sortOrder;
        }
    }

    public enum StatsKey
    {
        FUNDING_SIZE("funding.size"),
        CREDITS_SIZE("credits.size"),
        CREDITS_SIZE_SYM("credits.size.sym"),
        POS_SIZE("pos.size");

        private final String text;

        StatsKey(final String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }

    public enum TimeFrame
    {
        _1m("1m"),
        _5m("5m"),
        _15m("15m"),
        _30m("30m"),
        _1h("1h"),
        _3h("3h"),
        _6h("6h"),
        _12h("12h"),
        _1D("1D"),
        _7D("7D"),
        _14D("14D"),
        _1M("1M");

        private final String text;

        TimeFrame(final String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }

    public enum FundingPeriod
    {
        P2("p2"),
        P3("p3"),
        P4("p4"),
        P5("p5"),
        P6("p6"),
        P7("p7"),
        P8("p8"),
        P9("p9"),
        P10("p10"),
        P11("p11"),
        P12("p12"),
        P13("p13"),
        P14("p14"),
        P15("p15"),
        P16("p16"),
        P17("p17"),
        P18("p18"),
        P19("p19"),
        P20("p20"),
        P21("p21"),
        P22("p22"),
        P23("p23"),
        P24("p24"),
        P25("p25"),
        P26("p26"),
        P27("p27"),
        P28("p28"),
        P29("p29"),
        P30("p30");

        private final String text;

        FundingPeriod(final String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }

    public enum Aggregate
    {
        A10("a10"),
        A30("a30");

        private final String text;

        Aggregate(final String text)
        {
            this.text = text;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }


}
