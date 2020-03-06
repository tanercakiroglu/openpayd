package com.openpayd.currency.aspect.loggable;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Loggable {

    String message() default "Audit Message";
}
