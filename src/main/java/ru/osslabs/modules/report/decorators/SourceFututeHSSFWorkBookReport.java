package ru.osslabs.modules.report.decorators;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.osslabs.modules.report.types.Report;

import java.util.concurrent.Future;

/**
 * Created by ikuchmin on 08.11.15.
 */
public interface SourceFututeHSSFWorkBookReport extends Report {
    Future<HSSFWorkbook> getHSSFWorkbookFuture();

    String getDataBagCellName();
}
