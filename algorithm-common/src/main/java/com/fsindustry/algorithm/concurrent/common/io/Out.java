/******************************************************************************
 *  Compilation:  javac Out.java
 *  Execution:    java Out
 *  Dependencies: none
 *
 *  Writes data of various types to: stdout, file, or socket.
 *
 ******************************************************************************/

package com.fsindustry.algorithm.concurrent.common.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

import com.fsindustry.algorithm.concurrent.common.annotation.NonThreadSafe;

/**
 * 输出字符串和数字到stdout、文件、url、socket；
 */
@NonThreadSafe
public class Out {

    /**
     * 字符集编码
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 语言
     */
    private static final Locale LOCALE = Locale.SIMPLIFIED_CHINESE;

    private PrintWriter out;

    /**
     * OutputStream作为输出
     */
    public Out(OutputStream os) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
            out = new PrintWriter(osw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * stdout作为输出
     */
    public Out() {
        this(System.out);
    }

    /**
     * socket作为输出
     */
    public Out(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
            out = new PrintWriter(osw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件作为输出
     */
    public Out(String filename) {
        try {
            OutputStream os = new FileOutputStream(filename);
            OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
            out = new PrintWriter(osw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        out.close();
    }

    public void println() {
        out.println();
    }

    public void println(Object x) {
        out.println(x);
    }

    public void println(boolean x) {
        out.println(x);
    }

    public void println(char x) {
        out.println(x);
    }

    public void println(double x) {
        out.println(x);
    }

    public void println(float x) {
        out.println(x);
    }

    public void println(int x) {
        out.println(x);
    }

    public void println(long x) {
        out.println(x);
    }

    public void println(byte x) {
        out.println(x);
    }

    public void print() {
        out.flush();
    }

    public void print(Object x) {
        out.print(x);
        out.flush();
    }

    public void print(boolean x) {
        out.print(x);
        out.flush();
    }

    public void print(char x) {
        out.print(x);
        out.flush();
    }

    public void print(double x) {
        out.print(x);
        out.flush();
    }

    public void print(float x) {
        out.print(x);
        out.flush();
    }

    public void print(int x) {
        out.print(x);
        out.flush();
    }

    public void print(long x) {
        out.print(x);
        out.flush();
    }

    public void print(byte x) {
        out.print(x);
        out.flush();
    }

    public void printf(String format, Object... args) {
        out.printf(LOCALE, format, args);
        out.flush();
    }

    public void printf(Locale locale, String format, Object... args) {
        out.printf(locale, format, args);
        out.flush();
    }
}

