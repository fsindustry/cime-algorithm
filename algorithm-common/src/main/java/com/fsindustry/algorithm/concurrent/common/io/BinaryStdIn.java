
package com.fsindustry.algorithm.concurrent.common.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

import com.fsindustry.algorithm.concurrent.common.annotation.NonThreadSafe;

/**
 * 从stdin中读取二进制数据；
 * <p>
 * 默认使用大端模式读取，即读取顺序与阅读顺序一致；
 */
@NonThreadSafe
public final class BinaryStdIn {

    /**
     * 输入流
     */
    private static BufferedInputStream in = new BufferedInputStream(System.in);

    /**
     * 文件结束标识
     */
    private static final int EOF = -1;

    /**
     * 缓存8个bit
     */
    private static int buffer;

    /**
     * buffer中剩余未处理的bit数
     */
    private static int n;

    /**
     * 预先读取1个字节
     */
    static {
        fillBuffer();
    }

    private BinaryStdIn() {
    }

    private static void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        } catch (IOException e) {
            System.out.println("EOF");
            buffer = EOF;
            n = -1;
        }
    }

    /**
     * 关闭输入流
     */
    public static void close() {
        try {
            in.close();
        } catch (IOException ioe) {
            throw new IllegalStateException("Could not close BinaryStdIn", ioe);
        }
    }

    /**
     * 判断是否已经读完数据
     */
    public static boolean isEmpty() {
        return buffer == EOF;
    }

    /**
     * 读取1个bit
     */
    public static boolean readBoolean() {
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        n--;
        boolean bit = ((buffer >> n) & 1) == 1;
        if (n == 0) {
            fillBuffer();
        }
        return bit;
    }

    /**
     * 读取一个8bit的char
     */
    public static char readChar() {
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }

        // special case when aligned byte
        if (n == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        // combine last n bits of current buffer with first 8-n bits of new buffer
        int x = buffer;
        x <<= (8 - n);
        int oldN = n;
        fillBuffer();
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        n = oldN;
        x |= (buffer >>> n);
        return (char) (x & 0xff);
        // the above code doesn't quite work for the last character if n = 8
        // because buffer will be -1, so there is a special case for aligned byte
    }

    /**
     * 读取r个bit作为一个char
     */
    public static char readChar(int r) {
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value of r = " + r);
        }

        // optimize r = 8 case
        if (r == 8) {
            return readChar();
        }

        char x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) {
                x |= 1;
            }
        }
        return x;
    }

    /**
     * 读取一个字符串
     */
    public static String readString() {
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 读取16个bit作为一个short
     */
    public static short readShort() {
        short x = 0;
        for (int i = 0; i < 2; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * 读取32个bit作为一个int
     */
    public static int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * 读取r个bit作为一个int
     */
    public static int readInt(int r) {
        if (r < 1 || r > 32) {
            throw new IllegalArgumentException("Illegal value of r = " + r);
        }

        // optimize r = 32 case
        if (r == 32) {
            return readInt();
        }

        int x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) {
                x |= 1;
            }
        }
        return x;
    }

    /**
     * 读取64个bit作为一个long
     */
    public static long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * 读取64个bit作为一个double
     */
    public static double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * 读取32个bit作为一个float
     */
    public static float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    /**
     * 读取8个bit作为一个byte
     */
    public static byte readByte() {
        char c = readChar();
        return (byte) (c & 0xff);
    }
}
