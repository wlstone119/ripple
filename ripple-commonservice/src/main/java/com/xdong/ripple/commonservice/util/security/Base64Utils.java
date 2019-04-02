package com.xdong.ripple.commonservice.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {

    public static String encode(byte[] bytes) throws Exception {
        return new BASE64Encoder().encode(bytes);
    }

    public static byte[] decode(String s) throws Exception {
        return new BASE64Decoder().decodeBuffer(s);
    }
}
