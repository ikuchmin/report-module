package ru.osslabs.modules.report.functions;

import ru.osslabs.modules.report.types.Report;

/**
 * Created by ikuchmin on 02.11.15.
 */
@FunctionalInterface
public interface Converter<Re extends Report, ReC, R> {
    R converter(Re report, ReC compileReport);
}
