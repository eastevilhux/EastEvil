package com.good.framework.utils;

import android.util.Log;

public class LogUtil {
    public static boolean debug;

    public static void e (String tag, String msg){
        if (isDebug()) {
            Log.e(tag,msg);
        }
    }

    public static void i (String tag, String msg){
        if (isDebug()) {
            Log.i(tag,msg);
        }
    }


    public static void d (String tag, String msg){
        if (isDebug()) {
            Log.d(tag,msg);
        }
    }

    public static void w (String tag, String msg){
        if (isDebug()) {
            Log.w(tag,msg);
        }
    }


    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        LogUtil.debug = debug;
    }
}
