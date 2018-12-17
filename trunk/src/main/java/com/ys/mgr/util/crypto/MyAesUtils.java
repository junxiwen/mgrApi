package com.ys.mgr.util.crypto;

import com.ys.mgr.config.MyConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * AES加解密
 *
 *
 * Date: 2017/12/11 16:00
 */
@Slf4j
public class MyAesUtils {
    private static final byte[] iv = new String("1234567890123456").getBytes();

    private static byte[] iv8 = {1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * AES加密：先AES再base64最后URLEncode
     *
     * @param encryptString 待加密的明文字符串
     * @param encryptKey    加密的key，必须为16位字符
     *
     * @return 先AES再base64最后URLEncode的结果
     *
     * @throws Exception
     */
    public static String encrpt(String encryptString, String encryptKey) throws Exception {
        if (encryptString == null) {
            encryptString = "";
        }
        if (encryptKey == null) {
            log.error("encryptKey为空");
            throw new Exception("加密失败");
        }
        if (encryptKey.length() != 16) {
            log.error("encryptKey长度不等于16位");
            throw new Exception("加密失败");
        }
        try {
            //log.debug("加密前的原字符串: {}", encryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            byte[] keys = encryptKey.getBytes("UTF-8");
            SecretKeySpec key = new SecretKeySpec(keys, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 算法/模式/补码方式
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
            //log.debug("{} :AES加密之后的16进制数据: {}", encryptString, printHexString(encryptedData));
            String base64Str = new String(Base64.encodeBase64(encryptedData), "UTF-8");
            //log.debug("{} :AES&base64加密之后的字符串: {}", encryptString, base64Str);
            String result = URLEncoder.encode(base64Str, "UTF-8");
            log.debug("{} :AES&base64&URLEncode之后的最终加密结果: {}", encryptString, result);
            return result;
        } catch (Exception e) {
            log.error("加密失败", e);
            throw new Exception("加密失败");
        }
    }

    /**
     * AES解密
     *
     * @param decryptString 待解密的字符串
     * @param decryptKey    加密的key，必须为16位字符
     *
     * @return 解密出来的结果
     *
     * @throws Exception
     */
    public static String decrypt(String decryptString, String decryptKey) throws Exception {
        if (decryptString == null) {
            log.error("decryptString为空");
            throw new Exception("解密失败");
        }
        if (decryptKey == null) {
            log.error("decryptKey为空");
            throw new Exception("解密失败");
        }
        if (decryptKey.length() != 16) {
            log.error("decryptKey长度不等于16位");
            throw new Exception("解密失败");
        }

        try {
            // 判断是否需要URLDecode，因为base64只会出现+ = / 这几个特殊字符，所以只判断 + = /
            if (decryptString.contains("%2B") || decryptString.contains("%3D") || decryptString.contains("%2F")) {
                String urldecodeStr = URLDecoder.decode(decryptString, "UTF-8");
                log.debug("{} :待解密的字符串需要URLDecode: {}", decryptString, urldecodeStr);
                decryptString = urldecodeStr;
            }

            byte[] base64Byte = Base64.decodeBase64(decryptString.getBytes("UTF-8"));
            //log.debug("{} :base64解密之后的16进制数据: {}", printHexString(base64Byte));
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(base64Byte);
            String str = new String(decryptedData, "UTF-8");
            log.debug("{} :base64&AES解密之后的字符串: {}", str);
            return str;
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new Exception("解密失败");
        }
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台，调试用，不参与加密
     *
     * @param b
     *
     * @return
     */
    public static String printHexString(byte[] b) {
        String str = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            str += hex.toUpperCase();
        }
        return str;
    }

    public static void main(String[] args) throws Exception{
        String params = "aaa=aaa&bbb=第二第二&ccc=2018-01-01 00:00:00";
        String key = MyConst.HASH_PARAM_KEYS[0];
        log.info(MyAesUtils.encrpt(params, key));
    }
}
