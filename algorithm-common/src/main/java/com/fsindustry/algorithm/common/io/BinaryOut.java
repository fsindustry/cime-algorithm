package com.fsindustry.algorithm.common.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.fsindustry.algorithm.common.annotation.NonThreadSafe;

/**
 * 输出二进制数据到stdout、文件、url、socket；
 * <p>
 * 默认使用大端模式输出，即输出顺序与阅读顺序一致；；
 */
@NonThreadSafe
public final class BinaryOut {

    /**
     * 输出流
     */
    private BufferedOutputStream out;

    /**
     * 缓存8个bit
     */
    private int buffer;

    /**
     * buffer中剩余未处理的bit数
     */
    private int n;

    /**
     * 输出到stdout
     */
    public BinaryOut() {
        out = new BufferedOutputStream(System.out);
    }

    /**
     * 输出到OutputStream
     */
    public BinaryOut(OutputStream os) {
        out = new BufferedOutputStream(os);
    }

    /**
     * 输出到指定文件
     */
    public BinaryOut(String filename) {
        try {
            OutputStream os = new FileOutputStream(filename);
            out = new BufferedOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出到socket
     */
    public BinaryOut(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            out = new BufferedOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出一个bit
     */
    private void writeBit(boolean x) {
        // add bit to buffer
        buffer <<= 1;
        if (x) {
            buffer |= 1;
        }

        // if buffer is full (8 bits), write out as a single byte
        n++;
        if (n == 8) {
            clearBuffer();
        }
    }

    /**
     * 输出一个字节
     *
     * @param x the byte
     */
    private void writeByte(int x) {
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

    // write out any remaining bits in buffer to the binary output stream, padding with 0s
    private void clearBuffer() {
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
     * Flushes the binary output stream, padding 0s if number of bits written so far
     * is not a multiple of 8.
     */
    public void flush() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Flushes and closes the binary output stream.
     * Once it is closed, bits can no longer be written.
     */
    public void close() {
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
    public void write(boolean x) {
        writeBit(x);
    }

    /**
     * 输出一个字节
     */
    public void write(byte x) {
        writeByte(x & 0xff);
    }

    /**
     * 输出一个int
     */
    public void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    /**
     * 输出x中的低r位
     */
    public void write(int x, int r) {
        if (r == 32) {
            write(x);
            return;
        }
        if (r < 1 || r > 32) {
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
     * 输出一个double
     */
    public void write(double x) {
        write(Double.doubleToRawLongBits(x));
    }

    /**
     * 输出一个long
     */
    public void write(long x) {
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
     * 输出一个float
     */
    public void write(float x) {
        write(Float.floatToRawIntBits(x));
    }

    /**
     * 输出一个short
     */
    public void write(short x) {
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    /**
     * 输出一个8bit的char
     */
    public void write(char x) {
        if (x < 0 || x >= 256) {
            throw new IllegalArgumentException("Illegal 8-bit char = " + x);
        }
        writeByte(x);
    }

    /**
     * 输出x的低r位
     */
    public void write(char x, int r) {
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
    public void write(String s) {
        for (int i = 0; i < s.length(); i++) {
            write(s.charAt(i));
        }
    }

    /**
     * 输出s中每个char的低r位
     */
    public void write(String s, int r) {
        for (int i = 0; i < s.length(); i++) {
            write(s.charAt(i), r);
        }
    }
}
