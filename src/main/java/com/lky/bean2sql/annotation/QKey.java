package com.lky.bean2sql.annotation;

import java.lang.annotation.*;

/**
 * 主键
 */
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface QKey {

}
