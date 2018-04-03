package com.fsindustry.algorithm.ds.priorityqueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * MinPQ测试
 */
public class MinPQTest {

    private int[] testArr = {9, 7, 10, 8, 5, 2, 3, 4, 6, 1, 0};

    @Test
    public void insert() throws Exception {

        MinPQ<Integer> minPQ = new MinPQ<>();
        for (int i : testArr) {
            minPQ.insert(i);
        }

        Assert.assertTrue(minPQ.isMinHeap());

        for (int i : minPQ) {
            System.out.println(i);
        }
    }

    @Test
    public void delMin() throws Exception {

        Integer[] arr = new Integer[testArr.length];
        for (int i = 0; i < testArr.length; i++) {
            arr[i] = testArr[i];
        }

        MinPQ<Integer> minPQ = new MinPQ<>(arr);
        Assert.assertTrue(minPQ.isMinHeap());

        Assert.assertEquals(0, (int) minPQ.delMin());
    }

    @Test
    public void min() throws Exception {

        MinPQ<Integer> minPQ = new MinPQ<>(10);
        for (int i = testArr.length - 1; i >= 0; i--) {
            minPQ.insert(testArr[i]);
        }
        Assert.assertTrue(minPQ.isMinHeap());

        Assert.assertEquals(0, (int) minPQ.min());
    }

    @Test
    public void isEmpty() throws Exception {

        MinPQ<Integer> minPQ = new MinPQ<>(10);
        Assert.assertTrue(minPQ.isEmpty());

        minPQ.insert(1);
        Assert.assertFalse(minPQ.isEmpty());
    }

    @Test
    public void size() throws Exception {

        MinPQ<Integer> minPQ = new MinPQ<>(5);
        for (int i = testArr.length - 1; i >= 0; i--) {
            minPQ.insert(testArr[i]);
        }
        Assert.assertTrue(minPQ.isMinHeap());

        Assert.assertEquals(testArr.length, minPQ.size());
    }

    @Test
    public void iterator() throws Exception {

        MinPQ<Integer> minPQ = new MinPQ<>();
        for (int i = testArr.length - 1; i >= 0; i--) {
            minPQ.insert(testArr[i]);
        }
        Assert.assertTrue(minPQ.isMinHeap());

        int i = 0;
        Iterator<Integer> iterator = minPQ.iterator();
        while (iterator.hasNext()) {
            Assert.assertEquals(i++, (int) iterator.next());
        }
    }

    @Test
    public void forEach() throws Exception {

        MinPQ<Integer> minPQ = new MinPQ<>();
        for (int i = testArr.length - 1; i >= 0; i--) {
            minPQ.insert(testArr[i]);
        }
        Assert.assertTrue(minPQ.isMinHeap());

        List<Integer> list = new ArrayList<>();
        minPQ.forEach(list::add);

        Assert.assertEquals(10, (int) list.get(testArr.length - 1));
        Assert.assertEquals(0, (int) list.get(0));
    }
}