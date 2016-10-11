package io.havoc.todo.util;

import android.util.Log;

import java.util.Locale;

/**
 * Helpful logging utility class
 * Stolen from the beautiful and brilliant dev, ccrama (Slide for Reddit)
 * Source: https://goo.gl/1TvAfc
 * <p>
 * The comments for the methods were taken from the below Stack Overflow answer:
 * http://stackoverflow.com/a/7959379/2094116
 */
public class LogUtil {
    private static final int CALLING_METHOD_INDEX;

    /**
     * Get the stacktrace index of the method that called this class
     * Variation of http://stackoverflow.com/a/8592871/4026792
     */
    static {
        int i = 1;
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            i++;
            if (ste.getClassName().equals(LogUtil.class.getName())) {
                break;
            }
        }
        CALLING_METHOD_INDEX = i;
    }

    /**
     * Source: http://stackoverflow.com/a/24586896/4026792
     *
     * @return Log tag in format (CLASSNAME.java:LINENUMBER); which makes it clickable in logcat
     */
    private static String getTag() {
        try {
            final StackTraceElement ste = Thread.currentThread().getStackTrace()[CALLING_METHOD_INDEX];
            return String.format(Locale.ENGLISH, "(%s:%d)", ste.getFileName(), ste.getLineNumber());
        } catch (Exception e) {
            return "Somewhere in Havoc...";
        }
    }

    /**
     * Verbose logging
     * Use this when you want to go nuts with logging
     *
     * @param message to output
     */
    public static void v(String message) {
        Log.v(getTag(), message);
    }

    /**
     * Debug logging
     * Use this when you want to log the flow of the app and for logging variables
     *
     * @param message to output
     */
    public static void d(String message) {
        Log.d(getTag(), message);
    }

    /**
     * Information logging
     * Use this when you want to report successes (basically)
     *
     * @param message to output
     */
    public static void i(String message) {
        Log.i(getTag(), message);
    }

    /**
     * Weird logging
     * Use this when you want to log stuff you didn't expect to happen, but isn't necessarily an error
     * (basically a "Hey, this happened, and it's weird--we should look at this)
     *
     * @param message to output
     */
    public static void w(String message) {
        Log.w(getTag(), message);
    }

    /**
     * Error logging
     * Use this when you want to log when bad stuff happens, you know the error has occurred,
     * and therefore you're logging it (use in places like a catch statement)
     *
     * @param message to output
     */
    public static void e(String message) {
        Log.e(getTag(), message);
    }

    /**
     * WTF (What a terrible failure) logging
     * Use this when you want to log when something goes absolutely, horribly, "holy-crap" wrong
     * (use in places where you're catching errors that should never happen)
     *
     * @param message to output
     */
    public static void wtf(String message) {
        Log.wtf(getTag(), message);
    }
}