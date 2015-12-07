package ru.osslabs.modules.report;

import javaslang.concurrent.Future;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.modules.report.decorators.*;
import ru.osslabs.modules.report.spu.ServiceIdReport;

import java.io.OutputStream;
import java.nio.file.Path;
import java.time.OffsetDateTime;

/**
 * This Builder is uses for moment when Converter is implements
 * Created by ikuchmin on 18.11.15.
 */
public class CommonBuilder implements BetweenDateReport, DestinationOutputStreamReport, DestinationPathReport, SourceFututeHSSFWorkBookReport, SourcePathReport, ServiceIdReport {


    private final Future<HSSFWorkbook> workBookFuture;
    private final OutputStream destinationOutputStream;
    private final String serviceDataObject;

    public CommonBuilder(Future<HSSFWorkbook> workBookFuture, OutputStream destinationOutputStream, String serviceDataObject) {
        this.workBookFuture = workBookFuture;
        this.destinationOutputStream = destinationOutputStream;
        this.serviceDataObject = serviceDataObject;
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

    @Override
    public String getServiceId() {
        return serviceDataObject;
    }
}
