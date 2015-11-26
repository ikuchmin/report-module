package ru.osslabs.modules.report.reflections;

import javaslang.control.Option;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by ikuchmin on 23.11.15.
 */
public class ObjectMapper {

    private ObjectRegistry objectRegistry;

    public ObjectMapper() {
        objectRegistry = new ObjectRegistryImpl();
    }

    /**
     * TODO: Temp decision
     *
     * @param dataObject
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Option<T> readValue(IData dataObject, ReferenceSupplier<? extends T> clazz, Class<T> cls) {
        Objects.requireNonNull(dataObject, "DataObject wouldn't null");
        return Option.of(objectRegistry.<T>dispatch(cls))
                .flatMap((fn) -> fn.apply(dataObject, clazz));
    }

    /**
     * @param dataObject
     * @param clazz
     * @param <T>
     * @return
     * @throws NoSuchElementException
     */
    public <T> T readValue(DataObject dataObject, ReferenceSupplier<? extends T> clazz, Class<T> cls) {
        Objects.requireNonNull(dataObject, "DataObject wouldn't null");
        return Option.of(objectRegistry.<T>dispatch(cls))
                .flatMap((fn) -> fn.apply(dataObject, clazz)).get();
    }


    /**
     * TODO: This method should be implement
     *
     * @param dataObject
     * @param typeRef
     * @param <T>
     * @return
     */
    public <T> Option<T> readValue(IData dataObject, ReferenceSupplier<? extends T> typeRef) {
        Objects.requireNonNull(dataObject, "DataObject wouldn't null");
        throw new NotImplementedException();
    }

    /**
     * * TODO: This method should be implement
     *
     * @param dataObject
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T readValue(IData dataObject, Class<T> clazz) {
        Objects.requireNonNull(dataObject, "DataObject wouldn't null");
        throw new NotImplementedException();
    }
}
