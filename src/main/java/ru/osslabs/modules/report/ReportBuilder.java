package ru.osslabs.modules.report;

import ru.osslabs.modules.report.functions.*;
import ru.osslabs.modules.report.stages.ReportOnPreCompileStage;
import ru.osslabs.modules.report.stages.ReportOnPreConvertStage;
import ru.osslabs.modules.report.stages.ReportOnPreFetchDataStage;
import ru.osslabs.modules.report.types.Report;

/**
 * Created by ikuchmin on 02.11.15.
 */
public class ReportBuilder<Re extends Report> implements ReportOnPreFetchDataStage<Re> {

    private final Re report;

    public ReportBuilder(Re report) {
        this.report = report;
    }

    @Override
    public <R> ReportOnPreCompileStage<Re, R> compose(Fetcher<? super Re, R> fetcher) {
        R data = fetcher.compose(report);
        return new PreCompileStage<>(report, data);
    }


    public static class PreCompileStage<Re extends Report, Da> implements ReportOnPreCompileStage<Re, Da> {

        private final Re report;
        private final Da composableData;

        public PreCompileStage(Re report, Da composableData) {
            this.report = report;
            this.composableData = composableData;
        }

        @Override
        public <R> ReportOnPreCompileStage<Re, R> transform(Transformer<? super Re, ? super Da, R> transformer) {
            return new PreCompileStage<>(report, transformer.transform(report, composableData));
        }

        @Override
        public <ReC> ReportOnPreConvertStage<Re, ReC> compile(Engine<? super Re, ? super Da, ReC> engine) {
            return new PreConvertStage<>(report, engine.compile(report, composableData));
        }
    }


    public static class PreConvertStage<Re extends Report, ReC> implements ReportOnPreConvertStage<Re, ReC> {

        private final Re report;
        private final ReC compiledReport;

        public PreConvertStage(Re report, ReC compiledReport) {
            this.report = report;
            this.compiledReport = compiledReport;
        }

        @Override
        public <ReF> ReportOnPreConvertStage<Re, ReF> convert(Converter<? super Re, ? super ReC, ReF> converter) {
            return new PreConvertStage<>(report, converter.converter(report, compiledReport));
        }

        @Override
        public <ReP> ReP publish(Publisher<? super Re, ? super ReC, ReP> converter) {
            return converter.publish(report, compiledReport);
        }
    }
}
