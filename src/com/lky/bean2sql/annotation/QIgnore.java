package com.lky.bean2sql.annotation;

import java.lang.annotation.*;

/**
 * 是否忽略
 */
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface QIgnore {

}
