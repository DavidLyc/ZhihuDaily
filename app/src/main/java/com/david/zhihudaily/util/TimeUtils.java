package com.david.zhihudaily.util;

import java.util.Calendar;

public class TimeUtils {

    public static String TransformDateToTimeString(int year, int month, int day) {
        return "" + year
                + (month < 9 ? ("0" + (month + 1)) : (month + 1))
                + (day < 10 ? ("0" + day) : day);
    }

    public static String TransformDateToTimeString(Calendar calendar) {
        return TransformDateToTimeString(calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
}
