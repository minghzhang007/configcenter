package com.lewis.configcenter.common.util;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by Administrator on 2017/10/2.
 */
public class StringUtil extends StringUtils {

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private final static String whitespaces =
            " |\n|\r|\t|\f|\u000b|\u00a0|\u2000|\u2001|\u2002|\u2003|\u2004|\u2005|\u2006|\u2007|\u2008|\u2009|\u200a|\u200b|\u2028|\u2029|\u3000";

    public static String removeBlank(String str) {
        if (str == null)
            return null;
        return str.replaceAll(whitespaces, "");
    }

    public static String defaultIfBlank(Object obj, String defaultString) {
        if (obj == null) {
            return defaultString;
        } else {
            return StringUtils.defaultIfBlank(obj.toString(), defaultString);
        }
    }

    public static byte[] stringToBytes(String string, String charset) {
        try {
            return string.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String bytesToString(byte[] bytes, String charset) {
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean in(String value, boolean caseIgnore, String... ins) {
        if (caseIgnore) {
            for (String in : ins) {
                if (StringUtils.equalsIgnoreCase(value, in))
                    return true;
            }
        } else {
            for (String in : ins) {
                if (StringUtils.equals(value, in))
                    return true;
            }
        }
        return false;
    }
}
