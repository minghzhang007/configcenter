package com.lewis.configcenter.common.login;

public class LoginConstants {

    public static final int SECONDS_PER_MINUTE = 60;

    public static final int SECONDS_PER_HOUR = 60 * SECONDS_PER_MINUTE;

    public static final int SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;

    public static final String LOGIN_COOKIE_NAME = "SNAIL_READER_FINANCE_LOGIN";

    public static final int LOGIN_COOKIE_EXPIRE = SECONDS_PER_HOUR / 2; // 登录cookie保存在缓存中的秒数

    public static final String REQUEST_LOGIN_ACCOUNT = "requestLoginAccount";

}
