package com.fsindustry.algorithm.common.io;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.fsindustry.algorithm.common.annotation.NonThreadSafe;

/**
 * 从stdin读取字符串和数字
 */
@NonThreadSafe
public final class StdIn {

    private static final String BOOLEAN_INT_TRUE = "1";

    private static final String BOOLEAN_INT_FALSE = "0";

    private static final String BOOLEAN_STRING_TRUE = "true";

    private static final String BOOLEAN_STRING_FALSE = "false";

    private static final String CHARSET_NAME = "UTF-8";

    private static final Locale LOCALE = Locale.SIMPLIFIED_CHINESE;

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");

    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

    private static Scanner scanner;

    private StdIn() {
    }

    public static boolean isEmpty() {
        return !scanner.hasNext();
    }

    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public static boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public static String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    public static char readChar() {
        try {
            scanner.useDelimiter(EMPTY_PATTERN);
            String ch = scanner.next();
            assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
                    + " Please contact the authors.";
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'char' value from standard input, but there are no more tokens available");
        }
    }

    public static String readAll() {
        if (!scanner.hasNextLine()) {
            return "";
        }

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public static String readString() {
        try {
            return scanner.next();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'String' value from standard input, but there are no more tokens available");
        }
    }

    public static int readInt() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read an 'int' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attemps to read an 'int' value from standard input, but there are no more tokens available");
        }

    }

    public static double readDouble() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'double' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'double' value from standard input, but there are no more tokens available");
        }
    }

    public static float readFloat() {
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'float' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'float' value from standard input, but there are no more tokens available");
        }
    }

    public static long readLong() {
        try {
            return scanner.nextLong();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'long' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'long' value from standard input, but there are no more tokens available");
        }
    }

    public static short readShort() {
        try {
            return scanner.nextShort();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'short' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'short' value from standard input, but there are no more tokens available");
        }
    }

    public static byte readByte() {
        try {
            return scanner.nextByte();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException(
                    "attempts to read a 'byte' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'byte' value from standard input, but there are no more tokens available");
        }
    }

    public static boolean readBoolean() {
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
                    "attempts to read a 'boolean' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "attempts to read a 'boolean' value from standard input, but there are no more tokens available");
        }

    }

    public static String[] readAllStrings() {

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

    public static String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Integer.parseInt(fields[i]);
        }
        return vals;
    }

    public static long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Long.parseLong(fields[i]);
        }
        return vals;
    }

    public static double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Double.parseDouble(fields[i]);
        }
        return vals;
    }

    static {
        resync();
    }

    /**
     * 如果标准输出流改变，则重新初始化
     */
    private static void resync() {
        setScanner(new Scanner(new java.io.BufferedInputStream(System.in), CHARSET_NAME));
    }

    private static void setScanner(Scanner scanner) {
        StdIn.scanner = scanner;
        StdIn.scanner.useLocale(LOCALE);
    }
}