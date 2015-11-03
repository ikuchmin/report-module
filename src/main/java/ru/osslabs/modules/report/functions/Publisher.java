package ru.osslabs.modules.report.functions;

import ru.osslabs.modules.report.Report;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface Publisher<Re extends Report, ReC, ReP> {
    ReP publish(Re report, ReC reportForPublication);
}
