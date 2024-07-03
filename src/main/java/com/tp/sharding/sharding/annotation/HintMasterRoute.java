package com.tp.sharding.sharding.annotation;

import java.lang.annotation.*;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:01:07
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface HintMasterRoute {
    boolean isMasterOnly() default true;
}
