package com.xdong.ripple.crawler.common;

import java.util.regex.Pattern;

/**
 * 资源过滤枚举
 * 
 * @author stone
 */
public enum PatternEnum {

 FILM(Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$")),

 IMG(Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$"));

    private Pattern regValue;

    private PatternEnum(Pattern regValue){
        this.regValue = regValue;
    }

    public Pattern getRegValue() {
        return regValue;
    }

    public void setRegValue(Pattern regValue) {
        this.regValue = regValue;
    }

}
