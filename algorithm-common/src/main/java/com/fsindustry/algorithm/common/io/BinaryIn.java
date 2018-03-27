package com.fsindustry.algorithm.common.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;

import com.fsindustry.algorithm.common.annotation.NonThreadSafe;

/**
 * 从stdin、文件、url、socket中读取二进制数据；
 * <p>
 * 默认使用大端模式读取，即读取顺序与阅读顺序一致；
 */
@NonThreadSafe
public final class BinaryIn {

    /**
     * 文件结束标识
     */
    private static final int EOF = -1;

    /**
     * 输入流
     */
    private BufferedInputStream in;

    /**
     * 缓存8个bit
     */
    private int buffer;

    /**
     * buffer中剩余未处理的bit数
     */
    private int n;

    /**
     * 从stdin输入
     */
    public BinaryIn() {
        in = new BufferedInputStream(System.in);
        fillBuffer();
    }

    /**
     * 从InputStream输入
     */
    public BinaryIn(InputStream is) {
        in = new BufferedInputStream(is);
        fillBuffer();
    }

    /**
     * 从socket输入
     */
    public BinaryIn(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException ioe) {
            System.err.println("Could not open " + socket);
        }
    }

    /**
     * 从URL输入
     */
    public BinaryIn(URL url) {
        try {
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException ioe) {
            System.err.println("Could not open " + url);
        }
    }

    /**
     * 从字符串地址解析输入
     *
     * @param name the name of the file or URL
     */
    public BinaryIn(String name) {

        try {
            // first try to read file from local file system
            File file = new File(name);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                in = new BufferedInputStream(fis);
                fillBuffer();
                return;
            }

            // next try for files included in jar
            URL url = getClass().getResource(name);

            // or URL from web
            if (url == null) {
                url = new URL(name);
            }

            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException ioe) {
            System.err.println("Could not open " + name);
        }
    }

    /**
     * 读取一个字节数据
     */
    private void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        } catch (IOException e) {
            System.err.println("EOF");
            buffer = EOF;
            n = -1;
        }
    }

    /**
     * 判断输入流是否可用
     */
    public boolean exists() {
        return in != null;
    }

    /**
     * 是否已读到末尾
     */
    public boolean isEmpty() {
        return buffer == EOF;
    }

    /**
     * 读取一个bit
     */
    public boolean readBoolean() {
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
     * 读取一个字符（1个字节）
     */
    public char readChar() {
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }

        // 一个char占2字节，低8位保持不变，高8位置0
        if (n == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        // combine last N bits of current buffer with first 8-N bits of new buffer
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
        // the above code doesn't quite work for the last character if N = 8
        // because buffer will be -1
    }

    /**
     * 读取r个bit，转换为char
     */
    public char readChar(int r) {
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
    public String readString() {
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
    public short readShort() {
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
    public int readInt() {
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
    public int readInt(int r) {
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
    public long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    /**
     * 读取64个bit作为double
     */
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * 读取32个bit作为float
     */
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    /**
     * 读取8个bit作为一个byte
     */
    public byte readByte() {
        char c = readChar();
        return (byte) (c & 0xff);
    }
}