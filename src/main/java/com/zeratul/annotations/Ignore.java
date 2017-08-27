package com.zeratul.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author dreamyao
 * @version 1.0.0
 * @title
 * @Date 2017/8/27 15:26
 */
@Retention(RUNTIME)
@Target({METHOD})
public @interface Ignore {
}
