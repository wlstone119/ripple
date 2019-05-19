package com.xdong.ripple.common.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class RandomUtil {

    public long randomTime(String url) {
        String host = null;
        try {
            URL urlStr = new URL(url);
            host = urlStr.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (host.contains("music.baidu.com") || host.contains("music.taihe.com")) {
            return (long) (Math.random() * 3 * 1000);
        } else {
            return (long) (Math.random() * 3 * 0);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(new RandomUtil().randomTime("http://music.wangyi.com"));
        }
    }

}
