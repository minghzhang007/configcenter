package com.lewis.configcenter.common.util;

public final class TimeTokenUtils {

    private TimeTokenUtils() {
    }

    public static String createTimeToken() {
        return DateUtils.toString(System.currentTimeMillis(), "yyyyMMddHHmmssSSS");
    }
}
