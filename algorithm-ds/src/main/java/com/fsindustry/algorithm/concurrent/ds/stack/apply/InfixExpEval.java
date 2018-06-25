package com.fsindustry.algorithm.concurrent.ds.stack.apply;

import com.fsindustry.algorithm.concurrent.ds.stack.ALStack;

/**
 * 中缀表达式求值
 * 方法1：双栈表达式求值；
 * 方法2：中缀表达式转后缀表达式，然后对后缀表达式求值；
 *
 * @author fuzhengxin
 * @date 2018/6/13
 */
public class InfixExpEval {

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^';
    }

    /**
     * 双栈表达式求值
     *
     * @param exp 表达式
     *
     * @return 计算结果
     */
    public static int eval1(String exp) {

        ALStack<Integer> operands = new ALStack<>();
        ALStack<Character> operators = new ALStack<>();

        char current;
        for (int i = 0; i < exp.length(); i++) {

            current = exp.charAt(i);

            // 如果是操作符
            if (isOperator(current)) {

            }

            // 如果是操作数

        }

        return 0;
    }

    /**
     * 中缀转后缀求值
     *
     * @param exp 表达式
     *
     * @return 计算结果
     */
    public static int eval2(String exp) {

        return 0;
    }
}
