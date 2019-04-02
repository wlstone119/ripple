package com.xdong.ripple.commonservice.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
    public static String encrypt(String body) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        // 算法/模式/补码方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(VECTOR.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encodeAES = cipher.doFinal(body.getBytes(CHARSET));
        // base64 makeResponse
        return new BASE64Encoder().encode(encodeAES);
    }

    /**
     * 解密
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String decrypt(String body) throws Exception {
        // base64 decode
        byte[] decode64 = new BASE64Decoder().decodeBuffer(body);
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        // 算法/模式/补码方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(VECTOR.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] decodeAES = cipher.doFinal(decode64);
        return new String(decodeAES, CHARSET);
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
