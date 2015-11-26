package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Option;
import ru.osslabs.model.datasource.DataObjectField;
import ru.osslabs.model.datasource.IData;

/**
 * Created by ikuchmin on 24.11.15.
 */
public interface ObjectRegistry {

    /**
     * Method can return null.
     * @param cls
     * @param <T>
     * @return
     */
    <T> Function2<IData, TypeReference<? extends T>, Option<T>> dispatch(Class<?> cls);
}
