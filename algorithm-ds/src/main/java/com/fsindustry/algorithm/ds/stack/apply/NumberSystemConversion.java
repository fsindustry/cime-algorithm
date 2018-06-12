package com.fsindustry.algorithm.ds.stack.apply;

import com.fsindustry.algorithm.ds.stack.ALStack;

/**
 * <h1>数制转换</h1>
 *
 * @author fuzhengxin
 * @date 2018/6/12
 */
public class NumberSystemConversion {

    /**
     * 数制转换:10进制数转换为其它进制
     *
     * @param num  数值
     * @param base 进制，取值2~16
     *
     * @return 转换后的字符串
     */
    public static String convert(int num, int base) {

        String digit = "0123456789ABCDEF";
        ALStack<Character> stack = new ALStack<>();

        // 循环取余，使用do-while保证输入num为0时，也能运算
        do {
            stack.push(digit.charAt(num % base));
            num /= base;
        } while (num != 0);

        // 倒转输出结果
        StringBuilder sb = new StringBuilder("");
        while (stack.size() > 0) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(convert(0, 2));
        System.out.println(convert(10, 8));
        System.out.println(convert(2, 2));
        System.out.println(convert(16, 16));
    }
}
