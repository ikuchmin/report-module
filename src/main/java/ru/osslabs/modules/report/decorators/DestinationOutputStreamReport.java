package ru.osslabs.modules.report.decorators;

import ru.osslabs.modules.report.types.Report;

import java.io.OutputStream;

/**
 * Created by ikuchmin on 11.11.15.
 */
public interface DestinationOutputStreamReport extends Report{
    OutputStream getDestinationOS();
}
