package com.lewis.configcenter.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class NumberUtils {

    public static int parseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int default0(Integer value) {
        return value != null ? value : 0;
    }

    public static long default0(Long value) {
        return value != null ? value : 0;
    }

    public static int yuan2cents(String yuan) {
        //return new BigDecimal(StringUtils.removeBlank(yuan)).multiply(new BigDecimal(100)).intValueExact();
        return new BigDecimal(yuan).multiply(new BigDecimal(100)).intValueExact();
    }

    public static double cent2yuan(double cents) {
        return new BigDecimal(cents).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double toFixed2(double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String toRatio(double value) {
        return new BigDecimal(value).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
    }

    public static String cents2yuan(int cents) {
        if (cents == 0) {
            return "0";
        }
        String sign = cents > 0 ? "" : "-";
        long abs = Math.abs(cents);
        return sign + (abs / 100) + "." + StringUtils.leftPad(String.valueOf(abs % 100), 2, '0');
    }
}
