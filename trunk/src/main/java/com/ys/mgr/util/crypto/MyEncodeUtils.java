package com.ys.mgr.util.crypto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 各种编码解码工具
 *
 *
 * Date: 2017/12/11 16:03
 */
public class MyEncodeUtils {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    /**
     * Hex解码.
     *
     * @throws DecoderException
     */
    public static byte[] decodeHex(String input) throws DecoderException {
        return Hex.decodeHex(input.toCharArray());
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * Base64解码.
     */
    public static String decodeBase64String(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Html 转码.
     */
    public static String escapeHtml(String html) {
        if (html == null)
            return null;
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码.
     */
    public static String unescapeHtml(String htmlEscaped) {
        if (htmlEscaped == null)
            return null;
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String escapeXml(String xml) {
        if (xml == null)
            return null;
        return StringEscapeUtils.escapeXml10(xml);
    }

    /**
     * Xml 解码.
     */
    public static String unescapeXml(String xmlEscaped) {
        if (xmlEscaped == null)
            return null;
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     *
     * @throws UnsupportedEncodingException
     */
    public static String urlEncode(String str) throws UnsupportedEncodingException {
        if (str == null)
            return null;
        return URLEncoder.encode(str, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     *
     * @throws UnsupportedEncodingException
     */
    public static String urlDecode(String str) throws UnsupportedEncodingException {
        if (str == null)
            return null;
        return URLDecoder.decode(str, DEFAULT_URL_ENCODING);
    }


    /**
     * Unicode ISO_8859_1转换成UTF-8字符集
     *
     * @param str 待转换字符串
     *
     * @return 转换完成字符串
     */
    public static String UnicodeToGB(String str) throws UnsupportedEncodingException {
        if (str == null)
            return null;
        return new String(str.getBytes("ISO_8859_1"), DEFAULT_URL_ENCODING);
    }

}
