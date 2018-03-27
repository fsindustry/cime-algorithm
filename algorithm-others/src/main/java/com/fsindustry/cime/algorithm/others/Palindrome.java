package com.fsindustry.cime.algorithm.others;

/**
 * 回文
 *
 * @see ReverseArray
 */
public class Palindrome {

    public static boolean palindrome(String src) {

        if (null == src) {
            return false;
        }

        if ("".equals(src) || src.length() == 1) {
            return true;
        }

        int n = src.length();
        for (int i = 0; i < n / 2; i++) {

            char a = src.charAt(i);
            char b = src.charAt(n - 1 - i);

            if (a != b) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        System.out.println(palindrome(null));
        System.out.println(palindrome(""));
        System.out.println(palindrome("a"));
        System.out.println(palindrome("ab"));
        System.out.println(palindrome("aa"));
        System.out.println(palindrome("aaa"));
        System.out.println(palindrome("aba"));
        System.out.println(palindrome("abcba"));
        System.out.println(palindrome("abcdba"));
        System.out.println(palindrome("abccba"));
    }
}
