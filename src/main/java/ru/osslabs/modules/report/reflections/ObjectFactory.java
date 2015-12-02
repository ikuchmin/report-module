package ru.osslabs.modules.report.reflections;

import ru.osslabs.model.datasource.DataObject;

import java.util.List;

/**
 * Created by ikuchmin on 23.11.15.
 */
public interface ObjectFactory<T> {

    T build(DataObject dataObject, ReferenceSupplier<? extends T> typeRef);

    T build(List<DataObject> dataObjectList, ReferenceSupplier<? extends T> typeRef);

    T build(DataObject[] dataObjects, ReferenceSupplier<? extends T> typeRef);

    T build(String dataObject, ReferenceSupplier<? extends T> typeRef);

    T build(ReferenceSupplier<? extends T> typeRef);
}
