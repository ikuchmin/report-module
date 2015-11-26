package ru.osslabs.modules.report.reflections;

import java.lang.reflect.Type;

/**
 * Created by ikuchmin on 26.11.15.
 */
@FunctionalInterface
public interface ReferenceSupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    Type getType();
}
