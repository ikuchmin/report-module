package ru.osslabs.modules.report;

import ru.osslabs.modules.report.types.Report;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 14.11.15.
 */
public abstract class AbstractReportFactory implements ReportFactory {
    protected final Map<RunnerKey, Function<? extends Report, ?>> runners = new HashMap<>();


    @Override
    @SuppressWarnings("unchecked")
    public <T extends Report, R> R runReport(ExportType type, T report, Class<R> expectedResult) {
        Function<? extends Report, ?> runner = objectNotNull(() -> runners.get(new RunnerKey(type, expectedResult)), () -> {
            throw new IllegalArgumentException("Report not found");
        });

        return (R) unboxFunction(runner, report);
    }

    @SuppressWarnings("unchecked")
    private <T1, T2, R> R unboxFunction(Function<T1, R> func, T2 arg) {
        return func.apply((T1) arg);
    }


    public static class RunnerKey {
        private final ExportType exportType;
        private final Class<?> clazz;

        public RunnerKey(ExportType exportType, Class<?> clazz) {
            this.exportType = exportType;
            this.clazz = clazz;
        }

        public ExportType getExportType() {
            return exportType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RunnerKey runnerKey = (RunnerKey) o;

            if (exportType != runnerKey.exportType) return false;
            return clazz.equals(runnerKey.clazz);

        }

        @Override
        public int hashCode() {
            int result = exportType.hashCode();
            result = 31 * result + clazz.hashCode();
            return result;
        }
    }
}

