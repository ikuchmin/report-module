package ru.osslabs.modules.report.reflections;


/**
 * Created by ikuchmin on 24.11.15.
 */
public abstract class AbstractObjectFactory<T> implements ObjectFactory<T> {

    protected ObjectRegistry objectRegistry;

    public AbstractObjectFactory(ObjectRegistry objectRegistry) {
        this.objectRegistry = objectRegistry;
    }

}
