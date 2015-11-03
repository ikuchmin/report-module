package ru.osslabs.modules.report;

import ru.osslabs.modules.report.functions.*;
import ru.osslabs.modules.report.stages.ReportOnPreCompileStage;
import ru.osslabs.modules.report.stages.ReportOnPreConvertStage;
import ru.osslabs.modules.report.stages.ReportOnPreFetchDataStage;
import ru.osslabs.modules.report.stages.ReportOnPrePublishStage;

/**
 * Created by ikuchmin on 02.11.15.
 */
public class ReportBuilder<E extends Report> {
    private E report;

    public static class PreFetchDataStage<Re extends Report> implements ReportOnPreFetchDataStage<Re> {

        private final Re report;

        public PreFetchDataStage(Re report) {
            this.report = report;
        }

        @Override
        public <R> ReportOnPreCompileStage<Re, R> compose(Fetcher<Re, R> fetcher) {
            R data = fetcher.compose(report);
            return new PreCompileStage<>(report, data);
        }
    }

    public static class PreCompileStage<Re extends Report, Da> implements ReportOnPreCompileStage<Re, Da> {

        private final Re report;
        private final Da composableData;

        public PreCompileStage(Re report, Da composableData) {
            this.report = report;
            this.composableData = composableData;
        }

        @Override
        public <R> ReportOnPreCompileStage<Re, R> transform(Transformer<Re, Da, R> transformer) {
            return new PreCompileStage<>(report, transformer.transform(composableData, report));
        }

        @Override
        public <ReC> ReportOnPreConvertStage<Re, ReC> compile(Engine<Re, Da, ReC> engine) {
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
        public <ReF> ReportOnPreConvertStage<Re, ReF> convert(Converter<Re, ReC, ReF> converter) {
            return new PreConvertStage<>(report, converter.converter(report, compiledReport));
        }

        @Override
        public <ReP> ReP publish(Publisher<Re, ReC, ReP> converter) {
            return converter.publish(report, compiledReport);
        }
    }

//    public class OnPreFetchData implements ReportOnPreFetchDataStage {
//        @Override
//        public <R> ReportOnPreCompileStage<R> compose(Fetcher<R> fetcher) {
//
//        }
//    }

    public class ReportDataFetched {
        public ReportTransform transform(){};
        public ReportCompile compile(){};
    }

    public class ReportTransform {
        public ReportTransform transform(){};
        public ReportCompile compile(){};
    }

    public class ReportCompile {
        public ReportConvert convert(){};
        public void publish(){};
    }

    public class ReportConvert {
        public ReportConvert convert() {};
        public void publish(){};
    }

    public ReportBuilder(Report report) {
    }
}
