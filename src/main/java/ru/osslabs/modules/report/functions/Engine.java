package ru.osslabs.modules.report.functions;

import ru.osslabs.modules.report.types.Report;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface Engine<Re extends Report, Da, ReC> {
    ReC compile(Re report, Da composableData);
}
