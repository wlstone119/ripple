package com.xdong.ripple.commonservice.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.antgroup.zmxy.openplatform.api.internal.util.Base64Util;

import java.security.SecureRandom;

public class AESUtil {

    private final static String CHARSET = "utf-8";
    /**
     * KEY(长度16)
     */
    private final static String KEY     = "V1dnjVpZbl38LKqX";

    /**
     * 向量(长度16)
     */
    private final static String VECTOR  = "kA9jHZbmLpOwDgTc";

    /**
     * 加密
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String body) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64Utils.decode(key), "AES");
        // 算法/模式/补码方式
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encodeAES = cipher.doFinal(body.getBytes(CHARSET));
        return Base64Utils.encode(encodeAES);
    }

    /**
     * 解密
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String decrypt(String aesBase64Key, String body) throws Exception {
        // base64 decode
        byte[] decode64 = Base64Utils.decode(body);
        SecretKeySpec skeySpec = new SecretKeySpec(Base64Utils.decode(aesBase64Key), "AES");
        // 算法/模式/补码方式
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decodeAES = cipher.doFinal(decode64);
        return new String(decodeAES, CHARSET);
    }

    public static String decrypt(String encryptedData, String aesBase64Key, String charset) throws Exception {
        // AES加密
        Cipher cipher = initCipher(aesBase64Key, Cipher.DECRYPT_MODE);

        byte[] bytOut = cipher.doFinal(Base64Util.base64ToByteArray(encryptedData));
        String result = new String(bytOut, charset);

        // ios加密的数据解密后会吗会出现ascii码等于0的填充值,需要去掉
        int indexOfNil = result.indexOf('\u0000');
        if (indexOfNil >= 0) {
            result = result.substring(0, indexOfNil);
        }
        return result;
    }

    /**
     * @param aesBase64Key
     * @param mode 指定是加密还是解密模式
     * @return
     * @throws Exception
     */
    public static Cipher initCipher(String aesBase64Key, int mode) throws Exception {
        SecretKeySpec skeySpec = getSecretKeySpec(aesBase64Key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, skeySpec);
        return cipher;
    }

    /**
     * 根据base64编码的key获取SecretKeySpec对象
     * 
     * @param aesBase64Key base64编码的key
     * @return SecretKeySpec对象
     * @throws Exception 获取SecretKeySpec对象异常
     */
    public static SecretKeySpec getSecretKeySpec(String aesBase64Key) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(Base64Utils.decode(aesBase64Key), "AES");
        return skeySpec;
    }

    /**
     * 随机KEY
     *
     * @param algorithm 算法
     * @param len 长度[128,192,256]
     * @param vector 向量
     * @return
     */
    public static byte[] randomKey(String algorithm, int len, byte[] vector) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        kg.init(len, new SecureRandom(vector));
        return kg.generateKey().getEncoded();
    }

    /**
     * 加密
     *
     * @param algorithm
     * @param data
     * @param key
     * @param vector
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String algorithm, byte[] data, byte[] key, byte[] vector) throws Exception {
        return operation(true, algorithm, data, key, vector);
    }

    /**
     * 解密
     *
     * @param algorithm
     * @param data
     * @param key
     * @param vector
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(String algorithm, byte[] data, byte[] key, byte[] vector) throws Exception {
        return operation(false, algorithm, data, key, vector);
    }

    /**
     * 操作解密
     *
     * @param encrypt 是否为加密
     * @param algorithm 算法
     * @param data 数据
     * @param key 加密KEY
     * @param vector 向量
     * @return
     */
    private static byte[] operation(boolean encrypt, String algorithm, byte[] data, byte[] key, byte[] vector) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(algorithm);
            boolean hasVector = null != vector;
            if (encrypt) {
                if (hasVector) {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(vector));
                } else {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                }
            } else {
                if (hasVector) {
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(vector));
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                }
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
