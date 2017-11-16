package com.lewis.configcenter.common.util;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public final class DateUtils {
    private DateUtils() {
    }

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_INT_FORMAT = "yyyyMMdd";

    //一天的毫秒数
    public static final Integer MILLIS_OF_DAY = 86400000;

    public static String toString(Long timeToken) {
        return toString(timeToken, DATE_TIME_FORMAT);
    }

    public static String toString(Long timeToken, String format) {
        DateTime dateTime = new DateTime(timeToken);
        return dateTime.toString(format);
    }

    /**
     * 获取两个时间戳相差的天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int apartDays(Long startTime, Long endTime) {
        return (int) ((endTime - startTime) / MILLIS_OF_DAY);
    }

    /**
     * 判断某个intMonth是否是某个月
     *
     * @param intMonth 格式：201702
     * @param month    数字 1...12 表示月份
     * @return
     */
    public static boolean isXMonth(Integer intMonth, int month) {
        if (intMonth == null || intMonth < 0) {
            return false;
        }
        return intMonth % 100 == month;
    }

    /**
     * 判断某个intMonth是否是一月
     *
     * @param intMonth
     * @return
     */
    public static boolean isJanuary(Integer intMonth) {
        return isXMonth(intMonth, 1);
    }

    /**
     * 判断某个intMonth是否是二月
     *
     * @param intMonth
     * @return
     */
    public static boolean isFebruary(Integer intMonth) {
        return isXMonth(intMonth, 2);
    }

    /**
     * 判断某个intMonth是否是三月
     *
     * @param intMonth
     * @return
     */
    public static boolean isMarch(Integer intMonth) {
        return isXMonth(intMonth, 3);
    }

    /**
     * 判断某个intMonth是否是四月
     *
     * @param intMonth
     * @return
     */
    public static boolean isApril(Integer intMonth) {
        return isXMonth(intMonth, 4);
    }

    /**
     * 判断某个intMonth是否是五月
     *
     * @param intMonth
     * @return
     */
    public static boolean isMay(Integer intMonth) {
        return isXMonth(intMonth, 5);
    }

    /**
     * 判断某个intMonth是否是六月
     *
     * @param intMonth
     * @return
     */
    public static boolean isJune(Integer intMonth) {
        return isXMonth(intMonth, 6);
    }

    /**
     * 判断某个intMonth是否是七月
     *
     * @param intMonth
     * @return
     */
    public static boolean isJuly(Integer intMonth) {
        return isXMonth(intMonth, 7);
    }

    /**
     * 判断某个intMonth是否是八月
     *
     * @param intMonth
     * @return
     */
    public static boolean isAugust(Integer intMonth) {
        return isXMonth(intMonth, 8);
    }

    /**
     * 判断某个intMonth是否是九月
     *
     * @param intMonth
     * @return
     */
    public static boolean isSeptember(Integer intMonth) {
        return isXMonth(intMonth, 9);
    }

    /**
     * 判断某个intMonth是否是十月
     *
     * @param intMonth
     * @return
     */
    public static boolean isOctober(Integer intMonth) {
        return isXMonth(intMonth, 10);
    }

    /**
     * 判断某个intMonth是否是十一月
     *
     * @param intMonth
     * @return
     */
    public static boolean isNovember(Integer intMonth) {
        return isXMonth(intMonth, 11);
    }

    /**
     * 判断某个intMonth是否是十二月
     *
     * @param intMonth
     * @return
     */
    public static boolean isDecember(Integer intMonth) {
        return isXMonth(intMonth, 12);
    }

    public static Integer timeToken2DayInt(Long timeToken) {
        return timeToken2DayInt(timeToken, 0);
    }

    public static Integer timeToken2DayInt(Long timeToken, int dayOffSet) {
        DateTime dateTime = new DateTime(timeToken).plusDays(dayOffSet);
        return Integer.parseInt(dateTime.toString(DATE_INT_FORMAT));
    }

    public static Long dateTimeString2TimeToken(String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = DateTime.parse(dateString, dateTimeFormatter);
        return dateTime.getMillis();
    }

    public static DateTime getDateTime(Integer dayInt) {
        int year = dayInt / 10000;
        int month = (dayInt / 100) % 100;
        Integer date = dayInt % 100;
        return new DateTime(year, month, date, 0, 0);
    }

    public static void main(String[] args) {




        DateTime dateTime1 = new DateTime("2017-11-10");
        DateTime dateTime2 = new DateTime("2017-07-26");
        System.out.println(dateTime1.getDayOfYear()-dateTime2.getDayOfYear());
        //1.获取该日期的一切日期统计表数据
        System.out.println(dateTime1.getMillis());
        System.out.println(dateTime2.getMillis());

        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 1020; i++) {
            list.add(i);
        }
        List<List<Integer>> partition = Lists.partition(list, list.size() / 200);
        System.out.println(partition);
        //1487402212391
        DateTime dateTime = new DateTime(1487402212391L);
        System.out.println(dateTime.toString("yyyy-MM-dd hh:mm:ss"));

        DateTime dateTime3 = new DateTime("2017-11-30");
        int diff = dateTime3.dayOfYear().get() - dateTime.dayOfYear().get();
        System.out.println(diff);

        System.out.println(dateTime3.dayOfMonth().withMaximumValue().equals(dateTime3));

        DateTime dateTime4 = new DateTime(1510734317373L);
        System.out.println(dateTime4.toString("yyyy-MM-dd hh:mm:ss"));

    }
}
