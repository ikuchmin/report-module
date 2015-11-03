package ru.osslabs.modules.report.stages;

import ru.osslabs.modules.report.Report;
import ru.osslabs.modules.report.functions.Publisher;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface ReportOnPrePublishStage<Re extends Report, ReF> extends ReportStage<Re> {
    <ReP> ReP publish(Publisher<Re, ReF, ReP> converter);

}
