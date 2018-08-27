package com.duvarapps.bitfinexapi.helper;

public class TextOps
{
    public static String makeCommaSeparated(String... data)
    {
        StringBuilder sb = new StringBuilder();
        for (String s : data)
        {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
