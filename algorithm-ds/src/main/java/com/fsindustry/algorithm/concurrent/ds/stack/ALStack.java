package com.fsindustry.algorithm.concurrent.ds.stack;

import java.util.ArrayList;

/**
 * <h1>基于ArrayList的栈的实现</h1>
 *
 * @author fuzhengxin
 */
public class ALStack<T> {

    private ArrayList<T> arr;

    public ALStack() {
        this.arr = new ArrayList<>();
    }

    public boolean isEmpty() {
        return arr.isEmpty();
    }

    public T peek() {
        return arr.get(arr.size() - 1);
    }

    public T pop() {
        return arr.remove(arr.size() - 1);
    }

    public void push(T item) {
        arr.add(item);
    }

    public int size() {
        return arr.size();
    }
}
