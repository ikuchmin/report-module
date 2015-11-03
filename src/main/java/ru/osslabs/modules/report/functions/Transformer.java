package ru.osslabs.modules.report.functions;

import ru.osslabs.modules.report.Report;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface Transformer<Re extends Report, Da, R> {
    R transform(Da data, Re report);
}
