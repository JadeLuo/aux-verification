package org.bupt.www.utils;

import java.util.regex.Pattern;

public class Validator {

    private static final String MOBILE_REGEX = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    private static final String EMAIL_REGEX = "^([\\w-_]+(?:\\.[\\w-_]+)*)@((?:[a-z0-9]+(?:-[a-zA-Z0-9]+)*)+\\.[a-z]{2,6})$";

    /**
     * 验证是否为空
     *
     * @param input
     * @return
     */
    public static boolean checkEmpty(String input) {
        return checkNull(input) || input.trim().equals("");
    }

    /**
     * 验证是否为null
     *
     * @param input
     * @return
     */
    public static boolean checkNull(String input) {
        return input == null;
    }

    public static boolean checkMobile(String mobile) {
        return Pattern.matches(MOBILE_REGEX, mobile);
    }

    public static boolean checkEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

}
