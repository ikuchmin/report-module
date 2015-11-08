package ru.osslabs.modules.report.stages;

import ru.osslabs.modules.report.types.Report;
import ru.osslabs.modules.report.functions.Converter;
import ru.osslabs.modules.report.functions.Publisher;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface ReportOnPreConvertStage<Re extends Report, ReC> extends ReportStage<Re> {
    <ReF> ReportOnPreConvertStage<Re, ReF> convert(Converter<? super Re, ? super ReC, ReF> converter);
    <ReP> ReP publish(Publisher<? super Re, ? super ReC, ReP> converter);
}
