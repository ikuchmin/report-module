//package ru.osslabs.modules.report.impls.sed;
//
//import javaslang.Tuple2;
//import ru.osslabs.modules.report.*;
//import ru.osslabs.modules.report.decorators.BetweenDateReport;
//import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
//import ru.osslabs.modules.report.decorators.DestinationPathReport;
//import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
//import ru.osslabs.modules.report.engines.JUniPrintEngine;
//import ru.osslabs.modules.report.fetchers.MockDataFetchers;
//import ru.osslabs.modules.report.functions.Fetcher;
//import ru.osslabs.modules.report.publishers.HSSFWorkBookFileStorePublisher;
//import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;
//import ru.osslabs.modules.report.types.Report;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.ConcurrencyManagement;
//import javax.ejb.DependsOn;
//import javax.ejb.Singleton;
//import javax.ejb.Startup;
//import javax.enterprise.context.Dependent;
//import javax.inject.Inject;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static javax.ejb.ConcurrencyManagementType.BEAN;
//import static ru.osslabs.modules.report.ReportUtils.objectNotNull;
//
///**
// * Created by ikuchmin on 08.11.15.
// * Имя отчета должно быть зафиксировано в сущности уходящей в БД - изменяемо в Runtime
// * Все функции должны фиксировать требования ко входным параметрам в качестве интерфейсов
// * для реализации
// * Проблема в том, что если входные параметры отличаются в цепочках, то для запуска отчета все равно
// * придется реализовать весь набор интерфейсов для T. Что совсем не очень хорошо. Для решения
// * таких проблем есть параметризация методов
// */
//
////@Dependent // Это временный хак
//@Startup
//@Singleton
//@ConcurrencyManagement(BEAN)
//@DependsOn("ReportDiscoveryImpl")
//@Dependent
//@ReportFactoryAnnotation
//public class SEDDDunaevReportFactory extends AbstractReportFactory {
//
//    // Добавить Qualifier
//
//
//
//    private final Double multiplier = 100.0;
//
//    public SEDDDunaevReportFactory() {
//        runners = new HashMap<>();
//    }
//
//    /**
//     * В дальнейшем возможно вынести описание функций с аннотацией, которая
//     * будет обеспечивать регистрацию Flow в сервис Discovery
//     * @param <R1>
//     * @param <R2>
//     */
//    @PostConstruct
//    public <R1 extends BetweenDateReport & DestinationOutputStreamReport & SourceFututeHSSFWorkBookReport,
//            R2 extends BetweenDateReport & DestinationPathReport & SourceFututeHSSFWorkBookReport> void init() {
//
//        Function<R1, ?> fn1 = (r) -> new ReportBuilder<>(r)
//                .compose(sedDataFetcher)
//                .transform(HSSFWorkbookTransformers::fromStreamTuplesToHSSFWorkbook)
//                .compile(JUniPrintEngine::compile)
//                .publish(HSSFWorkBookFileStorePublisher::writeToOutputStream);
//
//        Function<R2, ?> fn2 = (r) -> new ReportBuilder<>(r)
//                .compose(MockDataFetchers::matrixFiveOnTwentyFive)
//                .transform((re, data) -> Matrix.of(data)
//                        .matrixMap((row, col, el) -> el * multiplier))
//                .transform(HSSFWorkbookTransformers::fromMatrixToHSSFWorkbook)
//                .compile(JUniPrintEngine::compile)
//                .publish(HSSFWorkBookFileStorePublisher::writeToFileStore);
//
//        runners.put(new RunnerKey(ExportType.xls, Void.class), fn1);
//        runners.put(new RunnerKey(ExportType.xls, Path.class), fn2);
//    }
//
//    @Override
//    public String getReportCode() {
//        return "sedddunaev";
//    }
//
//    @Override
//    public Path getReportPath() {
//        return Paths.get("/reports/juniprint/sedreport.xlt");
//    }
//
//    @Override
//    public String getReportName() {
//        return "Отчет по документообороту";
//    }
//
//    @Override
//    public Collection<ExportType> getExportTypes() {
//        return runners.keySet()
//                .stream().map((run) -> run.getExportType()).collect(Collectors.toSet());
//    }
//
//    @Override
//    public Collection<ReportParameter> getReportParams() {
//        return Arrays.asList(
//                new ReportParameter("beginDate", "Дата начала диапазона", String.class),
//                new ReportParameter("endDate", "Дата окончания диапазона", String.class));
//    }
//}
