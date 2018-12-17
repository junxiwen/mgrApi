package com.ys.mgr.util.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5、SHA256等
 *
 *
 * Date: 2017/12/8 09:25
 */
public class MyHashUtils {
    /**
     * 对输入字符串进行单次SHA散列.
     *
     * @param input
     *
     * @return
     */
    public static String sha1(String input) {
        try {
            return digest(input.getBytes("utf-8"), null, 1, "SHA");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对输入字符串进行单次SHA256散列.
     *
     * @param input
     *
     * @return
     */
    public static String sha256(String input) {
        try {
            return digest(input.getBytes("utf-8"), null, 1, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对输入字符串进行单次SHA256散列.
     *
     * @param input
     *
     * @return
     */
    public static String sha512(String input) {
        try {
            return digest(input.getBytes("utf-8"), null, 1, "SHA-512");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对输入字符串进行单次md5散列.
     *
     * @param input
     *
     * @return
     */
    public static String md5(String input) {
        try {
            return digest(input.getBytes("utf-8"), null, 1, "MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对输入字符串进行md5散列.
     *
     * @param input
     * @param iterations 散列次数
     *
     * @return
     */
    public static String md5(String input, int iterations) {
        try {
            return digest(input.getBytes("utf-8"), null, iterations, "MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对输入字符串进行md5散列.
     *
     * @param input
     * @param salt       盐
     * @param iterations 散列次数
     *
     * @return
     */
    public static String md5(String input, String salt, int iterations) {
        try {
            if (salt == null) {
                salt = "";
            }
            return digest(input.getBytes("utf-8"), salt.getBytes("utf-8"), iterations, "MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String fileMd5(File file) {
        FileInputStream in = null;
        try {
            if (!file.exists()) {
                return null;
            }
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            return bi.toString(16);
        } catch (Exception e) {
            return null;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String fileMd5(String path) {
        return fileMd5(new File(path));
    }

    /**
     * 对字符串进行散列, 支持md5
     *
     * @throws NoSuchAlgorithmException
     */
    private static String digest(byte[] input, byte[] salt, int iterations, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        if (salt != null) {
            digest.update(salt);
        }

        byte[] result = digest.digest(input);

        for (int i = 1; i < iterations; i++) {
            digest.reset();
            result = digest.digest(result);
        }
        return toHex(result);
    }

    public static String toHex(byte hash[]) {
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

}
