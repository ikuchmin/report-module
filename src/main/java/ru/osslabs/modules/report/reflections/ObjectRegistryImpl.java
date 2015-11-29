package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Option;
import ru.osslabs.Dispatcher;
import ru.osslabs.model.datasource.IData;
import ru.osslabs.modules.report.domain.Lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;
import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ObjectRegistryImpl implements ObjectRegistry {
    private Map<Class<?>, Function2<IData, ReferenceSupplier<?>, Option<?>>> factoryMethods;

    public ObjectRegistryImpl() {
        factoryMethods = new HashMap<>();
        factoryMethods.put(Object.class, (obj, type) -> Option.of(obj)
                .map(IData::getValue)
                .map((val) -> ObjectUtils.<Dispatcher>cast(val).dispatch(new ObjectFactoryImpl<>(this), type)));
        factoryMethods.put(Lookup.class, (lkp, type) -> Option.of(lkp)
                .map(IData::getValueLabel)
                .map(Lookup::new));
        factoryMethods.put(List.class, (lst, type) -> Option.of(lst)
                .map(IData::getValue)
                .map((val) -> ObjectUtils.<Dispatcher>cast(val).dispatch(new ListObjectFactory<>(this), cast(type))));
        factoryMethods.put(Integer.class, (val, type) -> Option.of(val)
                .map(IData::getValue)
                .map((v) -> Integer.valueOf(cast(v))));
        factoryMethods.put(String.class, (val, type) -> Option.of(val)
                .map(IData::getValue)
                .toOption()
                .map(ObjectUtils::<String>cast));
        factoryMethods.put(Boolean.class, (val, type) -> Option.of(val)
                .map(IData::getValue)
                .map(v -> Boolean.valueOf(cast(v))));
    }

        /**
         * Method can return null if Object.class wasn't got registry.
         * if type don't find method should be used Object.class.
         *
         * @param cls
         * @param <T>
         * @return
         */
    @Override
    public <T> Function2<IData, ReferenceSupplier<? extends T>, Option<T>> dispatch(Class<?> cls) {
        return cast(Option.of(factoryMethods.get(cls)).orElseGet(() -> factoryMethods.get(Object.class)));
    }
}
