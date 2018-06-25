package com.fsindustry.algorithm.concurrent.sort.heapsort;

import java.util.Arrays;

/**
 * 堆排序实现：基于最大堆实现
 */
public class HeatSort {

    /**
     * 比较
     */
    private static boolean less(Comparable[] arr, int i, int j) {
        return arr[i - 1].compareTo(arr[j - 1]) < 0;
    }

    /**
     * 交换
     */
    private static void exch(Comparable[] arr, int i, int j) {
        Comparable tmp = arr[i - 1];
        arr[i - 1] = arr[j - 1];
        arr[j - 1] = tmp;
    }

    /**
     * 下沉
     */
    private static void sink(Comparable[] arr, int k, int size) {

        while (2 * k <= size) {

            // 获取较大子节点的索引
            int j = 2 * k;
            if (j < size && less(arr, j, j + 1)) {
                j++;
            }

            // 如果当前节点大于子节点，则找到合适位置
            if (!less(arr, k, j)) {
                break;
            }

            // 如果当前节点小于子节点，则继续交换下沉
            exch(arr, k, j);
            k = j;
        }
    }

    /**
     * 排序
     */
    public static void sort(Comparable[] arr) {

        int size = arr.length;

        // 构造堆
        for (int k = size / 2; k >= 1; k--) {
            sink(arr, k, size);
        }

        // 下沉排序
        while (size > 1) {
            exch(arr, 1, size--);
            sink(arr, 1, size);
        }
    }

    public static void main(String[] args) {

        Integer[] arr = {3, 6, 5, 4, 7, 9, 8, 0, 2, 1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
