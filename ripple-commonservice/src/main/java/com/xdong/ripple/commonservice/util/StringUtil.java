package com.xdong.ripple.commonservice.util;

public class StringUtil extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';

    /**
     * 驼峰命名法工具
     * 
     * @return toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String name) {
        if (name == null) {
            return null;
        }

        name = name.toLowerCase();

        StringBuilder sb = new StringBuilder(name.length());
        boolean upperCase = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     * 
     * @return toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String name) {
        if (name == null) {
            return null;
        }
        String tempName = toCamelCase(name);
        return tempName.substring(0, 1).toUpperCase() + tempName.substring(1);
    }

    /**
     * 驼峰命名法工具
     * 
     * @return toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if (i > 0 && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 首字母大写
     * 
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }
}
