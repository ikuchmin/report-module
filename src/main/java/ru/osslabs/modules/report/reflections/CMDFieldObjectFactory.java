package ru.osslabs.modules.report.reflections;

import javaslang.collection.Stream;
import javaslang.control.Option;
import javaslang.control.Try;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.MetaObject;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.CMDField;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static javaslang.collection.Stream.ofAll;
import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;
import static ru.osslabs.modules.report.reflections.TypeReference.referenceFabric;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class CMDFieldObjectFactory<T> extends AbstractObjectFactory<CMDField<T>> {

    private static Logger log = Logger.getLogger(CMDFieldObjectFactory.class.getName());

    public CMDFieldObjectFactory(ObjectRegistry objectRegistry) {
        super(objectRegistry);
    }


    @Override
    public CMDField<T> build(DataObject dataObject, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
//        return Option.of(dataObject)
//                .map(v -> (CMDField<T>)CMDField.of(cast(v.getValue()), v.getMetaObject().getName()))
//                .orElseGet(CMDField<T>::new);
    }

    @Override
    public CMDField<T> build(List<DataObject> dataObjectList, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
//        return Stream.ofAll(dataObjectList)
//                .headOption()
//                .map(v -> (CMDField<T>)CMDField.of(cast(v.getValue()), v.getMetaObject().getName()))
//                .orElseGet(CMDField<T>::new);
    }

    @Override
    public CMDField<T> build(DataObject[] dataObjects, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
//        return Stream.ofAll(dataObjects)
//                .headOption()
//                .map(v -> (CMDField<T>)CMDField.of(cast(v.getValue()), v.getMetaObject().getName()))
//                .orElseGet(CMDField<T>::new);
    }

    /**
     * In this context function is working only for CMDField<Boolean>
     * @param dataObject
     * @param fnMetaObject
     * @param typeRef
     * @return
     */
    @Override
    public CMDField<T> build(String dataObject, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return Option.of(dataObject)
                .map(v -> (CMDField<T>)CMDField.of(Boolean.valueOf(dataObject), fnMetaObject.get().getName()))
                .orElseGet(CMDField<T>::new);
    }

    @Override
    public CMDField<T> build(Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
    }
}
