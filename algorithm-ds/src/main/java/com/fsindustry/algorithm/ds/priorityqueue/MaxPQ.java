package com.fsindustry.algorithm.ds.priorityqueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 优先队列-最大堆实现
 *
 * @author fuzhengxin
 */
public class MaxPQ<Key extends Comparable<Key>> implements Iterable<Key> {

    /**
     * 存放堆元素的数组
     */
    private Key[] arr;

    /**
     * 堆中元素的数量
     */
    private int size;

    /**
     * 比较器，设定元素比较规则
     */
    private Comparator<Key> comparator;

    /**
     * 上浮
     */
    private void swim(int k) {

        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }

    }

    /**
     * 下沉
     */
    private void sink(int k) {

        while (2 * k <= size) {

            int j = 2 * k;
            // 找出两个子节点中较大的节点
            if (j < size && less(j, j + 1)) {
                j++;
            }

            // 如果当前节点大等于子节点，则位置合适，跳出循环
            if (!less(k, j)) {
                break;
            }

            // 交换元素
            exch(k, j);
            // 更新元素位置，进入下一轮迭代
            k = j;
        }
    }

    /**
     * 比较
     */
    private boolean less(int i, int j) {

        // 若未指定比较器，则按照默认规则比较
        if (null == comparator) {
            return arr[i].compareTo(arr[j]) < 0;
        }

        // 若指定比较器，则使用比较器比较
        return comparator.compare(arr[i], arr[j]) < 0;
    }

    /**
     * 交换
     */
    private void exch(int i, int j) {
        Key tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 动态扩容
     */
    private void resize(int capacity) {
        assert capacity > size;

        // 创建新数组，拷贝原数组元素
        Key[] newArr = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= size; i++) {
            newArr[i] = arr[i];
        }

        // 更新数组引用
        arr = newArr;
    }

    public boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    private boolean isMaxHeap(int k) {

        if (k > size) {
            return true;
        }

        int left = 2 * k;
        int right = 2 * k + 1;

        if (left <= size && less(k, left)) {
            return false;
        }

        if (right <= size && less(k, right)) {
            return false;
        }

        // 递归判断
        return isMaxHeap(left) && isMaxHeap(right);
    }

    public MaxPQ() {
        this(1);
    }

    /**
     * 指定arr的初始容量
     */
    public MaxPQ(int initialCapacity) {
        // 索引从1开始，因而数组长度+1
        arr = (Key[]) new Comparable[initialCapacity + 1];
        size = 0;
    }

    /**
     * 指定容量和比较器
     */
    public MaxPQ(int initialCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        arr = (Key[]) new Comparable[initialCapacity + 1];
        size = 0;
    }

    /**
     * 指定比较器
     */
    public MaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    /**
     * 通过传入arr创建优先队列
     */
    public MaxPQ(Key[] arr) {

        size = arr.length;
        this.arr = (Key[]) new Comparable[arr.length + 1];
        for (int i = 0; i < size; i++) {
            this.arr[i + 1] = arr[i];
        }

        // 从倒数第二层开始sink，因为叶子节点不需要sink
        for (int k = size / 2; k >= 1; k--) {
            sink(k);
        }
    }

    /**
     * 插入元素
     */
    public void insert(Key value) {

        // 如果容量已满，则成倍扩容
        if (size == arr.length - 1) {
            resize(2 * arr.length);
        }

        // 在数组末尾存放新增元素，增大size
        arr[++size] = value;

        // 将元素上浮到合适的位置
        swim(size);
    }

    /**
     * 删除最大值
     */
    public Key delMax() {

        if (isEmpty()) {
            throw new NoSuchElementException("priority queue is empty");
        }

        // 获取最大值
        Key max = arr[1];
        // 将数组末尾元素换至根节点，同时减小size
        exch(1, size--);
        // 将根节点元素下沉到合适位置
        sink(1);
        // 清空引用，防止内存泄露
        arr[size + 1] = null;

        // 如果元素数量仅为数组长度的1/4，则数组缩容至原来的1/2；
        // 之所以缩容至原来的1/2而不是1/4，是因为索引0位置空缺，缩容1/4，可能少一个位置；
        if ((size > 0) && (size == (arr.length - 1) / 4)) {
            resize(arr.length / 2);
        }

        return max;
    }

    /**
     * 返回队列中的最大值
     */
    public Key max() {

        if (isEmpty()) {
            throw new NoSuchElementException("priority queue is empty");
        }

        return arr[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    @Override
    public void forEach(Consumer<? super Key> action) {
        // foreach语法默认调用迭代器接口
        for (Key key : this) {
            action.accept(key);
        }
    }

    @Override
    public Spliterator<Key> spliterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * 迭代器
     */
    private class HeapIterator implements Iterator<Key> {

        private MaxPQ<Key> copy;

        HeapIterator() {
            if (null == comparator) {
                copy = new MaxPQ<>(size());
            } else {
                copy = new MaxPQ<>(size(), comparator);
            }

            // 已经是堆有序，因而是线性时间效率；
            for (int i = 1; i <= size; i++) {
                copy.insert(arr[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Key next() {

            if (!hasNext()) {
                throw new NoSuchElementException("Priority Queue is empty.");
            }

            return copy.delMax();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
