package ru.osslabs.modules.report.decorators;

import ru.osslabs.modules.report.types.Report;

import java.time.OffsetDateTime;

/**
 * Created by ikuchmin on 08.11.15.
 */
public interface BetweenDateReport extends Report {
    OffsetDateTime getFirstDate();
    OffsetDateTime getLastDate();
}
