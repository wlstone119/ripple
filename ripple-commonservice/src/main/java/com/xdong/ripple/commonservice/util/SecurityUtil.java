package com.xdong.ripple.commonservice.util;

import com.antgroup.zmxy.openplatform.api.internal.util.AESUtil;
import com.xdong.ripple.commonservice.util.security.AESUtils;
import com.xdong.ripple.commonservice.util.security.Base64Utils;

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
        String key = Base64Utils.encode(com.xdong.ripple.commonservice.util.security.AESUtils.randomKey("AES", 128,
                                                                                                        null));

        byte[] keyByte = com.xdong.ripple.commonservice.util.security.AESUtils.randomKey("AES", 128, null);
        String emsg = Base64Utils.encode(AESUtils.encrypt("AES", "1".getBytes(), keyByte, null));
        System.out.println("默认：" + emsg);

        System.out.println("默认："
                           + new String(AESUtils.decrypt("AES", Base64Utils.decode(emsg), keyByte, null), "UTF-8"));

        System.out.println("密钥：" + key);
        String encryptMsg = com.xdong.ripple.commonservice.util.security.AESUtil.encrypt(key, "1");
        System.out.println("加密：" + encryptMsg);
        System.out.println("解密：" + com.xdong.ripple.commonservice.util.security.AESUtil.decrypt(key, encryptMsg));

        String key2 = AESUtil.initKey();
        System.out.println("芝麻密钥：" + key2 + "===" + key2.length());
        System.out.println("芝麻加密：" + AESUtil.encrypt("1", aesBase64Key, charset));
        System.out.println("芝麻解密："
                           + AESUtil.decrypt(AESUtil.encrypt("1", aesBase64Key, charset), aesBase64Key, charset));

    }
}
