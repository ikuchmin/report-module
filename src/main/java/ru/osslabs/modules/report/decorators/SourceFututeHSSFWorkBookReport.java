package ru.osslabs.modules.report.decorators;

import javaslang.concurrent.Future;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.osslabs.modules.report.types.Report;

/**
 * Created by ikuchmin on 08.11.15.
 */
public interface SourceFututeHSSFWorkBookReport extends Report {
    Future<HSSFWorkbook> getHSSFWorkbookFuture();

    String getDataBagCellName();
}
