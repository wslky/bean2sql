package com.lky.bean2sql.annotation;

import java.lang.annotation.*;

/**
 * 需要生成的表
 */
@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface QTable {
    String name() default "default";
}
