package ru.osslabs.modules.report;

import ru.osslabs.modules.report.impls.sed.SEDDDunaevReport;
import ru.osslabs.modules.report.types.Report;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

/**
 * Created by ikuchmin on 09.11.15.
 */
public interface ReportWrapper<T extends Report> {
    String getReportName();
    Path getReportPath();
    Set<ExportType> getExportTypes();
    Set<ReportParameter> getReportParams();
    <R> R runReport(ExportType type, T report, Class<R> expectedResult);
}
