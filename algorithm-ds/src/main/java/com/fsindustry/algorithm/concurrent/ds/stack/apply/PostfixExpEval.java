package com.fsindustry.algorithm.concurrent.ds.stack.apply;

import com.fsindustry.algorithm.concurrent.ds.stack.ALStack;

/**
 * 后缀算术表达式求值
 *
 * @author fuzhengxin
 * @date 2018/6/13
 */
public class PostfixExpEval {

    /**
     * 后缀表达式求值
     *
     * @param exp 表达式
     *
     * @return 计算结果
     */
    public static int evalate(String exp) {

        ALStack<Integer> stack = new ALStack<>();

        int left, right;
        char current;
        for (int i = 0; i < exp.length(); i++) {
            current = exp.charAt(i);

            // 跳过空白字符
            if (isWhitespace(current)) {
                continue;
            }

            // 如果是操作数，则入栈
            if (Character.isDigit(current)) {
                stack.push(current - '0');
            }
            // 如果是操作符，则弹出操作数，执行计算
            else if (isOperator(current)) {
                right = stack.pop();
                left = stack.pop();
                stack.push(compute(left, right, current));
            }
        }

        return stack.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-'
                || ch == '*' || ch == '/'
                || ch == '%';
    }

    private static boolean isWhitespace(char ch) {
        return ch == ' ' || ch == '\t';
    }

    private static int compute(int left, int right, char op) {

        int value;
        switch (op) {
            case '+': {
                value = left + right;
                break;
            }
            case '-': {
                value = left - right;
                break;
            }
            case '*': {
                value = left * right;
                break;
            }
            case '/': {
                // 除零异常
                if (right == 0) {
                    throw new ArithmeticException("divide by zero");
                }
                value = left / right;
                break;
            }
            case '%': {
                // 除零异常
                if (right == 0) {
                    throw new ArithmeticException("divide by zero");
                }
                value = left % right;
                break;
            }
            default: {
                throw new ArithmeticException("unsupported operator " + op);
            }
        }

        return value;
    }

    public static void main(String[] args) {

        // 1+2
        System.out.println(evalate("12+"));

        // 1+2*3
        System.out.println(evalate("123*+"));

        // 1*2/3
        System.out.println(evalate("12*3/"));
    }
}
