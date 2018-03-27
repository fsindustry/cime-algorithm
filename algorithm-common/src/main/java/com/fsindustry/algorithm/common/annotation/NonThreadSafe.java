package com.fsindustry.algorithm.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标识一个类型为非线程安全类型
 */
@Retention(RetentionPolicy.SOURCE)
public @interface NonThreadSafe {
}
