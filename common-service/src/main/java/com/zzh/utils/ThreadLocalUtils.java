package com.zzh.utils;

public class ThreadLocalUtils {
    private static final ThreadLocal<Integer> tl = new ThreadLocal<>();
    private static final ThreadLocal<String> un = new ThreadLocal<>();

    public static void setCurrentId(Integer id) {
        tl.set(id);
    }

    public static Integer getCurrentId() {
        return tl.get();
    }

    public static void removeCurrentId() {
        tl.remove();
    }

    //用户名ThreadLocal对象
    public static void setUsername(String username) {
        un.set(username);
    }

    public static String getUsername() {
        return un.get();
    }

    public static void removeUsername() {
        un.remove();
    }
}
