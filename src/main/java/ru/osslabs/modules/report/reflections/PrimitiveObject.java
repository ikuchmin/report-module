package ru.osslabs.modules.report.reflections;

import ru.osslabs.Dispatcher;

/**
 * Created by ikuchmin on 01.12.15.
 */
public class PrimitiveObject implements Dispatcher {

    @Override
    public <T> T dispatch(ObjectFactory<T> objectFactory, ReferenceSupplier<? extends T> referenceSupplier) {
        return objectFactory.build(this, referenceSupplier);
    }
}
