package com.xdong.ripple.commonservice.util.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Random;

public class AESUtils {

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
     * @param encrypt   是否为加密
     * @param algorithm 算法
     * @param data      数据
     * @param key       加密KEY
     * @param vector    向量
     * @return
     */
    private static byte[] operation(boolean encrypt, String algorithm, byte[] data, byte[] key, byte[] vector) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(algorithm);
        IvParameterSpec iv = null;
        boolean hasVector = false;
        if (null != vector) {
            iv = new IvParameterSpec(vector);
            hasVector = true;
        }
        if (encrypt) {
            if (hasVector)
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            else
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } else {
            if (hasVector)
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            else
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }
        return cipher.doFinal(data);
    }

    /**
     * 随机KEY
     *
     * @param algorithm 算法
     * @param len       长度[128,192,256]
     * @param vector    向量
     * @return
     */
    public static byte[] randomKey(String algorithm, int len, byte[] vector) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        if (null == vector)
            kg.init(len);
        else
            kg.init(len, new SecureRandom(vector));
        return kg.generateKey().getEncoded();
    }

    /**
     * 随机vector
     *
     * @param len
     * @return
     */
    public static byte[] randomVector(int len) {
        int count = len / 8;
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString().getBytes();
    }
}
