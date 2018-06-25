package com.fsindustry.algorithm.concurrent.ds.stack.apply;

import com.fsindustry.algorithm.concurrent.ds.stack.ALStack;

/**
 * 括号对检查
 *
 * @author fuzhengxin
 * @date 2018/6/12
 */
public class BraketPairCheck {

    /**
     * 括号对检查
     *
     * @param exp 待检查的表达式
     *
     * @return true，配对正常；false，配对异常；
     */
    public static boolean check(String exp) {

        ALStack<Character> stack = new ALStack<>();

        char left, current;
        for (int i = 0; i < exp.length(); i++) {

            current = exp.charAt(i);

            // 检查到左括号，就放入栈中
            if (current == '('
                    || current == '['
                    || current == '{') {
                stack.push(current);
            }
            // 如果是右括号，则判断配对
            else if (current == '}'
                    || current == ']'
                    || current == ')') {

                // 栈为空，表明缺少左括号
                if (stack.isEmpty()) {
                    return false;
                }

                // 括号对不匹配
                left = stack.pop();
                if (left == '(' && current != ')'
                        || left == '[' && current != ']'
                        || left == '{' && current != '}') {
                    return false;
                }
            }
        }

        // 栈不为空，说明缺少右括号
        if (!stack.isEmpty()) {
            return false;
        }

        // 括号平衡
        return true;
    }

    public static void main(String[] args) {

        // 正常用例
        System.out.println(check(""));
        System.out.println(check("adfasfdafasf"));
        System.out.println(check("adfasf23(afafasfa)adfafasf"));
        System.out.println(check("232342[adfafa]afaf"));
        System.out.println(check("adfasfa{adfasfaf}asdfadf"));
        System.out.println(check("{[adfafa(adfafa)]}"));
        System.out.println(check("{234fasfda[(dfadfa)afasdfa]asdfaf[e234(fasdf)547]vzfas}faw"));

        // 异常用例
        System.out.println(check("asdfa(adfa)adfa)"));
        System.out.println(check("[adsfa[]adfasdfa"));
        System.out.println(check("{[(adsfad}])"));

    }

}
