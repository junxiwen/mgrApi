package com.ys.mgr.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * 字符串工具
 *
 *
 * Date: 2017/12/8 09:32
 */
@Slf4j
public class MyStringUtils extends StringUtils {
    public static final String CHARSET_NAME = "UTF-8";
    public static final String EMPTY = "";

    /**
     * 转换为字节数组
     *
     * @param str
     *
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                log.error("getBytes", e);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 转换为字符串
     *
     * @param bytes
     *
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            log.error("toString", e);
            return EMPTY;
        }
    }

    /**
     * Unicode ISO_8859_1转换成UTF-8字符集
     *
     * @param str 待转换字符串
     *
     * @return 转换完成字符串
     */
    public static String UnicodeToUTF8(String str) {
        if (isEmpty(str)) {
            return EMPTY;
        } else {
            try {
                return new String(str.getBytes("ISO_8859_1"), CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                log.error("UnicodeToUTF8", e);
                return str;
            }
        }
    }

    /**
     * 获得0-9,A-Z范围的随机数
     *
     * @param length 随机数长度
     *
     * @return String
     */
    public static String getRandomChar(int length) {

        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(36)]);
        }
        return buffer.toString();
    }


    /**
     * 获得0-9范围的随机数
     *
     * @param length 随机数长度
     *
     * @return String
     */
    public static String getRandomNum(int length) {

        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(10)]);
        }
        return buffer.toString();
    }

    /**
     * 获取制定范围内的随机整数
     *
     * @param min
     * @param max
     *
     * @return
     */
    public static int getRandomNum(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 获取时间戳+随机数的不重复主键值：长度20
     *
     * @return
     */
    public static String getPrimaryKey() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateformat.format(new Date()) + getRandomNum(3);
    }

    /**
     * 截取字符串左侧指定长度的字符串
     *
     * @param input 输入字符串
     * @param count 截取长度
     *
     * @return 截取字符串
     */
    public static String left(String input, int count) {
        if (isEmpty(input)) {
            return "";
        }
        count = (count > input.length()) ? input.length() : count;
        return input.substring(0, count);
    }

    /**
     * 截取字符串右侧指定长度的字符串
     *
     * @param input 输入字符串
     * @param count 截取长度
     *
     * @return 截取字符串
     */
    public static String right(String input, int count) {
        if (isEmpty(input)) {
            return "";
        }
        count = (count > input.length()) ? input.length() : count;
        return input.substring(input.length() - count, input.length());
    }


    /**
     * 分隔字符串成数组. <p/> 使用StringTokenizer，String的split函数不能处理'|'符号
     *
     * @param input 输入字符串
     * @param delim 分隔符
     *
     * @return 分隔后数组
     */
    public static String[] splitString(String input, String delim) {
        if (isEmpty(input)) {
            return new String[0];
        }
        ArrayList<String> al = new ArrayList<String>();
        for (StringTokenizer stringtokenizer = new StringTokenizer(input, delim); stringtokenizer.hasMoreTokens();
             al.add(stringtokenizer.nextToken())) {
        }
        String result[] = new String[al.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (String) al.get(i);
        }
        return result;
    }

    /**
     * 换行符转换成html的标签
     *
     * @param str
     *
     * @return
     */
    public String replaceNewlineToHtml(String str) {
        if (str.contains("\n")) {
            str = str.replace("\n", "<br>");
        }
        if (str.contains("\r")) {
            str = str.replace("\r", "&nbsp;");
        }
        return str;
    }

    /**
     * 将一些特殊的字符转换成转义字符
     *
     * @param str
     *
     * @return
     */
    public static String replaceSpecialCharToEscape(String str) {
        if (str.contains("<")) {
            str = str.replace("<", "&lt");
        }
        if (str.contains(">")) {
            str = str.replace(">", "&gt");
        }
        return str;
    }


}
