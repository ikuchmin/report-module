package ru.osslabs.modules.report;

import ru.osslabs.modules.report.types.Report;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;

/**
 * Created by ikuchmin on 09.11.15.
 */

public interface ReportFactory<T extends Report, R> extends ReportFactoryMarker {
    /**
     * Перенести в аннатацию - @ReportFactoryAnnotation
     *
     * @return
     */
    String getReportCode();

    /**
     * Перенести в аннатацию - @ReportFactoryAnnotation
     *
     * @return
     */
    Path getReportPath();

    /**
     * Перенести в аннатацию - @ReportFactoryAnnotation
     *
     * @return
     */
    String getReportName();

    /**
     * Тип экспортируемого отчета
     *
     * @return
     */
    ExportType getExportType();

    Collection<ReportParameter<?>> getReportParams();

    /**
     * Return function have to be clean in concurrency
     * @return
     */
    Function<T, R> getRunner();
}
