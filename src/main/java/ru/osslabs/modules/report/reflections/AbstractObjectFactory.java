package ru.osslabs.modules.report.reflections;


/**
 * Created by ikuchmin on 24.11.15.
 */
public abstract class AbstractObjectFactory<T> implements ObjectFactory<T> {

    protected ObjectRegistry objectRegistry;

    public AbstractObjectFactory(ObjectRegistry objectRegistry) {
        this.objectRegistry = objectRegistry;
    }

    /**
     * It is stub for work dispatching on dataObject
     * @param obj
     * @param type
     * @return
     */
    @Override
    public T build(Object obj, Class<? extends T> type) {
        return null;
    };
}
