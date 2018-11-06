package com.hua.miaosha.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern mobilePattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src) {

        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher m = mobilePattern.matcher(src);
        boolean b = m.matches();
        return b;
    };


    public static void main(String[] args) {

        System.out.println(isMobile("18682017404"));
        System.out.println(isMobile("17682017404"));
        System.out.println(isMobile("1768201740"));

    }
}
