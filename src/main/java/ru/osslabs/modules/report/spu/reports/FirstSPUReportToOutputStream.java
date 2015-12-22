package ru.osslabs.modules.report.spu.reports;

import javaslang.control.Option;
import ru.osslabs.modules.report.ExportType;
import ru.osslabs.modules.report.ReportBuilder;
import ru.osslabs.modules.report.ReportFactory;
import ru.osslabs.modules.report.ReportParameter;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.spu.ServiceIdReport;
import ru.osslabs.modules.report.spu.domain.Service;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class FirstSPUReportToOutputStream<T extends SourceFututeHSSFWorkBookReport & DestinationOutputStreamReport & ServiceIdReport> implements ReportFactory<T, Void> {
    private final String PATH_TO_REPORT = "/reports/juniprint/spu_report_1.xlt";
    @Inject
    private Fetcher<ServiceIdReport, Option<Service>> spuDataFetcher;

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
        return "Отчет для SPU";
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
                .compose(spuDataFetcher)
                .transform(HSSFWorkbookTransformers::fromStreamServiceToHSSFWorkbook)
                .compile(JUniPrintEngine::compile)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
