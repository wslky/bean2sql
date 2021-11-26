package com.lky.bean2sql.annotation;


import java.lang.annotation.*;

/**
 * 指定映射名
 */
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface QColumn {
    String name() default "default";
}
