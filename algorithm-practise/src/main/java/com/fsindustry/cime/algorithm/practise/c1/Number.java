package com.fsindustry.cime.algorithm.practise.c1;

public class Number {


    public static void main(String[] args) {

        boolean[][] arr = new boolean[5][5];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }

    }

    public static void pingfangen() {
        //平方根
        double t = 25.0;
        while (Math.abs(t - 25.0 / t) > 0.001) {
            t = (25.0 / t - t) / 2.0;
        }
        System.out.printf("%.5f\n", t);
    }

    public static void feibonaqi() {
        // 斐波那契数列
        int f = 0;
        int g = 1;

        for (int i = 0; i <= 15; i++) {
            System.out.println(f);
            f = f + g;
            g = f - g;
        }
    }
}
