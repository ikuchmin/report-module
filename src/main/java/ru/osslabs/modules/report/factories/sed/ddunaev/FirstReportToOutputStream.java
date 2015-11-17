package ru.osslabs.modules.report.factories.sed.ddunaev;

import javaslang.Tuple2;
import ru.osslabs.modules.report.*;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by ikuchmin on 15.11.15.
 */
@ReportFactoryAnnotation
public class FirstReportToOutputStream<T extends BetweenDateReport & DestinationOutputStreamReport & SourceFututeHSSFWorkBookReport> implements ReportFactory<T, Void> {

    @Inject
    private Fetcher<BetweenDateReport, Stream<Tuple2<String, Integer>>> sedDataFetcher;

    @Override
    public String getReportCode() {
        return "/reports/juniprint/sedreport.xlt";
    }

    @Override
    public Path getReportPath() {
        return Paths.get("/reports/juniprint/sedreport.xlt");
    }

    @Override
    public String getReportName() {
        return "Отчет по документообороту";
    }

    @Override
    public ExportType getExportType() {
        return ExportType.xml;
    }

    @Override
    public Collection<ReportParameter> getReportParams() {
        return Arrays.asList(
                new ReportParameter("beginDate", "Дата начала диапазона", String.class),
                new ReportParameter("endDate", "Дата окончания диапазона", String.class));
    }

    @Override
    public Function<T, Void> getRunner() {
        return (r) -> new ReportBuilder<>(r)
                .compose(sedDataFetcher)
                .transform(HSSFWorkbookTransformers::fromStreamTuplesToHSSFWorkbook)
                .compile(JUniPrintEngine::compile)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
