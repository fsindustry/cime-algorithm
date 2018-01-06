package com.fsindustry.algorithm.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 考题：使用3个线程，分别依次输出A->B->C，循环往复
 */
public class AwaitSingal implements Runnable {

    private ReentrantLock prevLock;

    private ReentrantLock currLock;

    private Condition prev;

    private Condition curr;

    private String txt;

    private volatile Integer count;

    public AwaitSingal(ReentrantLock prevLock, ReentrantLock currLock,
                       String txt, Integer count,
                       Condition prev, Condition curr) {
        this.prevLock = prevLock;
        this.currLock = currLock;
        this.txt = txt;
        this.count = count;
        this.prev = prev;
        this.curr = curr;
    }

    @Override
    public void run() {

        while (count > 0) {

            prevLock.lock();
            try {

                currLock.lock();
                try {
                    System.out.println(txt);
                    count--;
                    curr.signalAll();
                } finally {
                    currLock.unlock();
                }

                if (count > 0) {
                    try {
                        prev.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                prevLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock aLock = new ReentrantLock();
        Condition aCon = aLock.newCondition();
        ReentrantLock bLock = new ReentrantLock();
        Condition bCon = bLock.newCondition();
        ReentrantLock cLock = new ReentrantLock();
        Condition cCon = cLock.newCondition();

        AwaitSingal a = new AwaitSingal(cLock, aLock, "A", 10, cCon, aCon);
        AwaitSingal b = new AwaitSingal(aLock, bLock, "B", 10, aCon, bCon);
        AwaitSingal c = new AwaitSingal(bLock, cLock, "C", 10, bCon, cCon);

        new Thread(a).start();
        Thread.sleep(100);
        new Thread(b).start();
        Thread.sleep(100);
        new Thread(c).start();

    }
}
