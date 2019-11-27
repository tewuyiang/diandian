package com.diandian.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式话工具类
 */
public class DateTimeUtil {

    private static SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String datetimeToString(Date date) {
        return simpleDateFormat.format(date);
    }
}
