package com.lewis.configcenter.common.login;

public enum LoginCacheEnum {

    OPENID_ASSOCIATE("openid_associate"),

    LOGIN_COOKIE("login_cookie"),

    /**
     * 只能在一处登录
     */
    LOGIN_SINGLE("login_single"),;

    private String root = "snail_reader_finance";

    private String stringValue;

    private LoginCacheEnum(String stringValue) {
        this.stringValue = stringValue;
    }

    public String make(String key) {
        return root + "#" + stringValue + "#" + key;
    }

}
