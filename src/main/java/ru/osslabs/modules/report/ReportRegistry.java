package ru.osslabs.modules.report;

import javaslang.concurrent.Future;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import ru.osslabs.commons.dataproviders.ReportsDataProvider;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.reports.ReportInfo;
import ru.osslabs.modules.report.types.Report;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 15.11.15.
 */
@Named("reportsDataProvider")
@Startup
@Singleton
@ConcurrencyManagement(BEAN)
@Dependent
public class ReportRegistry implements ReportsDataProvider {
    private Map<RunnerKey, ReportFactory<? extends Report, ?>> runners = new HashMap<>(); // Function runner report is clean
    private Map<String, CommonReportWrapper> reportWrappers = new TreeMap<>();

    @Inject
    BeanManager beanManager;

    @Inject
    Logger logger;


    @PostConstruct
    public synchronized void init() {
        beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {
        }).stream()
                .filter((bean) -> bean.getTypes().contains(ReportFactoryMarker.class))
                .forEach(this::reportFactoryInitialize);
    }

    @SuppressWarnings("unchecked")
    private <T> void reportFactoryInitialize(Bean<T> reportFactoryBean) {
        ReportFactory<? extends Report, ?> rf = (ReportFactory) beanManager.getContext(reportFactoryBean.getScope())
                .get(reportFactoryBean, beanManager.createCreationalContext(reportFactoryBean));

        // TODO: This function is not tested on wildcard <? extends T> in generic class.
        Optional<Type> expectedClass = Arrays.asList(rf.getClass().getGenericInterfaces()).stream()
                .map((in) -> (ParameterizedType) in)
                .filter((in) -> in.getRawType() == ReportFactory.class)
                .map((in) -> in.getActualTypeArguments()[1])
                .findFirst();

        // Function report flow is available with RunnerKey
        RunnerKey runnerKey = new RunnerKey(rf.getReportCode(), rf.getExportType(), (Class<?>) expectedClass.orElse(Object.class));
        if (runners.put(runnerKey, rf) != null)
            logger.log(Level.WARNING, "In system is concurrence report flow with key: {0}", runnerKey);

        // Report information is available with ReportCode
        CommonReportWrapper currReportWrapper = reportWrappers.get(rf.getReportCode());
        if (currReportWrapper == null) {
            currReportWrapper = new CommonReportWrapper(
                    rf.getReportCode(),
                    rf.getReportPath(),
                    rf.getReportName(),
                    rf.getReportParams());
            reportWrappers.put(rf.getReportCode(), currReportWrapper);
        }
        currReportWrapper.addExportType(rf.getExportType());

        logger.log(Level.INFO, "Report with code {0} is registered: ", rf.getReportCode());
    }


    /**
     * Выполняется деспетчеризация на основе формата вызываемого отчета
     * и переданного expectedResult
     *
     * @param type
     * @param report         Так как разные Flow могут требовать разный тип Report, на входе
     *                       определить тип Report не представляется возможным, в связи с
     *                       приходится делать жесткое приведение
     * @param expectedResult Class ожидаемого типа результата по окончании процесса
     *                       формирования отчета
     * @param <R>            тип результат, возвращаемый по окончании формирования запроса
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Report, R> R runReport(String reportCode, ExportType type, T report, Class<R> expectedResult) {
        Function<? extends Report, ?> runner = objectNotNull(() -> runners.get(new RunnerKey(reportCode, type, expectedResult)).getRunner(), () -> {
            throw new IllegalArgumentException("Report not found");
        });

        return (R) unboxFunction(runner, report);
    }

    @SuppressWarnings("unchecked")
    private <T1, T2, R> R unboxFunction(Function<T1, R> func, T2 arg) {
        return func.apply((T1) arg);
    }

    @Override
    public ReportInfo getReportInfo(String s) {
        return null;
    }

    @Override
    public Collection<CommonReportWrapper> getReports() {
        return reportWrappers.values();
    }

    @Override
    public ReportWrapper getReport(String reportCode) {
        return reportWrappers.get(reportCode);
    }

    @Override
    public ru.osslabs.model.reports.Report getReportByPath(String s) {
        return null;
    }

    @Override
    public DataObject getReportParams(String s) {
        return null;
    }

    /**
     * @param reportCode
     * @param exportType
     * @param outputStream
     * @param map
     */
    @Override
    public void runReport(String reportCode, ExportType exportType, OutputStream outputStream, Map<String, Object> map) {
        runReport(reportCode, exportType,
                new CommonBuilder(Future.of(() ->
                        new HSSFWorkbook(new POIFSFileSystem(new BufferedInputStream(
                                this.getClass().getClassLoader().getResourceAsStream(
                                        reportWrappers.get(reportCode).getReportPath().toString()))))),
                        outputStream),
                Void.class);
    }

    @Override
    public List<String> getReportFilesList() {
        return null;
    }

    public static class RunnerKey {
        private final String reportCode;
        private final ExportType exportType;
        private final Class<?> clazz;

        public RunnerKey(String reportCode, ExportType exportType, Class<?> clazz) {
            this.reportCode = reportCode;
            this.exportType = exportType;
            this.clazz = clazz;
        }

        public String getReportCode() {
            return reportCode;
        }

        public ExportType getExportType() {
            return exportType;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        @Override
        public String toString() {
            return "RunnerKey{" +
                    "reportCode='" + reportCode + '\'' +
                    ", exportType=" + exportType +
                    ", clazz=" + clazz +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RunnerKey runnerKey = (RunnerKey) o;
            return Objects.equals(reportCode, runnerKey.reportCode) &&
                    exportType == runnerKey.exportType &&
                    Objects.equals(clazz, runnerKey.clazz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(reportCode, exportType, clazz);
        }
    }
}
