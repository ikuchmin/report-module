package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import ru.osslabs.model.datasource.DataObjectField;

/**
 * Created by ikuchmin on 24.11.15.
 */
public interface ObjectRegistry {

    <T> Function2<DataObjectField, Class<? extends T>, T> dispatch(Class<? extends T> cls);
}
