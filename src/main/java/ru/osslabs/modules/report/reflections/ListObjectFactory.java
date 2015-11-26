package ru.osslabs.modules.report.reflections;

import javaslang.collection.Stream;
import javaslang.control.Option;
import ru.osslabs.model.datasource.DataObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;
import static ru.osslabs.modules.report.reflections.TypeReference.referenceFabric;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ListObjectFactory<T> extends AbstractObjectFactory<List<T>> {

    public ListObjectFactory(ObjectRegistry objectRegistry) {
        super(objectRegistry);
    }


    @Override
    public List<T> build(DataObject dataObject, ReferenceSupplier<? extends List<T>> typeRef) {
        return null;
    }

    @Override
    public List<T> build(List<DataObject> dataObjectList, ReferenceSupplier<? extends List<T>> typeRef) {
        Type typeArgument = ((ParameterizedType) typeRef.getType()).getActualTypeArguments()[0];
        return Option.of(objectRegistry.dispatch(cast(typeArgument)))
                .map((func) -> Stream.ofAll(dataObjectList)
                        .map((dataObject) -> (Option<T>) func.apply(dataObject, referenceFabric(typeArgument)))
                        .foldLeft((List<T>) new ArrayList<T>(), (acc, el) -> {
                            el.peek(acc::add);
                            return acc;
                        }))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<T> build(DataObject[] dataObjects, ReferenceSupplier<? extends List<T>> typeRef) {
        return null;
    }

    @Override
    public List<T> build(Object dataObjects, ReferenceSupplier<? extends List<T>> typeRef) {
        return null;
    }
}
