package ru.osslabs.modules.report.functions;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface Fetcher<T, R> {
    R compose(T report);
}
