package com.xxxyjade.hiphopghetto.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int WORK_FACTOR = 11;

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(WORK_FACTOR));
    }

    public static Boolean verify(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }

}
