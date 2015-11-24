package ru.osslabs.modules.report;

import java.lang.annotation.*;

/**
 * Created by ikuchmin on 23.11.15.
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CMDBuildField {
    String name() default "";
}
