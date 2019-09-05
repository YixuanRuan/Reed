package com.example.StaticFunc;

import java.util.Calendar;
import java.util.Date;

public class Static {
    public static Date GetCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY,8);
        return calendar.getTime();
    }
    public static String Md2Txt(String str) {
        str = str.replaceAll("# ","");
        str = str.replaceAll("#","");
        str = str.replaceAll("-","");
        str = str.replaceAll("\\*","");
        return str;
    }
}
