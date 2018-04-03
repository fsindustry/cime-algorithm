package com.fsindustry.algorithm.ds.priorityqueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 优先队列：基于最小堆实现
 */
public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key> {

    private Key[] arr;

    private int size;

    private Comparator<Key> comparator;

    private boolean great(int i, int j) {

        if (null == comparator) {
            return arr[i].compareTo(arr[j]) > 0;
        }

        return comparator.compare(arr[i], arr[j]) > 0;
    }

    private void exch(int i, int j) {
        Key tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private void swim(int k) {

        while (k > 1) {
            if (!great(k / 2, k)) {
                break;
            }

            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {

        while (2 * k <= size) {

            // 获取最小子节点
            int j = 2 * k;
            if (j < size && great(j, j + 1)) {
                j++;
            }

            // k找到合适位置
            if (!great(k, j)) {
                break;
            }

            // 下沉较大的元素，继续下一轮查找
            exch(k, j);
            k = j;
        }
    }

    private void resize(int capacity) {

        Key[] newArr = (Key[]) new Comparable[capacity + 1];
        for (int i = 1; i <= size; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    public MinPQ() {
        this(0);
    }

    public MinPQ(int initialCapacity) {
        arr = (Key[]) new Comparable[initialCapacity + 1];
        size = 0;
    }

    public MinPQ(int initialCapacity, Comparator<Key> comparator) {
        arr = (Key[]) new Comparable[initialCapacity + 1];
        size = 0;
        this.comparator = comparator;
    }

    public MinPQ(Comparator<Key> comparator) {
        arr = (Key[]) new Comparable[1];
        size = 0;
        this.comparator = comparator;
    }

    public MinPQ(Key[] keys) {

        // 拷贝元素
        size = keys.length;
        arr = (Key[]) new Comparable[size + 1];
        for (int i = 0; i < keys.length; i++) {
            arr[i + 1] = keys[i];
        }

        // 下沉操作
        for (int k = size / 2; k >= 1; k--) {
            sink(k);
        }
    }

    public void insert(Key v) {

        // 扩容
        if (arr.length == size + 1) {
            resize(2 * arr.length);
        }

        arr[++size] = v;
        swim(size);
    }

    public Key delMin() {

        Key min = arr[1];
        exch(1, size--);
        sink(1);
        arr[size + 1] = null;

        // 缩容
        if (size > 0 && size == (arr.length - 1) / 4) {
            resize(arr.length / 2);
        }

        return min;
    }

    public Key min() {
        return arr[1];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public boolean isMinHeap() {
        return isMinHeap(1);
    }

    private boolean isMinHeap(int k) {

        if (k > size) {
            return true;
        }

        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= size && great(k, left)) {
            return false;
        }

        if ((right <= size) && great(k, right)) {
            return false;
        }

        return isMinHeap(left) && isMinHeap(right);
    }

    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    @Override
    public void forEach(Consumer<? super Key> action) {

        for (Key key : this) {
            action.accept(key);
        }
    }

    @Override
    public Spliterator<Key> spliterator() {
        throw new UnsupportedOperationException();
    }

    private class HeapIterator implements Iterator<Key> {

        private MinPQ<Key> copyPQ;

        HeapIterator() {

            if (null == comparator) {
                copyPQ = new MinPQ<>(size());
            } else {
                copyPQ = new MinPQ<>(size(), comparator);
            }

            for (int i = 1; i <= size(); i++) {
                copyPQ.insert(arr[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copyPQ.isEmpty();
        }

        @Override
        public Key next() {
            return copyPQ.delMin();
        }
    }
}
