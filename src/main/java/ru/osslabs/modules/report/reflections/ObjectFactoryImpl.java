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
public class ObjectFactoryImpl extends AbstractObjectFactory<Object> {

    public ObjectFactoryImpl(ObjectRegistry objectRegistry) {
        super(objectRegistry);
    }

    @Override
    public Object build(DataObject dataObject, Class<?> type) {
        Object instance;
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
                    .peek((val) -> Try.run(() -> field.set(instance, val)).onFailure(e -> {
                        throw new RuntimeException("Field with name " + field.getName() + " isn't set", e);
                    }));
        }
        return instance;
    }

    @Override
    public Object build(List<DataObject> dataObjectList, Class<?> type) {
        return null;
    }

    @Override
    public Object build(DataObject[] dataObjects, Class<?> type) {
        return null;
    }

}
