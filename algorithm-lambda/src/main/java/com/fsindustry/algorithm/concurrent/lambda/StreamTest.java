package com.fsindustry.algorithm.concurrent.lambda;

import java.util.stream.Stream;

/**
 * Created by fuzhengxin on 2017/12/31.
 */
public class StreamTest {

    public void test1() {
        Stream<String> values = Stream.of("a", "b", "c");
        int sum = values.mapToInt(String::length).sum();
    }
}
