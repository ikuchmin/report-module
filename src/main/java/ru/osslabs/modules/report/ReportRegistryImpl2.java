package ru.osslabs.modules.report;

import ru.osslabs.commons.dataproviders.ReportsDataProvider;
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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 15.11.15.
 */
//@Named("reportsDataProvider")
@Startup
@Singleton
@ConcurrencyManagement(BEAN)
@Dependent
public class ReportRegistryImpl2 {
    private final Map<RunnerKey, Function<? extends Report, ?>> runners = new ConcurrentHashMap<>();

    private final Map<RunnerKey, Collection<ReportParameter>> reportParameters = new ConcurrentHashMap<>();

    @Inject
    BeanManager beanManager;

    @Inject
    Logger logger;


    @PostConstruct
    public void init() {
        System.out.println("Bp");
        beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {}).stream()
                .filter((bean) -> bean.getTypes().contains(ReportFactoryMarker.class))
                .forEach(this::reportFactoryInitialize);
    }

    @SuppressWarnings("unchecked")
    private <T> void reportFactoryInitialize(Bean<T> reportFactoryBean) {
        ReportFactory<? extends Report, ?> rf = (ReportFactory) beanManager.getContext(reportFactoryBean.getScope())
                .get(reportFactoryBean, beanManager.createCreationalContext(reportFactoryBean));

        Optional<Type> expectedClass = Arrays.asList(rf.getClass().getGenericInterfaces()).stream()
                .map((in) -> (ParameterizedType) in)
                .filter((in) -> in.getRawType() == ReportFactory.class)
                .map((in) -> in.getActualTypeArguments()[1])
                .findFirst();

        RunnerKey runnerKey = new RunnerKey(rf.getReportCode(), rf.getExportType(), (Class<?>) expectedClass.orElse(Object.class));
        if (runners.put(runnerKey, rf.getRunner()) != null)
            logger.log(Level.WARNING, "In system is concurrence report flow with key: {0}", runnerKey);
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
//    @Override
    @SuppressWarnings("unchecked")
    public <T extends Report, R> R runReport(ExportType type, T report, Class<R> expectedResult) {
        return null;
//        Function<? extends Report, ?> runner = objectNotNull(() -> runners.get(new RunnerKey(reportCode, type, expectedResult)), () -> {
//            throw new IllegalArgumentException("Report not found");
//        });
//
//        return (R) unboxFunction(runner, report);
    }

    @SuppressWarnings("unchecked")
    private <T1, T2, R> R unboxFunction(Function<T1, R> func, T2 arg) {
        return func.apply((T1) arg);
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

            if (!reportCode.equals(runnerKey.reportCode)) return false;
            if (exportType != runnerKey.exportType) return false;
            return clazz.equals(runnerKey.clazz);

        }

        @Override
        public int hashCode() {
            int result = reportCode.hashCode();
            result = 31 * result + exportType.hashCode();
            result = 31 * result + clazz.hashCode();
            return result;
        }
    }
}
