package ru.osslabs.modules.report.decorators;

import ru.osslabs.modules.report.types.Report;

import java.nio.file.Path;

/**
 * Created by ikuchmin on 08.11.15.
 */
public interface DestinationPathReport extends Report {
    Path getDestination();
}
