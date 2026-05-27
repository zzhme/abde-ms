package com.zzh.utils;

public class ThreadLocalUtils {
    private static final ThreadLocal<Integer> tl = new ThreadLocal<>();

    public static void setCurrentId(Integer id) {
        tl.set(id);
    }

    public static Integer getCurrentId() {
        return tl.get();
    }

    public static void removeCurrentId() {
        tl.remove();
    }
}
