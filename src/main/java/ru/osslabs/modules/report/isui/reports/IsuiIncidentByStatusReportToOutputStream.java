package ru.osslabs.modules.report.isui.reports;

import ru.osslabs.modules.report.*;
import ru.osslabs.modules.report.decorators.*;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.isui.domain.Incident;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;
import ru.osslabs.modules.report.types.Report;

import javax.inject.Inject;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class IsuiIncidentByStatusReportToOutputStream<T extends SourceFututeHSSFWorkBookReport & DestinationOutputStreamReport & Report> implements ReportFactory<T, Void> {
    private final String PATH_TO_REPORT = "/reports/juniprint/isui/isui_report_incidentByStatus.xlt";
    @Inject
    private Fetcher<Report, List<Incident>> isuiIncidentsDataFetcher;

    @Override
    public String getReportCode() {
        return PATH_TO_REPORT;
    }

    @Override
    public Path getReportPath() {
        return Paths.get(PATH_TO_REPORT);
    }

    @Override
    public String getReportName() {
        return "Отчет для ИСУИ";
    }

    @Override
    public ExportType getExportType() {
        return ExportType.xls;
    }

    @Override
    public Collection<ReportParameter<?>> getReportParams() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Function<T, Void> getRunner() {
        return (r) -> new ReportBuilder<>(r)
                .compose(isuiIncidentsDataFetcher)
                .transform(HSSFWorkbookTransformers::fromIncidentListToReportIncidentByStatus)
                .compile(JUniPrintEngine::compile)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
