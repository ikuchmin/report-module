package ru.osslabs.modules.report.stages;

import ru.osslabs.modules.report.types.Report;
import ru.osslabs.modules.report.functions.Engine;
import ru.osslabs.modules.report.functions.Transformer;

/**
 * Created by ikuchmin on 02.11.15.
 */
public interface ReportOnPreCompileStage<Re extends Report, Da> extends ReportStage<Re>{
    <R> ReportOnPreCompileStage<Re, R> transform(Transformer<? super Re, ? super Da, R> transformer);
    <ReC> ReportOnPreConvertStage<Re, ReC> compile(Engine<? super Re, ? super Da, ReC> engine);
}
