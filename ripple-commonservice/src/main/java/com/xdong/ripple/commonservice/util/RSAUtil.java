package com.xdong.ripple.commonservice.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 类RSAUtil.java的实现描述：RSA加解密 
 * @author wanglei Mar 31, 2019 9:31:45 PM
 */
public class RSAUtil {

    public static final String  KEY_ALGORITHM = "RSA";

    public static final String  PADDING       = "RSA/ECB/PKCS1Padding";

    private static final String PUBLIC_KEY    = "rsa_public_key";
    private static final String PRIVATE_KEY   = "rsa_private_key";
    private static final String ENCODING      = "UTF-8";

    /**
     * 加密<br>
     * <h2>用公钥加密
     * <h3><br>
     * 
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, String base64PublicKeyStr) throws Exception {
        byte[] inputBytes = content.getBytes(ENCODING);
        byte[] outputBytes = encryptByPublicKey(inputBytes, base64PublicKeyStr);
        return Base64.encodeBase64String(outputBytes);
    }

    /**
     * 加密<br>
     * <h2>用私钥加密
     * <h2><br>
     * 
     * @param content
     * @param base64PrivateKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String content, String base64PrivateKeyStr) throws Exception {
        byte[] inputBytes = content.getBytes(ENCODING);
        byte[] outputBytes = encryptByPrivateKey(inputBytes, base64PrivateKeyStr);
        return Base64.encodeBase64String(outputBytes);
    }

    /**
     * 解密<br>
     * <h2>用公钥解密
     * <h2><br>
     * 
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String content, String base64PublicKeyStr) throws Exception {
        byte[] inputBytes = Base64.decodeBase64(content);
        byte[] outputBytes = decryptByPublicKey(inputBytes, base64PublicKeyStr);
        return new String(outputBytes, ENCODING);
    }

    /**
     * 解密<br>
     * <h2>用私钥解密
     * <h2><br>
     * 
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String content, String base64PrivateKeyStr) throws Exception {
        byte[] inputBytes = Base64.decodeBase64(content);
        byte[] outputBytes = decryptByPrivateKey(inputBytes, base64PrivateKeyStr);
        return new String(outputBytes, ENCODING);
    }

    /**
     * 加密<br>
     * <h2>用公钥加密
     * <h2><br>
     * 
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] content, String base64PublicKeyStr) throws Exception {
        // 对公钥解密
        byte[] publicKeyBytes = Base64.decodeBase64(base64PublicKeyStr);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(content);
    }

    /**
     * 加密<br>
     * <h2>用私钥加密
     * <h2><br>
     * 
     * @param content
     * @param base64PrivateKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] content, String base64PrivateKeyStr) throws Exception {
        // 对密钥解密
        byte[] privateKeyBytes = Base64.decodeBase64(base64PrivateKeyStr);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(content);
    }

    /**
     * 解密<br>
     * <h2>用公钥解密
     * <h2><br>
     * 
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] content, String base64PublicKeyStr) throws Exception {
        // 对密钥解密
        byte[] publicKeyBytes = Base64.decodeBase64(base64PublicKeyStr);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(content);
    }

    /**
     * 解密<br>
     * <h2>用私钥解密
     * <h2><br>
     * 
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] content, String base64PrivateKeyStr) throws Exception {
        // 对密钥解密
        byte[] privateKeyBytes = Base64.decodeBase64(base64PrivateKeyStr);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(content);
    }

    /**
     * 取得私钥 <br>
     * 
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getBase64PrivateKeyStr(Map<String, Key> keyMap) throws Exception {
        Key key = keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得公钥 <br>
     * 
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getBase64PublicKeyStr(Map<String, Key> keyMap) throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 初始化密钥
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024); // 初始化RSA1024安全些

        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 公钥

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 私钥

        Map<String, Key> keyMap = new HashMap<String, Key>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

}
