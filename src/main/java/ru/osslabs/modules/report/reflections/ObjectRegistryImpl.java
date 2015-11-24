package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Option;
import ru.osslabs.model.datasource.DataObjectField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ObjectRegistryImpl implements ObjectRegistry {
    private Map<Class<?>, Function2<DataObjectField, Class<?>, ?>> factoryMethods;

    public ObjectRegistryImpl() {
        factoryMethods = new HashMap<>();
        factoryMethods.put(Object.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> new ObjectFactoryImpl(this).build(obj.getValue(), type), () -> null));
        factoryMethods.put(Integer.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> Integer.valueOf((String) obj.getValue()), () -> null));
        factoryMethods.put(String.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> (String) obj.getValue(), () -> null)); // You shouldn't remove cast to String
        factoryMethods.put(Boolean.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> Boolean.valueOf((String) obj.getValue()), () -> null));
//        factoryMethods.put(List.class, this::buildList);
    }

    @Override
    public <T> Function2<DataObjectField, Class<? extends T>, T> dispatch(Class<? extends T> cls) {
        return cast(factoryMethods.get(cls));
    }
}
