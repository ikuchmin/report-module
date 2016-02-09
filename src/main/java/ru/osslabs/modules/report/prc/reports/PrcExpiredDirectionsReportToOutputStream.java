package ru.osslabs.modules.report.prc.reports;

import ru.osslabs.modules.report.*;
import ru.osslabs.modules.report.decorators.*;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.prc.domain.Direction;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;
import ru.osslabs.modules.report.types.Report;

import javax.inject.Inject;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;

public class PrcExpiredDirectionsReportToOutputStream<T extends SourceFututeHSSFWorkBookReport & DestinationOutputStreamReport & Report> implements ReportFactory<T, Void> {
    private final String PATH_TO_REPORT = "/reports/juniprint/prc/prc_report_expiredDirections.xlt";
    @Inject
    private Fetcher<Report, List<Direction>> rpcDirectionsDataFetcher;

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
        return "Отчет для ПРК";
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
                .compose(rpcDirectionsDataFetcher)
                .transform(HSSFWorkbookTransformers::fromDirectionListToReportExpiredDirections)
                .compile(JUniPrintEngine::compile)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
