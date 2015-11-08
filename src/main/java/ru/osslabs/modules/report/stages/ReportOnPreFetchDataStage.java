package ru.osslabs.modules.report.stages;

import ru.osslabs.modules.report.types.Report;
import ru.osslabs.modules.report.functions.Fetcher;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface ReportOnPreFetchDataStage<Re extends Report> extends ReportStage<Re> {
    <R> ReportOnPreCompileStage<Re, R> compose(Fetcher<? super Re, R > fetcher);
}
