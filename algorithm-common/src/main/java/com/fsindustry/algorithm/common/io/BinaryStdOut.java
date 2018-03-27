package com.fsindustry.algorithm.common.io;

import java.io.BufferedOutputStream;
import java.io.IOException;

import com.fsindustry.algorithm.common.annotation.NonThreadSafe;

/**
 * 输出二进制数据到stdout；
 * <p>
 * 默认使用大端模式输出，即输出顺序与阅读顺序一致；；
 */
@NonThreadSafe
public final class BinaryStdOut {

    /**
     * 输出流
     */
    private static BufferedOutputStream out = new BufferedOutputStream(System.out);

    /**
     * 缓存8个bit
     */
    private static int buffer;

    /**
     * buffer中剩余未处理的bit数
     */
    private static int n;

    private BinaryStdOut() {
    }

    /**
     * 输出一个bit
     */
    private static void writeBit(boolean bit) {
        // add bit to buffer
        buffer <<= 1;
        if (bit) {
            buffer |= 1;
        }

        // if buffer is full (8 bits), write out as a single byte
        n++;
        if (n == 8) {
            clearBuffer();
        }
    }

    /**
     * 输出8个bit
     */
    private static void writeByte(int x) {
        assert x >= 0 && x < 256;

        // optimized if byte-aligned
        if (n == 0) {
            try {
                out.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // otherwise write one bit at a time
        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    // write out any remaining bits in buffer to standard output, padding with 0s
    private static void clearBuffer() {
        if (n == 0) {
            return;
        }
        if (n > 0) {
            buffer <<= (8 - n);
        }
        try {
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        buffer = 0;
    }

    /**
     * Flushes standard output, padding 0s if number of bits written so far
     * is not a multiple of 8.
     */
    public static void flush() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Flushes and closes standard output. Once standard output is closed, you can no
     * longer write bits to it.
     */
    public static void close() {
        flush();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出一个bit
     */
    public static void write(boolean x) {
        writeBit(x);
    }

    /**
     * 输出8个bit作为一个byte
     */
    public static void write(byte x) {
        writeByte(x & 0xff);
    }

    /**
     * 输出32个bit作为一个int
     */
    public static void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    /**
     * 输出x的低r位作为一个int
     */
    public static void write(int x, int r) {
        if (r == 32) {
            write(x);
            return;
        }
        if (r < 1 || r > 32) {
            throw new IllegalArgumentException("Illegal value for r = " + r);
        }
        if (x < 0 || x >= (1 << r)) {
            throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        }
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    /**
     * 输出64个bit作为一个double
     */
    public static void write(double x) {
        write(Double.doubleToRawLongBits(x));
    }

    /**
     * 输出64个bit作为一个long
     */
    public static void write(long x) {
        writeByte((int) ((x >>> 56) & 0xff));
        writeByte((int) ((x >>> 48) & 0xff));
        writeByte((int) ((x >>> 40) & 0xff));
        writeByte((int) ((x >>> 32) & 0xff));
        writeByte((int) ((x >>> 24) & 0xff));
        writeByte((int) ((x >>> 16) & 0xff));
        writeByte((int) ((x >>> 8) & 0xff));
        writeByte((int) ((x >>> 0) & 0xff));
    }

    /**
     * 输出32个bit作为一个float
     */
    public static void write(float x) {
        write(Float.floatToRawIntBits(x));
    }

    /**
     * 输出16个bit作为一个short
     */
    public static void write(short x) {
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    /**
     * 输出8个bit作为一个byte
     */
    public static void write(char x) {
        if (x < 0 || x >= 256) {
            throw new IllegalArgumentException("Illegal 8-bit char = " + x);
        }
        writeByte(x);
    }

    /**
     * 输出x的低r位
     */
    public static void write(char x, int r) {
        if (r == 8) {
            write(x);
            return;
        }
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value for r = " + r);
        }
        if (x >= (1 << r)) {
            throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        }
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    /**
     * 输出s的每个char的低8位
     */
    public static void write(String s) {
        for (int i = 0; i < s.length(); i++) {
            write(s.charAt(i));
        }
    }

    /**
     * 输出s的每个char的低r位
     */
    public static void write(String s, int r) {
        for (int i = 0; i < s.length(); i++) {
            write(s.charAt(i), r);
        }
    }

}