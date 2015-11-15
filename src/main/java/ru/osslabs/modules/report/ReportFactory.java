package ru.osslabs.modules.report;

import ru.osslabs.modules.report.ExportType;
import ru.osslabs.modules.report.types.Report;

import javax.enterprise.inject.Intercepted;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

/**
 * Created by ikuchmin on 09.11.15.
 */

public interface ReportFactory {
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

    Collection<ExportType> getExportTypes();

    Collection<ReportParameter> getReportParams();

    /**
     * Выполняется деспетчеризация на основе формата вызываемого отчета
     * и переданного expectedResult
     *
     * @param type
     * @param report Так как разные Flow могут требовать разный тип Report, на входе
     *               определить тип Report не представляется возможным, в связи с
     *               приходится делать жесткое приведение
     * @param expectedResult Class ожидаемого типа результата по окончании процесса
     *                       формирования отчета
     * @param <R> тип результат, возвращаемый по окончании формирования запроса
     * @return
     */
    <T extends Report, R> R runReport(ExportType type, T report, Class<R> expectedResult);
}
