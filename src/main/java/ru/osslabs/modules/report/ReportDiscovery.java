package ru.osslabs.modules.report;

import ru.osslabs.modules.report.types.Report;

import java.util.Collection;

/**
 * Created by ikuchmin on 10.11.15.
 */
public interface ReportDiscovery {
    Collection<ReportWrapper<?>> getReports();

    ReportWrapper<?> getReport(String reportPath);

    <T extends Report> void registerWrapper(ReportWrapper<T> wrapper);
}
