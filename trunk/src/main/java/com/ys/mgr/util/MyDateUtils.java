package com.ys.mgr.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 *
 * Date: 2017/12/8 09:41
 */
public class MyDateUtils extends DateUtils {
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy", "yyyy-MM-dd H:m:s",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yy-MM-dd H:m:s", "y-M-d H:m:s", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss", "yyyy-MM", "yyyy-M", "yy-M", "yyyy-M-d", "M/d/yyyy", "yyyy-MM-dd HH:mm", "MM/dd/yyyy HH:mm", "yyyyMMdd",
            "yyyy年MM月dd日", "yyyy年MM月dd日HH时mm分ss秒", "yy年M月d日", "yy年M月d日H时m分s秒", "yy年M月d日", "yyyy年MM月", "yy/M/d H:m:s", "M/d/yy H:m:s",
            "yyyyMMddHHmmss", "MMddyyyyHHmmss", "yyyyMMddHHmmssSSS", "yyyyMMddHHmmssZ", "yyyyMMdd hh:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "M/d/yy h:mm a", "yyyy/MM/dd", "dd/MM/yyyy", "M/d/yy", "yyyyMMMdd", "MMddyyyy", "yyMd", "hh:mm:ss", "h:m:s",
            "EEE, d MMM yyyy HH:mm:ss Z", "yyyyy.MMMMM.dd GGG hh:mm aaa", "EEE, MMM d, ''yy", "yyyy.MM.dd G 'at' HH:mm:ss z",
            "yyyy-MM-dd'T'HH:mm:ss'Z'", "EEE MMM dd HH:mm:ss z yyyy", "EEE MMM d HH:mm:ss Z yyyy"};

    /**
     * 获取当天日期 yyyy-MM-dd
     */
    public static Date getCurrentDay() {
        Calendar c = Calendar.getInstance();
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取当天日期 yyyy-MM-dd HH:mm:ss
     */
    public static Date getCurrentTime() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * 将指定的日期加上指定的秒数
     */
    public static Date addSecs(Date d, int seconds) {
        if(seconds == 0) return d;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }
    /**
     * 增加
     * @param d
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date d, int minutes) {
        if(minutes == 0) return d;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    public static String TimeStamp2Date(String timestampString, String formats){
        Long timestamp = Long.parseLong(timestampString)*1000;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }

    public static String TimeStamp2DateThousand(String timestampString, String formats){
        Long timestamp = Long.parseLong(timestampString);
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }


    /**
     * 指定日期的后一天  yyyy-MM-dd
     *
     * @param d 指定的日期
     */
    public static Date getNextDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取当天日期之前的日期
     *
     * @param i 之前几天
     */
    public static Date getPreDate(int i) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -i);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 今天之前的第7天
     *
     * @return
     */
    public static Date getLast7Day() {
        return getPreDate(7);
    }

    /**
     * /**
     * 得到当前日期yyyy-MM-dd格式字符串
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前时间指定格式的字符串
     *
     * @param pattern 格式
     *
     * @return
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到当前时间yyyy-MM-dd HH:mm:ss格式字符串
     */
    public static String getDateTime() {
        return getDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化date为pattern格式的字符串
     *
     * @param date    待格式化的日期
     * @param pattern 需要转换成的格式， 为空默认格式（yyyy-MM-dd）
     *
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (pattern == null || pattern.trim().equals("")) {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        } else {
            formatDate = DateFormatUtils.format(date, pattern);
        }
        return formatDate;
    }

    /**
     * 格式化date为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date 待格式化的日期
     *
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期型字符串转化为日期 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy", "yyyy-MM-dd H:m:s",
     * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yy-MM-dd H:m:s", "y-M-d H:m:s", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm:ss",
     * "MM/dd/yyyy HH:mm:ss", "yyyy-MM", "yyyy-M", "yy-M", "yyyy-M-d", "M/d/yyyy", "yyyy-MM-dd HH:mm", "MM/dd/yyyy HH:mm", "yyyyMMdd",
     * "yyyy年MM月dd日", "yyyy年MM月dd日HH时mm分ss秒", "yy年M月d日", "yy年M月d日H时m分s秒", "yy年M月d日", "yyyy年MM月", "yy/M/d H:m:s", "M/d/yy H:m:s",
     * "yyyyMMddHHmmss", "MMddyyyyHHmmss", "yyyyMMddHHmmssSSS", "yyyyMMddHHmmssZ", "yyyyMMdd hh:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
     * "M/d/yy h:mm a", "yyyy/MM/dd", "dd/MM/yyyy", "M/d/yy", "yyyyMMMdd", "MMddyyyy", "yyMd", "hh:mm:ss", "h:m:s",
     * "EEE, d MMM yyyy HH:mm:ss Z", "yyyyy.MMMMM.dd GGG hh:mm aaa", "EEE, MMM d, ''yy", "yyyy.MM.dd G 'at' HH:mm:ss z",
     * "yyyy-MM-dd'T'HH:mm:ss'Z'", "EEE MMM dd HH:mm:ss z yyyy", "EEE MMM d HH:mm:ss Z yyyy" }
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        try {
            return parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 把一个毫秒数转换为时间格式（天,时:分:秒.毫秒）
     *
     * @param timeMillis 毫秒数
     *
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数（取整，大于等于24小时才算一天）
     *
     * @param before 较早（小）的时间
     * @param after  较晚（大）的时间
     *
     * @return
     */
    public static double getDiffDays1(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 两个日期之间的自然天数，日历上的字面差
     *
     * @param before 较早（小）的时间
     * @param after  较晚（大）的时间
     *
     * @return
     */
    public static int getDiffDays2(Date before, Date after) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(after);
        c2.setTime(before);
        int day1 = c1.get(Calendar.DAY_OF_YEAR);
        int day2 = c2.get(Calendar.DAY_OF_YEAR);
        if (day1 < day2) {
            // 跨年了
            if (isLeapYear(c2.get(Calendar.YEAR))) {
                day1 += 366;
            } else {
                day1 += 365;
            }
        }
        int day = day1 - day2;
        return day;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 是否闰年
     *
     * @param date
     */
    public static boolean isLeapYear(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year = c1.get(Calendar.YEAR);
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 把指定日期的时分秒都设置为0
     *
     * @param c Calendar 指定的日期
     */
    private static void setNoTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static Date getCurrentMonthFirst() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1);// 设为当前月的1号
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取当月最后一天
     *
     * @return
     */
    public static Date getCurrentMonthLast() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1);// 设为当前月的1号
        c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取指定日期所在月第一天
     */
    public static Date getCurrentMonthFirst(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);// 设为当前月的1号
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取指定日期所在月最后一天
     */
    public static Date getCurrentMonthLast(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);// 设为当前月的1号
        c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取指定日期所在上一月第一天
     */
    public static Date getPreviousMonthFirst(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);// 设为指定月的1号
        c.add(Calendar.MONTH, -1);// 减一个月，变为上月的1号
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取指定日期所在上一月最后一天
     */
    public static Date getPreviousMonthLast(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);// 把日期设置为指定月第一天
        c.add(Calendar.DATE, -1);// 减一天，上月最后一天
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取指定日期所在下一月第一天
     */
    public static Date getNextMonthFirst(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);// 设为指定月的1号
        c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取指定日期所在下一月最后一天
     */
    public static Date getNextMonthLast(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);// 把日期设置为指定月第一天
        c.add(Calendar.MONTH, 2);// 加两个月，下下月第一天
        c.add(Calendar.DATE, -1);// 减一天，下月最后一天
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获取过去的天数
     *
     * @param date
     *
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     *
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     *
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 两个日期之间的自然天数差
     *
     * @param date1 比较大的时间
     * @param date2 比较小的时间
     *
     * @return
     */
    public static int getDays(Date date1, Date date2) {
        return getDiffDays2(date1, date2);
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 指定时间的后一个小时的时间
     */
    public static Date getNextHour(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.HOUR_OF_DAY, 1);
        return c.getTime();
    }


    /**
     * 获得当前日期与本周日相差的天数
     */
    private static int getMondayPlus() {
        Calendar c = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        return 1 - dayOfWeek;
    }

    /**
     * 获得指定日期与本周日相差的天数
     */
    private static int getMondayPlus(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        return 1 - dayOfWeek;
    }

    /**
     * 本周日的日期
     */
    public static Date getCurrentSunday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, getMondayPlus() + 6);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 本周一的日期
     */
    public static Date getCurrentMonday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, getMondayPlus());
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获得上周日的日期
     */
    public static Date getPreviousSunday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, getMondayPlus() + 6 - 7);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获得上周一的日期
     */
    public static Date getPreviousMonday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, getMondayPlus() - 7);
        setNoTime(c);
        return c.getTime();
    }

    public static Date getPrePreviousMonday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, getMondayPlus() - 7-7);
        setNoTime(c);
        return c.getTime();
    }
    /**
     * 获得下周日的日期
     */
    public static Date getNextSunday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, getMondayPlus() + 6 + 7);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 获得下周一的日期
     */
    public static Date getNextMonday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, getMondayPlus() + 7);
        setNoTime(c);
        return c.getTime();
    }
    public static String weekdayName_cn[] = {"周日","周一","周二","周三","周四","周五","周六"};

    public static String getDayOfWeek(Date date,String[] weekdayName){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek<0) dayOfWeek=0;
        return weekdayName[dayOfWeek];
    }


    /**
     * 指定日期所在周周日的日期
     */
    public static Date getCurrentSunday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, getMondayPlus(date) + 6);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 指定日期所在周周一的日期
     */
    public static Date getCurrentMonday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, getMondayPlus(date));
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 指定日期所在周上周日的日期
     */
    public static Date getPreviousSunday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, getMondayPlus(date) + 6 - 7);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 指定日期所在周上周一的日期
     */
    public static Date getPreviousMonday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, getMondayPlus(date) - 7);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 指定日期所在周下周日的日期
     */
    public static Date getNextSunday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, getMondayPlus(date) + 6 + 7);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 指定日期所在周下周一的日期
     */
    public static Date getNextMonday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, getMondayPlus(date) + 7);
        setNoTime(c);
        return c.getTime();
    }

    /**
     * 两个时间之间的小时数
     * @param date1	开始时间
     * @param date2	当前时间
     * @return		小时
     */
    public static int getHour(Date date1, Date date2) {
        long hour = (date1.getTime() - date2.getTime()) / (60 * 60 * 1000);
        return (int) hour;
    }

    /**
     * 两个时间之间的小时数
     * @param date1	开始时间
     * @param date2	当前时间
     * @return		小时
     */
    public static int getMinutes(Date date1, Date date2) {
        long minutes = (date1.getTime() - date2.getTime()) / (60 * 1000);
        return (int) minutes;
    }

}
