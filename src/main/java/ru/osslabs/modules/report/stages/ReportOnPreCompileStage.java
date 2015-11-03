package ru.osslabs.modules.report.stages;

import ru.osslabs.modules.report.Report;
import ru.osslabs.modules.report.functions.Engine;
import ru.osslabs.modules.report.functions.Transformer;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface ReportOnPreCompileStage<Re extends Report, Da> extends ReportStage<Re>{
    <R> ReportOnPreCompileStage<Re, R> transform(Transformer<Re, Da, R> transformer);
    <ReC> ReportOnPreConvertStage<Re, ReC> compile(Engine<Re, Da, ReC> engine);
}
