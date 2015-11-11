package ru.osslabs.modules.report.publishers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.osslabs.modules.report.decorators.DestinationOutputStreamReport;
import ru.osslabs.modules.report.decorators.DestinationPathReport;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by ikuchmin on 08.11.15.
 */
public class HSSFWorkBookFileStorePublisher {

    public static <Re extends DestinationPathReport, ReC extends HSSFWorkbook> Path writeToFileStore(Re report, ReC reportForPublication) {
        try (OutputStream outputStream = new BufferedOutputStream(
                Files.newOutputStream(report.getDestination()))) {
            reportForPublication.write(outputStream);
            return report.getDestination();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

    /**
     * Пока оставим возврат Boolean пото
     * @param report
     * @param reportForPublication
     * @param <Re>
     * @param <ReC>
     * @return
     */
    public static <Re extends DestinationOutputStreamReport, ReC extends HSSFWorkbook> Void writeToOutputStream(Re report, ReC reportForPublication) {
        try (OutputStream outputStream = new BufferedOutputStream(report.getDestinationOS())) {
            reportForPublication.write(outputStream);
            return null;
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
}
