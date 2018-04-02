package com.fsindustry.algorithm.ds.priorityqueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * MaxPQ测试
 */
public class MaxPQTest {

    private int[] testArr = {9, 7, 10, 8, 5, 2, 3, 4, 6, 1, 0};

    @Test
    public void insert() throws Exception {

        MaxPQ<Integer> maxPQ = new MaxPQ<>();
        for (int i : testArr) {
            maxPQ.insert(i);
        }

        Assert.assertTrue(maxPQ.isMaxHeap());

        for (int i : maxPQ) {
            System.out.println(i);
        }
    }

    @Test
    public void delMax() throws Exception {

        Integer[] arr = new Integer[testArr.length];
        for (int i = 0; i < testArr.length; i++) {
            arr[i] = testArr[i];
        }

        MaxPQ<Integer> maxPQ = new MaxPQ<>(arr);
        Assert.assertTrue(maxPQ.isMaxHeap());

        Assert.assertEquals(10, (int) maxPQ.delMax());
    }

    @Test
    public void max() throws Exception {

        MaxPQ<Integer> maxPQ = new MaxPQ<>(10);
        for (int i = testArr.length - 1; i >= 0; i--) {
            maxPQ.insert(testArr[i]);
        }
        Assert.assertTrue(maxPQ.isMaxHeap());

        Assert.assertEquals(10, (int) maxPQ.max());
    }

    @Test
    public void isEmpty() throws Exception {

        MaxPQ<Integer> maxPQ = new MaxPQ<>(10);
        Assert.assertTrue(maxPQ.isEmpty());

        maxPQ.insert(1);
        Assert.assertFalse(maxPQ.isEmpty());
    }

    @Test
    public void size() throws Exception {

        MaxPQ<Integer> maxPQ = new MaxPQ<>(5);
        for (int i = testArr.length - 1; i >= 0; i--) {
            maxPQ.insert(testArr[i]);
        }
        Assert.assertTrue(maxPQ.isMaxHeap());

        Assert.assertEquals(testArr.length, maxPQ.size());
    }

    @Test
    public void iterator() throws Exception {

        MaxPQ<Integer> maxPQ = new MaxPQ<>();
        for (int i = testArr.length - 1; i >= 0; i--) {
            maxPQ.insert(testArr[i]);
        }
        Assert.assertTrue(maxPQ.isMaxHeap());

        int i = 10;
        Iterator<Integer> iterator = maxPQ.iterator();
        while (iterator.hasNext()) {
            Assert.assertEquals(i--, (int) iterator.next());
        }
    }

    @Test
    public void forEach() throws Exception {

        MaxPQ<Integer> maxPQ = new MaxPQ<>();
        for (int i = testArr.length - 1; i >= 0; i--) {
            maxPQ.insert(testArr[i]);
        }
        Assert.assertTrue(maxPQ.isMaxHeap());

        List<Integer> list = new ArrayList<>();
        maxPQ.forEach(list::add);

        Assert.assertEquals(0, (int) list.get(testArr.length - 1));
        Assert.assertEquals(10, (int) list.get(0));
    }
}