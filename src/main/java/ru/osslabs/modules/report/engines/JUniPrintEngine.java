package ru.osslabs.modules.report.engines;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.comsoft.juniprint.JUniPrint;
import org.comsoft.juniprint.JUniPrintException;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class JUniPrintEngine {

    public static <Re extends SourceFututeHSSFWorkBookReport, Da extends HSSFWorkbook> HSSFWorkbook compile(Re report, Da composableData) {
        try {
            JUniPrint jUniPrint = new JUniPrint(composableData);
            jUniPrint.init(null, null, report.getDataBagCellName());
            jUniPrint.uniPrint(false);
            return composableData;
        } catch (JUniPrintException e) {
            throw new RuntimeException(e);
        }
    }


}
