package ru.osslabs.modules.report.reflections;

import javaslang.control.Option;
import javaslang.control.Try;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.MetaObject;

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
public class ListObjectFactory<T> extends AbstractObjectFactory<List<T>> {

    private static Logger log = Logger.getLogger(ListObjectFactory.class.getName());

    public ListObjectFactory(ObjectRegistry objectRegistry) {
        super(objectRegistry);
    }


    @Override
    public List<T> build(DataObject dataObject, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends List<T>> typeRef) {
        return Collections.emptyList();
    }

    @Override
    public List<T> build(List<DataObject> dataObjectList, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends List<T>> typeRef) {
        Type typeArgument = ((ParameterizedType) typeRef.getType()).getActualTypeArguments()[0];
        return Option.of(objectRegistry.dispatch(cast(typeArgument)))
                .map(fn -> ofAll(dataObjectList)
                        .map(dataObject -> (Try<T>) fn.apply(dataObject, referenceFabric(typeArgument)))
                        .foldLeft((List<T>) new ArrayList<>(), (acc, el) -> {
                            el.onSuccess(acc::add).onFailure(e ->
                                    log.warning(() -> "Object don't construct. Message: ".concat(e.getMessage())));
                            return acc;
                        }))
                .orElseGet(() -> {
                    log.warning(() -> "Function dispatcher for object with type ".concat(typeArgument.getTypeName())
                            .concat(" not found."));
                    return Collections.<T>emptyList();
                });
    }

    @Override
    public List<T> build(DataObject[] dataObjects, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends List<T>> typeRef) {
        return Collections.emptyList();
    }

    @Override
    public List<T> build(String dataObject, Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends List<T>> typeRef) {
        return Collections.emptyList();
    }

    @Override
    public List<T> build(Supplier<MetaObject> fnMetaObject, ReferenceSupplier<? extends List<T>> typeRef) {
        return Collections.emptyList();
    }
}
