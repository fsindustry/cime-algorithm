package com.fsindustry.algorithm.sort;

import java.util.Arrays;

public class QuickSort {

    public static void sort(int[] arr, int low, int high) {

        if (low >= high) {
            return;
        }

        int idx = partition(arr, low, high);
        sort(arr, low, idx - 1);
        sort(arr, idx + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {

        int key = arr[low];

        while (low < high) {

            while (arr[high] >= key && low < high) {
                high--;
            }
            arr[low] = arr[high];

            while (arr[low] <= key && low < high) {
                low++;
            }
            arr[high] = arr[low];
        }

        arr[high] = key;

        return high;
    }

    public static void main(String[] args) {

        int[] arr = {3, 6, 5, 4, 7, 9, 8, 0, 2, 1};
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
