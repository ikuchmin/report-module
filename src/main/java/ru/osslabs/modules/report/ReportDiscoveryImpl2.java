package ru.osslabs.modules.report;

import ru.osslabs.modules.report.types.Report;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 15.11.15.
 */
public class ReportDiscoveryImpl2 {
    private final Map<RunnerKey, Function<? extends Report, ?>> runners = new ConcurrentHashMap<>();
    private final Map<RunnerKey, Collection<ReportParameter>> reportParameters = new ConcurrentHashMap<>();



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
        Function<? extends Report, ?> runner = objectNotNull(() -> runners.get(new RunnerKey(reportCode, type, expectedResult)), () -> {
            throw new IllegalArgumentException("Report not found");
        });

        return (R) unboxFunction(runner, report);
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
