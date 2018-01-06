package com.fsindustry.algorithm.concurrent;

/**
 * 考题：使用3个线程，分别依次输出A->B->C，循环往复10次
 */
public class WaitNotify implements Runnable {

    private final Object prev;

    private final Object curr;

    private String txt;

    private volatile Integer count;

    public WaitNotify(Object prev, Object curr,
                      String txt, Integer count) {
        this.prev = prev;
        this.curr = curr;
        this.txt = txt;
        this.count = count;
    }

    @Override
    public void run() {

        while (count > 0) {
            synchronized(prev) {
                synchronized(curr) {
                    System.out.println(txt + count);
                    count--;
                    curr.notify();
                }

                if (count > 0) {
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        WaitNotify A = new WaitNotify(c, a, "A", 10);
        WaitNotify B = new WaitNotify(a, b, "B", 10);
        WaitNotify C = new WaitNotify(b, c, "C", 10);

        new Thread(A).start();
        Thread.sleep(100);
        new Thread(B).start();
        Thread.sleep(100);
        new Thread(C).start();
    }

}
