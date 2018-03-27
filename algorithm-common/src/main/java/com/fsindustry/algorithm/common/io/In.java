package com.fsindustry.algorithm.common.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.fsindustry.algorithm.common.annotation.NonThreadSafe;

/**
 * 从stdin、文件、url、socket中读取字符串和数字；
 */
@NonThreadSafe
public final class In {

    private static final String BOOLEAN_INT_TRUE = "1";

    private static final String BOOLEAN_INT_FALSE = "0";

    private static final String BOOLEAN_STRING_TRUE = "true";

    private static final String BOOLEAN_STRING_FALSE = "false";

    /**
     * 字符集编码
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 语言
     */
    private static final Locale LOCALE = Locale.SIMPLIFIED_CHINESE;

    /**
     * 空白字符分隔符：用于逐个读取字符串
     */
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");

    /**
     * 单个字符分隔符：用于逐字符读取字符串
     */
    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    /**
     * 用于读取全部字符
     */
    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

    private Scanner scanner;

    /**
     * stdin输入
     */
    public In() {
        scanner = new Scanner(new BufferedInputStream(System.in), CHARSET_NAME);
        scanner.useLocale(LOCALE);
    }

    /**
     * socket输入
     *
     * @param socket socket套接字
     */
    public In(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket argument is null");
        }
        try {
            InputStream is = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + socket, ioe);
        }
    }

    /**
     * url输入
     *
     * @param url url地址
     */
    public In(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url argument is null");
        }
        try {
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + url, ioe);
        }
    }

    /**
     * 文件输入
     *
     * @param file 文件对象
     */
    public In(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file argument is null");
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + file, ioe);
        }
    }

    /**
     * 通过字符串地址获取输入
     *
     * @param name 字符串地址，可以是文件名、url地址等
     */
    public In(String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            // 1.尝试以文件方式解析
            File file = new File(name);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }

            // 2.尝试在当前jar包中寻找资源
            URL url = getClass().getResource(name);

            // 3.尝试在类路径下查找资源
            if (url == null) {
                url = getClass().getClassLoader().getResource(name);
            }

            // 4.网络资源的URL
            if (url == null) {
                url = new URL(name);
            }

            URLConnection site = url.openConnection();

            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + name, ioe);
        }
    }

    /**
     * 从scanner输入
     */
    public In(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner argument is null");
        }
        this.scanner = scanner;
    }

    /**
     * true，输入流存在；false，输入流不存在
     */
    public boolean exists() {
        return scanner != null;
    }

    /**
     * true，没有内容读取；false，有内容读取；
     */
    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    /**
     * true，有可读取的行；包括空白字符；
     * 配合{@link #readLine()}使用；
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * true，有可读取的字符；包括空白字符；
     * 配合{@link #readChar()}使用；
     */
    public boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    /**
     * 读取一行字符串
     *
     * @return 如果未读取到 or 异常，返回null;
     */
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    /**
     * 读取一个字符
     */
    public char readChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        try {
            String ch = scanner.next();
            assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
                    + " Please contact the authors.";
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'char' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取所有数据到一个字符串中
     */
    public String readAll() {
        if (!scanner.hasNextLine()) {
            return "";
        }

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    /**
     * 读取一个字符串
     */
    public String readString() {
        try {
            return scanner.next();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'String' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取一个int
     */
    public int readInt() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read an 'int' value from input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attemps to read an 'int' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取一个double
     */
    public double readDouble() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'double' value from input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attemps to read a 'double' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取一个float
     */
    public float readFloat() {
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'float' value from input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attemps to read a 'float' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取一个long
     */
    public long readLong() {
        try {
            return scanner.nextLong();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'long' value from input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attemps to read a 'long' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取一个short
     */
    public short readShort() {
        try {
            return scanner.nextShort();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'short' value from input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attemps to read a 'short' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取一个byte
     */
    public byte readByte() {
        try {
            return scanner.nextByte();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'byte' value from input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attemps to read a 'byte' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取一个boolean
     * <p>
     * true："true" or 1
     * false: "false" or 0
     */
    public boolean readBoolean() {
        try {
            String token = readString();
            if (BOOLEAN_STRING_TRUE.equalsIgnoreCase(token)) {
                return true;
            }
            if (BOOLEAN_STRING_FALSE.equalsIgnoreCase(token)) {
                return false;
            }
            if (BOOLEAN_INT_TRUE.equals(token)) {
                return true;
            }
            if (BOOLEAN_INT_FALSE.equals(token)) {
                return false;
            }
            throw new InputMismatchException(
                    "attempts to read a 'boolean' value from input stream, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'boolean' value from input stream, but there are no more tokens available");
        }
    }

    /**
     * 读取所有的字符串分割存放到数组中
     */
    public String[] readAllStrings() {

        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0) {
            return tokens;
        }
        String[] decapitokens = new String[tokens.length - 1];
        for (int i = 0; i < tokens.length - 1; i++) {
            decapitokens[i] = tokens[i + 1];
        }
        return decapitokens;
    }

    /**
     * 按行读取所有字符串
     */
    public String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * 读取所有的int
     */
    public int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Integer.parseInt(fields[i]);
        }
        return vals;
    }

    /**
     * 读取所有的long
     */
    public long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Long.parseLong(fields[i]);
        }
        return vals;
    }

    /**
     * 读取所有的double
     */
    public double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Double.parseDouble(fields[i]);
        }
        return vals;
    }

    /**
     * 关闭输入流
     */
    public void close() {
        scanner.close();
    }
}
