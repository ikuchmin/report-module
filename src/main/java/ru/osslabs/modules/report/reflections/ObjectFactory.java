package ru.osslabs.modules.report.reflections;

import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.MetaObject;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created by ikuchmin on 23.11.15.
 */
public interface ObjectFactory<T> {

    T build(DataObject dataObject, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends T> typeRef);

    T build(List<DataObject> dataObjectList, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends T> typeRef);

    T build(DataObject[] dataObjects, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends T> typeRef);

    T build(String dataObject, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends T> typeRef);

    /**
     * In this case you may get NPE. This situation expected when your schema
     * not equality scheme in CMDBuild. If you worry about it, you don't use fnMetaObject.
     * If you use fnMetaObject, you must catch NPE
     * @param fnMetaObject
     * @param typeRef
     * @return
     */
    T build(Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends T> typeRef);
}
