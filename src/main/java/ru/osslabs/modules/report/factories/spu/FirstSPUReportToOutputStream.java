package ru.osslabs.modules.report.factories.spu;

import ru.osslabs.modules.report.ExportType;
import ru.osslabs.modules.report.ReportBuilder;
import ru.osslabs.modules.report.ReportFactory;
import ru.osslabs.modules.report.ReportParameter;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class FirstSPUReportToOutputStream<T> extends ReportFactory<T, Void> {
    @Override
    public String getReportCode() {
        return null;
    }

    @Override
    public Path getReportPath() {
        return null;
    }

    @Override
    public String getReportName() {
        return null;
    }

    @Override
    public ExportType getExportType() {
        return null;
    }

    @Override
    public Collection<ReportParameter> getReportParams() {
        return null;
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
