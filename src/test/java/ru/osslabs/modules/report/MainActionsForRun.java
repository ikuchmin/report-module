package ru.osslabs.modules.report;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.DestinationPathReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.factories.sed.ddunaev.SecondReportToOutputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

//import org.jooq.lambda.Seq;
//import java.util.stream.Stream;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class MainActionsForRun {

//    @Test
//    public void jUniPrintReportProcess() {
//        Path templatePath = Paths.get("template1.xlt");
//        Double multiplier = 10.0;
//        try (InputStream templateStream = new BufferedInputStream(Files.newInputStream(templatePath, StandardOpenOption.READ))) {
//            HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(templateStream));
//            new ReportBuilder<>(new JUniPrintReport(workbook, workbook.getName("DataBeg")))
//                    .compose(MockDataFetchers.matrixFiveOnTwentyFive())
//                    .transform((report, composableData) -> Matrix.of(composableData)
//                            .matrixMap((row, col, el) -> el * multiplier))
//                    .transform(HSSFWorkbookTransformer::new)
//                    .compile(JUniPrintEngine.jUniPrintEngineFactory())
//                    .publish((report, compiledReport) -> {
//                        try (OutputStream outputStream = new BufferedOutputStream(
//                                Files.newOutputStream(Paths.get("template.compiled.xls")))) {
//                            compiledReport.write(outputStream);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return "template.compiled.xls";
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void testJOOL() throws Exception {
//        Short a = 12;
//        Stream<Stream<Double>> str = Stream.of(
//                Stream.of(1.0, 2.0, 3.0, 4.0),
//                Stream.of(1.0, 2.0, 3.0, 4.0),
//                Stream.of(1.0, 2.0, 3.0, 4.0),
//                Stream.of(1.0, 2.0, 3.0, 4.0));
//
//        str.map(s1 -> s1.zip(Stream.gen(1, i -> i + 1)))
//                .zip(Stream.gen(1, i -> i + 1))
//                .forEach(coll ->
//                        coll._1.forEach(elem ->
//                                System.out.print(elem._2 + " - " + coll._2 + " - " + elem._1 + "\n")));
//        str.zipAll(Stream.gen(1, i -> i + 1), str.get(0), 100).forEach(System.out::println);
//        str.flatMap((el, el2) -> )

//        IntStreamEx.iterate(0, it -> it + 1).limit(10).forEach(System.out::println);

//        Seq.seq(new Random().doubles()).limit(10).zip(Seq.generate(()))forEach(System.out::println);
//        Stream.generate(() -> new Random().nextDouble()).findFirst().ifPresent(System.out::println);


//    }

    class SEDDunaevReport implements BetweenDateReport, DestinationPathReport, SourceFututeHSSFWorkBookReport {
        @Override
        public OffsetDateTime getFirstDate() {
            return null;
        }

        @Override
        public OffsetDateTime getLastDate() {
            return null;
        }

        @Override
        public Path getDestination() {
            return Paths.get("template1.compiled.xls");
        }

        @Override
        public Future<HSSFWorkbook> getHSSFWorkbookFuture() {
            return Executors.newSingleThreadExecutor().submit(() -> {
                Path templatePath = Paths.get("template1.xlt");
                try (InputStream templateStream = new BufferedInputStream(Files.newInputStream(templatePath, StandardOpenOption.READ))) {
                    return new HSSFWorkbook(new POIFSFileSystem(templateStream));
                } catch (IOException io) {
                    throw new RuntimeException(io);
                }
            });
        }

        @Override
        public String getDataBagCellName() {
            return "DataBeg";
        }
    }

//        @Test
//    public void testSEDDDunaevReportFactory() throws Exception {
//        System.out.println(new SEDDDunaevReportFactory<SEDDunaevReport>().runReport(ExportType.xls, new SEDDunaevReport()));
//
//    }

    @SuppressWarnings("unchecked")
    public static <T, R, E> R funResolve(Function<T, R> fn, E arg) {
        return fn.apply((T) arg);
    }

    @Test
    public <T1 extends String,
            T2 extends Integer> void testUnTypeInType() throws Exception {
//        Map<Integer, Function<?, ?>> functionMap = new HashMap<>();
//
//        Function<T1, ?> fn1 = p -> p.concat("World");
//        Function<T2, ?> fn2 = p -> p.intValue() + 12;
//        functionMap.put(1, fn1);
//        functionMap.put(2, fn2);
//        System.out.println(functionMap.get(1).apply("dsfadsf"));
//        System.out.println(funResolve(functionMap.get(2), 12));
    }

    @Test
    public void testReflectionGenerics() throws Exception {
        SecondReportToOutputStream<?> rf = new SecondReportToOutputStream<>();
        Class<?> rfClass = rf.getClass();

        Function<Class<?>, Class<?>> findInterface = (analyzeClass) -> {
            for (Class<?> cl : rfClass.getInterfaces()) {
                if (cl.equals(analyzeClass))
                    return cl;
            }
            return null;
        };

        Arrays.asList(rf.getClass().getGenericInterfaces()).stream()
                .map((in) -> (ParameterizedType) in)
                .filter((in) -> in.getRawType() == ReportFactory.class)
                .map((in) -> in.getActualTypeArguments()[1])
                .findFirst();

//        Class<?> reportFactoryInterface = findInterface.apply(ReportFactory.class);
//        if (reportFactoryInterface != null) {
//            ParameterizedType t = (ParameterizedType)reportFactoryInterface.getGenericInterfaces()[0];
//            System.out.println((Class<?>)t.getActualTypeArguments()[1]);
//        }

//        ParameterizedType[] t = (ParameterizedType[]) rf.getClass().getGenericInterfaces();
//        Class<?> reportResultType = (Class<?>) t.getActualTypeArguments()[0];
//        System.out.println(reportResultType);

    }
}
