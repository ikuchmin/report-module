package ru.osslabs.modules.report.isui.reports;

import ru.osslabs.modules.report.*;
import ru.osslabs.modules.report.decorators.*;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.isui.domain.Incident;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.spu.ServiceIdReport;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;

import javax.inject.Inject;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class IsuiServiceRequestsByIncidentReportToOutputStream<T extends SourceFututeHSSFWorkBookReport & DestinationOutputStreamReport & ServiceIdReport> implements ReportFactory<T, Void> {
    private final String PATH_TO_REPORT = "/reports/juniprint/isui/isui_report_serviceRequestsByIncident.xlt";
    @Inject
    private Fetcher<ServiceIdReport, List<Incident>> isuiDataFetcher;

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
                .compose(isuiDataFetcher)
                .transform(HSSFWorkbookTransformers::fromIncidentListToReportServiceRequestsByIncident)
                .compile(JUniPrintEngine::compile)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
