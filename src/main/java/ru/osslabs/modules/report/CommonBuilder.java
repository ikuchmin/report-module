package ru.osslabs.modules.report;

import javaslang.concurrent.Future;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.osslabs.modules.report.decorators.*;

import java.io.OutputStream;
import java.nio.file.Path;
import java.time.OffsetDateTime;

/**
 * This Builder is uses for moment when Converter is implements
 * Created by ikuchmin on 18.11.15.
 */
public class CommonBuilder implements BetweenDateReport, DestinationOutputStreamReport, DestinationPathReport, SourceFututeHSSFWorkBookReport, SourcePathReport {


    private final Future<HSSFWorkbook> workBookFuture;
    private final OutputStream destinationOutputStream;

    public CommonBuilder(Future<HSSFWorkbook> workBookFuture, OutputStream destinationOutputStream) {
        this.workBookFuture = workBookFuture;
        this.destinationOutputStream = destinationOutputStream;
    }

    @Override
    public OffsetDateTime getFirstDate() {
        return null;
    }

    @Override
    public OffsetDateTime getLastDate() {
        return null;
    }

    @Override
    public OutputStream getDestinationOS() {
        return destinationOutputStream;
    }

    @Override
    public Path getDestination() {
        return null;
    }

    @Override
    public Future<HSSFWorkbook> getHSSFWorkbookFuture() {
        return workBookFuture;
    }

    @Override
    public String getDataBagCellName() {
        return "DataBeg";
    }

    @Override
    public Path getSource() {
        return null;
    }
}
