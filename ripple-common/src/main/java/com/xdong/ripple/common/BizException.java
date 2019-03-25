package com.xdong.ripple.common;

@SuppressWarnings("serial")
public class BizException extends RuntimeException {

    /** 错误码 */
    private String code;

    public static BizException create(String code, String message, Exception cause) {
        return new BizException(code, message, cause);
    }

    public static BizException create(String message, Exception cause) {
        return new BizException(message, cause);
    }

    public static BizException create(String message) {
        return new BizException(message);
    }

    public BizException(String message, Exception cause){
        super(message, cause);
    }

    public BizException(String message){
        super(message);
    }

    public BizException(String code, String message, Exception cause){
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
