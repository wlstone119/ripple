package com.xdong.ripple.commonservice.util;

import com.antgroup.zmxy.openplatform.api.internal.util.AESUtil;

/**
 * 类SecurityUtil.java的实现描述：加解密工具类
 * 
 * @author wanglei Mar 29, 2019 3:54:24 PM
 */
public class SecurityUtil {

    private static final String aesBase64Key = "Zf2KJCtNB5Te7G/4BbCcPg==";
    private static final String charset      = "UTF-8";

    /**
     * 加密信息
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public static String encryption(Object value) throws Exception {
        return AESUtil.encrypt(value.toString(), aesBase64Key, charset);
    }

    /**
     * 解密信息
     * 
     * @param encryptValue
     * @return
     * @throws Exception
     */
    public static String decryption(String encryptedData) throws Exception {
        return AESUtil.decrypt(encryptedData, aesBase64Key, charset);
    }

    public static void main(String[] args) throws Exception {
        String key = AESUtil.initKey();
        System.out.println(AESUtil.encrypt("1", aesBase64Key, charset));
        System.out.println(AESUtil.decrypt(AESUtil.encrypt("1", aesBase64Key, charset), aesBase64Key, charset));

    }
}
