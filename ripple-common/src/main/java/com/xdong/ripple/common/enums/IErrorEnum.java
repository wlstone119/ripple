package com.xdong.ripple.common.enums;

public enum IErrorEnum {

    USER_FEED_FORMAT_ERROR(1, "用户反馈参数错误");

    private int    errorCode;
    private String msg;

    private IErrorEnum(int errorCode, String msg){
        this.errorCode = errorCode;
        this.msg = msg;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
