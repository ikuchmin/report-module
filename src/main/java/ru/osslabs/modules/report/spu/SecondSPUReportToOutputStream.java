package ru.osslabs.modules.report.spu;

import ru.osslabs.modules.report.ExportType;
import ru.osslabs.modules.report.ReportBuilder;
import ru.osslabs.modules.report.ReportFactory;
import ru.osslabs.modules.report.ReportParameter;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.spu.domain.SubServices;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class SecondSPUReportToOutputStream<T extends SourceFututeHSSFWorkBookReport & DestinationOutputStreamReport & ServiceIdReport> implements ReportFactory<T, Void> {

    @Inject
    private Fetcher<ServiceIdReport, Stream<SubServices>> spuDataFetcher;

    @Override
    public String getReportCode() {
        return "/reports/juniprint/spu_report_2.xlt";
    }

    @Override
    public Path getReportPath() {
        return Paths.get("/reports/juniprint/spu_report_2.xlt");
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
                .transform(HSSFWorkbookTransformers::fromStreamSubServicesToHSSFWorkbook)
                .compile(JUniPrintEngine::compile)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
