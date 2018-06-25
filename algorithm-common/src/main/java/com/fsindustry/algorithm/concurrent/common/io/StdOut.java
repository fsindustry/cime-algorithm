package com.fsindustry.algorithm.concurrent.common.io;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import com.fsindustry.algorithm.concurrent.common.annotation.NonThreadSafe;

/**
 * 输出字符串和数字到stdout；
 */
@NonThreadSafe
public final class StdOut {

    /**
     * 字符集编码
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 语言
     */
    private static final Locale LOCALE = Locale.SIMPLIFIED_CHINESE;

    private static PrintWriter out;

    // 初始化输出流
    static {
        try {
            out = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }

    private StdOut() {
    }

    public static void close() {
        out.close();
    }

    public static void println() {
        out.println();
    }

    public static void println(Object x) {
        out.println(x);
    }

    public static void println(boolean x) {
        out.println(x);
    }

    public static void println(char x) {
        out.println(x);
    }

    public static void println(double x) {
        out.println(x);
    }

    public static void println(float x) {
        out.println(x);
    }

    public static void println(int x) {
        out.println(x);
    }

    public static void println(long x) {
        out.println(x);
    }

    public static void println(short x) {
        out.println(x);
    }

    public static void println(byte x) {
        out.println(x);
    }

    public static void print() {
        out.flush();
    }

    public static void print(Object x) {
        out.print(x);
        out.flush();
    }

    public static void print(boolean x) {
        out.print(x);
        out.flush();
    }

    public static void print(char x) {
        out.print(x);
        out.flush();
    }

    public static void print(double x) {
        out.print(x);
        out.flush();
    }

    public static void print(float x) {
        out.print(x);
        out.flush();
    }

    public static void print(int x) {
        out.print(x);
        out.flush();
    }

    public static void print(long x) {
        out.print(x);
        out.flush();
    }

    public static void print(short x) {
        out.print(x);
        out.flush();
    }

    public static void print(byte x) {
        out.print(x);
        out.flush();
    }

    public static void printf(String format, Object... args) {
        out.printf(LOCALE, format, args);
        out.flush();
    }

    public static void printf(Locale locale, String format, Object... args) {
        out.printf(locale, format, args);
        out.flush();
    }
}