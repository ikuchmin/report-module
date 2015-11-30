package ru.osslabs.modules.report.reflections;

import ru.osslabs.Dispatcher;

/**
 * Created by ikuchmin on 30.11.15.
 */
public class NullableObject implements Dispatcher{
    @Override
    public <T> T dispatch(ObjectFactory<T> objectFactory, ReferenceSupplier<? extends T> referenceSupplier) {
        return objectFactory.build(this, referenceSupplier);
    }
}
