package ru.osslabs.modules.report.reflections;

import javaslang.collection.Stream;
import javaslang.control.Option;
import javaslang.control.Try;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.CMDField;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public CMDField<T> build(DataObject dataObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
//        return Option.of(dataObject)
//                .map(v -> (CMDField<T>)CMDField.of(cast(v.getValue()), v.getMetaObject().getName()))
//                .orElseGet(CMDField<T>::new);
    }

    @Override
    public CMDField<T> build(List<DataObject> dataObjectList, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
//        return Stream.ofAll(dataObjectList)
//                .headOption()
//                .map(v -> (CMDField<T>)CMDField.of(cast(v.getValue()), v.getMetaObject().getName()))
//                .orElseGet(CMDField<T>::new);
    }

    @Override
    public CMDField<T> build(DataObject[] dataObjects, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
//        return Stream.ofAll(dataObjects)
//                .headOption()
//                .map(v -> (CMDField<T>)CMDField.of(cast(v.getValue()), v.getMetaObject().getName()))
//                .orElseGet(CMDField<T>::new);
    }

    @Override
    public CMDField<T> build(NullableObject nullableObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
    }

    @Override
    public CMDField<T> build(Object dataObjects, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
    }

    @Override
    public CMDField<T> build(String dataObject, ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return Option.of(dataObject)
                .map(v -> (CMDField<T>)CMDField.of(dataObject), v.getMetaObject().getName()))
                .orElseGet(CMDField<T>::new);
    }

    @Override
    public CMDField<T> build(ReferenceSupplier<? extends CMDField<T>> typeRef) {
        return new CMDField<>();
    }
}
