package ru.osslabs.modules.report;

import javaslang.collection.Stream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;
import ru.osslabs.modules.report.engines.JUniPrintEngine;
import ru.osslabs.modules.report.fetchers.MockDataFetchers;
import ru.osslabs.modules.report.transformers.HSSFWorkbookTransformers;
import ru.osslabs.modules.report.types.JUniPrintReport;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//import org.jooq.lambda.Seq;
//import java.util.stream.Stream;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class MainActionsForRun {

    @Test
    public void jUniPrintReportProcess() {
        Path templatePath = Paths.get("template1.xlt");
        Double multiplier = 10.0;
        try (InputStream templateStream = new BufferedInputStream(Files.newInputStream(templatePath, StandardOpenOption.READ))) {
            HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(templateStream));
            new ReportBuilder<>(new JUniPrintReport(workbook, workbook.getName("DataBeg")))
                    .compose(MockDataFetchers.matrixFiveOnTwentyFive())
                    .transform((report, composableData) -> Matrix.of(composableData)
                            .matrixMap((row, col, el) -> el * multiplier))
                    .transform(HSSFWorkbookTransformers.fromMatrix())
                    .compile(JUniPrintEngine.jUniPrintEngineFactory())
                    .publish((report, compiledReport) -> {
                        try (OutputStream outputStream = new BufferedOutputStream(
                                Files.newOutputStream(Paths.get("template.compiled.xls")))) {
                            compiledReport.write(outputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "template.compiled.xls";
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJOOL() throws Exception {
        Short a = 12;
        Stream<Stream<Double>> str = Stream.of(
                Stream.of(1.0, 2.0, 3.0, 4.0),
                Stream.of(1.0, 2.0, 3.0, 4.0),
                Stream.of(1.0, 2.0, 3.0, 4.0),
                Stream.of(1.0, 2.0, 3.0, 4.0));

        str.map(s1 -> s1.zip(Stream.gen(1, i -> i + 1)))
                .zip(Stream.gen(1, i -> i + 1))
                .forEach(coll ->
                        coll._1.forEach(elem ->
                                System.out.print(elem._2 + " - " + coll._2 + " - " + elem._1 + "\n")));
//        str.zipAll(Stream.gen(1, i -> i + 1), str.get(0), 100).forEach(System.out::println);
//        str.flatMap((el, el2) -> )

//        IntStreamEx.iterate(0, it -> it + 1).limit(10).forEach(System.out::println);

//        Seq.seq(new Random().doubles()).limit(10).zip(Seq.generate(()))forEach(System.out::println);
//        Stream.generate(() -> new Random().nextDouble()).findFirst().ifPresent(System.out::println);


    }
}
