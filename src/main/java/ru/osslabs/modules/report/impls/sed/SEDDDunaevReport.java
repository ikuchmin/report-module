//package ru.osslabs.modules.report.impls.sed;
//
//import javaslang.concurrent.Future;
//import javaslang.control.Try;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import ru.osslabs.modules.report.decorators.BetweenDateReport;
//import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
//import ru.osslabs.modules.report.decorators.DestinationPathReport;
//import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
//
//import java.io.BufferedInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.OffsetDateTime;
//import java.util.concurrent.Executors;
//
///**
// *  Created by ikuchmin on 09.11.15.
// */
//public class SEDDDunaevReport implements BetweenDateReport, DestinationPathReport, SourceFututeHSSFWorkBookReport, DestinationOutputStreamReport {
//
//    private final Future<HSSFWorkbook> hssfWorkbookFuture;
//    private final OutputStream destOutputStream;
//
//    public SEDDDunaevReport(Try.CheckedSupplier<InputStream> funcInputStream, OutputStream destOutputStream) {
//        this.destOutputStream = destOutputStream;
//        this.hssfWorkbookFuture = Executors.newSingleThreadExecutor().submit(() -> {
//            try (InputStream templateStream = new BufferedInputStream(funcInputStream.get())) {
//                return new HSSFWorkbook(new POIFSFileSystem(templateStream));
//            } catch (Throwable io) {
//                throw new RuntimeException(io);
//            }
//        });
//    }
//
////        hssfWorkbookFuture = Executors.newSingleThreadExecutor().submit(() -> {
////                    if (reportPath.toFile().exists()) {
////                        return func.apply(() -> Files.newInputStream(reportPath, StandardOpenOption.READ));
////                    } else {
////                        return func.apply(() -> Files.newInputStream(reportPath, StandardOpenOption.READ));
////                    }
////                }
////        );
////                try (InputStream templateStream = new BufferedInputStream(
////                        Files.newInputStream(reportPath, StandardOpenOption.READ))) {
////                    return new HSSFWorkbook(new POIFSFileSystem(templateStream));
////                } catch (IOException io) {
////                    throw new RuntimeException(io);
////                }
////            } else {
////
////            }
//
//
//    @Override
//    public OffsetDateTime getFirstDate() {
//        return null;
//    }
//
//    @Override
//    public OffsetDateTime getLastDate() {
//        return null;
//    }
//
//    @Override
//    public Path getDestination() {
//        return Paths.get("template1.compiled.xls");
//    }
//
//    @Override
//    public Future<HSSFWorkbook> getHSSFWorkbookFuture() {
//        return hssfWorkbookFuture;
//    }
//
//    @Override
//    public String getDataBagCellName() {
//        return "DataBeg";
//    }
//
//    @Override
//    public OutputStream getDestinationOS() {
//        return destOutputStream;
//    }
//}
