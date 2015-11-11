package ru.osslabs.modules.report;

import ru.osslabs.modules.report.types.Report;

import java.util.Map;

/**
 * Created by ikuchmin on 09.11.15.
 */
public interface ReportPOJOBuilder<T extends Report> {
    T build(Map<String, Object> parameters);
}
