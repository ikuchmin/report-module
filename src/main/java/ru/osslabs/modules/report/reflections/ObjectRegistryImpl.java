package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Option;
import ru.osslabs.Dispatcher;
import ru.osslabs.model.datasource.DataObjectField;
import ru.osslabs.model.datasource.IData;
import ru.osslabs.modules.report.domain.Lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;
import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ObjectRegistryImpl implements ObjectRegistry {
    private Map<Class<?>, Function2<IData, TypeReference<?>, Option<?>>> factoryMethods;

    public ObjectRegistryImpl() {
        factoryMethods = new HashMap<>();
        factoryMethods.put(Object.class, (obj, type) -> Option.of(obj.getValue())
                .map((val) -> ObjectUtils.<Dispatcher>cast(val).dispatch(new ObjectFactoryImpl<>(this), type)));
        factoryMethods.put(Lookup.class, (obj, type) -> Option.of(obj.getValueLabel())
                .map(Lookup::new));
        factoryMethods.put(List.class, (obj, type) -> Option.of(obj.getValue())
                .map((val) -> ObjectUtils.<Dispatcher>cast(val).dispatch(new ListObjectFactory<>(this), cast(type))));
        factoryMethods.put(Integer.class, (obj, type) -> Option.of(obj.getValue())
                .map((val) -> Integer.valueOf(cast(val))));
        factoryMethods.put(String.class, (obj, type) -> Option.of(obj.getValue())
                .map(ObjectUtils::cast));
        factoryMethods.put(Boolean.class, (obj, type) -> Option.of(obj.getValue())
                .map((val) -> Boolean.valueOf(cast(val))));
    }


    /**
     * Method can return null if Object.class wasn't got registry.
     * if type don't find method should be used Object.class.
     * @param cls
     * @param <T>
     * @return
     */
    @Override
    public <T> Function2<IData, TypeReference<? extends T>, Option<T>> dispatch(Class<?> cls) {
        return cast(Option.of(factoryMethods.get(cls)).orElseGet(() ->factoryMethods.get(Object.class)));
    }
}
