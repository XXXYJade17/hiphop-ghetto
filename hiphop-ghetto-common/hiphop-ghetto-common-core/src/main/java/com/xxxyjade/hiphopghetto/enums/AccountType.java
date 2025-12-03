package com.xxxyjade.hiphopghetto.enums;

import java.util.regex.Pattern;

public enum AccountType {
    USERNAME,   // 用户名
    PHONE,      // 手机号
    EMAIL;       // 邮箱

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static AccountType getAccountType(String account) {
        // 是否为手机号
        if (PHONE_PATTERN.matcher(account.trim()).matches()) {
            return AccountType.PHONE;
        }
        // 是否为邮箱
        if (EMAIL_PATTERN.matcher(account.trim()).matches()) {
            return AccountType.EMAIL;
        }
        // 其余为用户名
        return AccountType.USERNAME;
    }

}
