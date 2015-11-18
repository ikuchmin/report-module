package ru.osslabs.modules.report;

import ru.osslabs.modules.report.types.Report;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

/**
 * Created by ikuchmin on 09.11.15.
 */
public interface ReportWrapper {
    String getReportName();

    String getReportCode();

    Path getReportPath();

    Collection<ExportType> getExportTypes();

    Collection<ReportParameter> getReportParams();

//    <R> R runReport(ExportType type, T report, Class<R> expectedResult);
//
//    <R> R runReport(ExportType type, OutputStream outputStream, Class<R> expectedResult);
}
