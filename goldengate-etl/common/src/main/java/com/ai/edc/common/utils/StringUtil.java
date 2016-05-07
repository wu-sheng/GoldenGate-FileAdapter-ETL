package com.ai.edc.common.utils;


/**
 * 字符串辅助类 Title: Dev-Framework<br>
 * Description: <br>
 * Date: 2011-4-25 <br>
 * Copyright (c) 2012 SystemGo.org <br>
 *
 * @author wusheng
 */
public class StringUtil {
    /**
     * 判断字符串是否为 null/空/无内容
     *
     * @param str
     * @return
     * @author wusheng
     */
    public static boolean isBlank(String str) {
        if (null == str)
            return true;
        if ("".equals(str.trim()))
            return true;

        return false;
    }

    /**
     * 字符串相等 null和空字符串认为相等，忽略字符串前后空格
     *
     * @param str1
     * @param str2
     * @return
     * @author wusheng
     */
    public static boolean compareString(String str1, String str2) {
        if (null == str1) {
            str1 = "";
        }
        if (null == str2) {
            str2 = "";
        }
        if (str1.trim().equals(str2.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 将多个对象进行组装<br>
     *
     * @param parts
     * @return
     * @author wusheng
     */
    public static String assemblingString(Object... parts) {
        StringBuilder builder = new StringBuilder();
        if (CollectionUtil.isEmpty(parts)) {
            return null;
        }
        for (Object part : parts) {
            if (part == null) {
                part = "";
            }
            builder.append(part);
        }
        return builder.toString();
    }

    /**
     * 删除str指定的后缀
     *
     * @param str
     * @param suffix
     * @return
     */
    public static String removeSuffix(String str, String suffix) {
        if (null == str)
            return null;
        if ("".equals(str.trim()))
            return "";

        if (null == suffix || "".equals(suffix))
            return str;

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }

        throw new RuntimeException(StringUtil.assemblingString(str,
                " 没有按指定字符串", suffix, "结尾"));
    }

    /**
     * 将对象转成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    /**
     * 中文语句长度判断
     */
    public static int lengthOfChineseSentence(String value) {
        int valueLength = 0;
        char c;
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
            c = value.charAt(i);
            if (c > 255) {
                valueLength += 2;
            } else {
                valueLength++;
            }
        }
        return valueLength;
    }

    public static String subStringOfChineseSentence(String value, int endLength) {
        int valueLength = 0;
        char c;
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        int i = 0;
        for (; i < value.length(); i++) {
			/* 获取一个字符 */
            c = value.charAt(i);
            if (c > 255) {
                valueLength += 2;
            } else {
                valueLength++;
            }
            if (valueLength > endLength) {
                break;
            }
        }
        return value.substring(0, i);
    }


    /**
     * 处理String中的特殊字符
     *
     * @param s
     * @return
     */
    public static String quoteReplacement(String s) {
        if ((s.indexOf('\\') == -1) && (s.indexOf('$') == -1))
            return s;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                sb.append('\\');
                sb.append('\\');
            } else if (c == '$') {
                sb.append('\\');
                sb.append('$');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
