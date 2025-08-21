package org.lunapark.anteres.network;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class Log {
    private static final String TAG = "ANTARES";
    private static boolean enable = true;
    private static final int CHUNK = 2048;

    public static void i(String format, Object... args) {
        i(new Formatter().format(format, args).toString());
    }

    public static void i(String msg) {
        if (enable) {
            String[] arr = splitToNChar(msg);
            for (String anArr : arr) {
                android.util.Log.i(TAG, String.format("%s: %s", getLocation(), anArr));
            }
        }
    }

    public static void d(String format, Object... args) {
        d(new Formatter().format(format, args).toString());
    }

    public static void d(String msg) {
        if (enable) {
            String[] arr = splitToNChar(msg);
            for (String anArr : arr) {
                android.util.Log.d(TAG, String.format("%s: %s", getLocation(), anArr));
            }
        }
    }

    public static void e(String format, Object... args) {
        e(new Formatter().format(format, args).toString());
    }

    public static void e(Exception ex) {
        e(ex.toString());
    }

    public static void e(String msg) {
        if (enable) {
            String[] arr = splitToNChar(msg);
            for (String anArr : arr) {
                android.util.Log.e(TAG, String.format("%s: %s", getLocation(), anArr));
            }
        }
    }

    public static void e(Throwable throwable) {
        android.util.Log.e(TAG, "Error",throwable);
    }

    public static void w(String format, Object... args) {
        w(new Formatter().format(format, args).toString());
    }

    public static void w(String msg) {
        if (enable) {
            String[] arr = splitToNChar(msg);
            for (String anArr : arr) {
                android.util.Log.w(TAG, String.format("%s: %s", getLocation(), anArr));
            }
        }
    }

    private static String[] splitToNChar(String text) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += Log.CHUNK) {
            parts.add(text.substring(i, Math.min(length, i + Log.CHUNK)));
        }
        return parts.toArray(new String[0]);
    }

    private static String getLocation() {
        final String className = Log.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;

        for (StackTraceElement trace : traces) {
            try {
                if (found) {
                    if (!trace.getClassName().startsWith(className)) {
                        Class<?> clazz = Class.forName(trace.getClassName());
                        return "(" + getClassName(clazz) + ".java:"
                                + trace.getLineNumber() + ")"
//                                + "[Method: " + trace.getMethodName() + "]"
                                ;
                    }
                } else if (trace.getClassName().startsWith(className)) {
                    found = true;
                }
            } catch (ClassNotFoundException e) {
                if (e.getMessage() != null) android.util.Log.e("Logger", e.getMessage());
            }
        }

        return "[]: ";
    }

    private static String getClassName(Class<?> clazz) {
        if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.getSimpleName())) {
                return clazz.getSimpleName();
            }
            return getClassName(clazz.getEnclosingClass());
        }
        return "";
    }

    public static boolean isEnable() {
        return enable;
    }

    public static void setEnable(boolean enable) {
        Log.enable = enable;
    }
}