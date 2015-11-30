package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Try;
import ru.osslabs.model.datasource.IData;

/**
 * Created by ikuchmin on 24.11.15.
 */
public interface ObjectRegistry {

    /**
     * Method can return null.
     * @param <T>
     * @param cls
     * @return
     */
    <T> Function2<IData, ReferenceSupplier<? extends T>, Try<T>> dispatch(Class<?> cls);
}
