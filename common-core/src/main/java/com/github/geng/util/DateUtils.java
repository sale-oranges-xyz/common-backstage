package com.github.geng.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.time.*;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateUtils {
    private DateUtils () {}

    public static String YYYY_MM_DD_HH_mm_ss  = "yyyy-MM-dd HH:mm:ss";

    public static Date formatDate(String text, String pattern) {
        try {
            DateFormatter dateFormatter = new DateFormatter(pattern);
            return dateFormatter.parse(text, Locale.getDefault());
        } catch (ParseException e) {
           log.error("date parse error", e);
        }
        return null;
    }


    /**
     * 获取某天的开始时间和结束时间
     * @param date 时间
     * @return Date[0] 某天开始时间  Date[1] 某天结束时间
     */
    public static Date[] getStartAndEndOfDate(Date date) {
        if (null == date) {
            date = new Date();
        }

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = date.toInstant().atZone(zoneId).toLocalDate();

        LocalDateTime startLocalDateTime = localDate.atTime(0, 0, 0, 0);
        LocalDateTime endLocalDateTime = localDate.atTime(23, 59, 59, 0);

        return new Date[] {
                Date.from(startLocalDateTime.atZone(zoneId).toInstant()),
                Date.from(endLocalDateTime.atZone(zoneId).toInstant())
        };
    }

    /**
     * 日期加减
     * @param date 目标日期
     * @param daysToAdd 正数表示加、负数表示减
     * @return 加减后日期
     */
    public static Date dayPlus(Date date, long daysToAdd) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = date.toInstant().atZone(zoneId).toLocalDateTime().plusDays(daysToAdd);
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }
}
