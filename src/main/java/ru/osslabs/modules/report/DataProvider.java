package ru.osslabs.modules.report;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface DataProvider<R> {
    <T> R provide(T data);
}
