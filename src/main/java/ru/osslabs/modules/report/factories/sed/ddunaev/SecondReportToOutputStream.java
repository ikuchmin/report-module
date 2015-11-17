package ru.osslabs.modules.report.factories.sed.ddunaev;

import ru.osslabs.modules.report.*;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.DestinationPathReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.fetchers.MockDataFetchers;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.function.Function;

/**
 * Created by ikuchmin on 15.11.15.
 */
public class SecondReportToOutputStream<T extends DestinationOutputStreamReport & SourceFututeHSSFWorkBookReport> implements ReportFactory<T, Void>, ReportFactoryMarker {

    @Override
    public String getReportCode() {
        return "/reports/juniprint/template1.xlt";
    }

    @Override
    public Path getReportPath() {
        return Paths.get("/reports/juniprint/template1.xlt");
    }

    @Override
    public String getReportName() {
        return "Тестовый отчет для демонстрации работы";
    }

    @Override
    public ExportType getExportType() {
        return ExportType.xml;
    }

    @Override
    public Collection<ReportParameter> getReportParams() {
        return null;
    }

    @Override
    public Function<T, Void> getRunner() {
        Double multiplier = 100.0;
        return (r) -> new ReportBuilder<>(r)
                .compose(MockDataFetchers::matrixFiveOnTwentyFive)
                .transform((re, data) -> Matrix.of(data)
                        .matrixMap((row, col, el) -> el * multiplier))
                .transform(HSSFWorkbookTransformers::fromMatrixToHSSFWorkbook)
                .compile(JUniPrintEngine::compile)
                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
    }
}
