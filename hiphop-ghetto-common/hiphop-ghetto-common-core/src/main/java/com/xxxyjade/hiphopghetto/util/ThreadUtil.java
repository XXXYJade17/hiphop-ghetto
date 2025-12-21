package com.xxxyjade.hiphopghetto.util;

public class ThreadUtil {

    public static ThreadLocal<Long> idThreadLocal = new ThreadLocal<>();
//    public static ThreadLocal<String> usernameThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<String> nicknameThreadLocal = new ThreadLocal<>();

    public static void setUserId(Long id) {
        idThreadLocal.set(id);
    }

    public static Long getUserId() {
        return idThreadLocal.get();
    }

    public static void removeUserId() {
        idThreadLocal.remove();
    }

//    public static void setUsername(String username) {
//        usernameThreadLocal.set(username);
//    }
//
//    public static String getUsername() {
//        return usernameThreadLocal.get();
//    }
//
//    public static void removeUsername() {
//        usernameThreadLocal.remove();
//    }

    public static void setNickname(String nickname) {
        nicknameThreadLocal.set(nickname);
    }

    public static String getNickname() {
        return nicknameThreadLocal.get();
    }

    public static void removeNickname() {
        nicknameThreadLocal.remove();
    }

}
