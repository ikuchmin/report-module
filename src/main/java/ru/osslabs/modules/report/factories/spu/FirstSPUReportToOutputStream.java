package ru.osslabs.modules.report.factories.spu;

import javaslang.Tuple2;
import ru.osslabs.modules.report.ExportType;
import ru.osslabs.modules.report.ReportBuilder;
import ru.osslabs.modules.report.ReportFactory;
import ru.osslabs.modules.report.ReportParameter;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.domain.spu.SubServices;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;
import ru.osslabs.modules.report.types.Report;

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
public class FirstSPUReportToOutputStream<T extends SourceFututeHSSFWorkBookReport & DestinationOutputStreamReport> implements ReportFactory<T, Void> {

    @Inject
    private Fetcher<Report, Stream<SubServices>> spuDataFetcher;

    @Inject
    private Fetcher<BetweenDateReport, Stream<Tuple2<String, Integer>>> sedDataFetcher;

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
                .compile((re, d) -> d)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
