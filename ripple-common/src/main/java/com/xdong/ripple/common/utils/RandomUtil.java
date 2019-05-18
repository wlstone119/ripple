package com.xdong.ripple.common.utils;

public class RandomUtil {

    public long randomTime() {
        return (long) (Math.random() * 3 * 10000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(new RandomUtil().randomTime());
        }
    }

}
