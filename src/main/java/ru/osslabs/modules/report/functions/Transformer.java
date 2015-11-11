package ru.osslabs.modules.report.functions;

import ru.osslabs.modules.report.types.Report;

/**
 * Created by ikuchmin on 02.11.15.
 */
@FunctionalInterface
public interface Transformer<Re extends Report, Da, R> {
    R transform(Re report, Da data);
}
