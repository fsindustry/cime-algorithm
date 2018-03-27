package com.fsindustry.cime.algorithm.others;

import java.util.Arrays;

/**
 * 数组翻转
 */
public class ReverseArray {

    public static void reverse(int[] arr) {

        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            int tmp = arr[n - 1 - i];
            arr[n - 1 - i] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        reverseArr(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void reverseArr(int[] arr) {

        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            int tmp = arr[i];
            arr[i] = arr[n - 1 - i];
            arr[n - 1 - i] = tmp;
        }
    }
}
