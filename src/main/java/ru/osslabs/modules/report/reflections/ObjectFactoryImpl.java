package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Option;
import javaslang.control.Try;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.DataObjectField;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static ru.osslabs.modules.report.reflections.ObjectUtils.actualFieldName;
import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ObjectFactoryImpl<T> extends AbstractObjectFactory<T> {

    public ObjectFactoryImpl(ObjectRegistry objectRegistry) {
        super(objectRegistry);
    }

    @Override
    public T build(DataObject dataObject, Class<? extends T> type) {
        T instance;
        try {
            instance = type.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Constructor without arguments not found", e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Instance don't get from constructor", e);
        }

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            Option.of(objectRegistry.dispatch(field.getType())) // TODO: При поиске метода построения объекта необходимо использовать его иерархию. По аналогии с бинами
                    .map((func) -> func.apply(dataObject.getFields().get(actualFieldName(field)), field.getType()))
                    .peek((val) -> val.peek((v) -> Try.run(() -> field.set(instance, v)).onFailure(e -> {
                        throw new RuntimeException("Field with name " + field.getName() + " isn't set", e);
                    })));
        }
        return instance;
    }

    @Override
    public T build(List<DataObject> dataObjectList, Class<? extends T> type) {
        return null;
    }

    @Override
    public T build(DataObject[] dataObjects, Class<? extends T> type) {
        return null;
    }

    @Override
    public T build(Object dataObjects, Class<? extends T> type) {
        return null;
    }


}
