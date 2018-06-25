package com.fsindustry.algorithm.concurrent.search.binarysearch;

import java.util.Arrays;

public class BinarySearch {

    public static int search(int key, int[] sortedArr) {

        int highIdx = sortedArr.length - 1;
        int lowIdx = 0;

        while (highIdx >= lowIdx) {

            int midIdx = lowIdx + (highIdx - lowIdx) / 2;

            if (sortedArr[midIdx] == key) {
                return key;
            } else if (sortedArr[midIdx] > key) {
                lowIdx = midIdx;
            } else if (sortedArr[midIdx] < key) {
                highIdx = midIdx;
            }
        }

        return -1;
    }

    public static int search1(int[] arr, int key) {

        int high = arr.length - 1;
        int low = 0;

        while (low < high) {
            int mid = (low + high) / 2;

            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] > key) {
                high = mid;
            } else {
                low = mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 5, 4, 7, 9, 8, 0, 2, 1};
        Arrays.sort(arr);
        System.out.println(search1(arr, 3));
    }

}
