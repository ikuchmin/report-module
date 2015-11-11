package ru.osslabs.modules.report;

import ru.osslabs.modules.report.ExportType;

import javax.enterprise.inject.Intercepted;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.util.Set;

/**
 * Created by ikuchmin on 09.11.15.
 */

public interface ReportFactory<T> {
    /**
     * Перенести в аннатацию - @ReportFactoryAnnotation
     * @return
     */
    String getReportCode();

    /**
     * Перенести в аннатацию - @ReportFactoryAnnotation
     * @return
     */
    Path getReportPath();

    /**
     * Перенести в аннатацию - @ReportFactoryAnnotation
     * @return
     */
    String getReportName();

    Set<ExportType> getExportTypes();

    // Выполняется деспетчеризация на основе формата вызываемого отчета и предполагаемого результата
    <R> R runReport(ExportType type, T report, Class<R> expectedResult);
}
